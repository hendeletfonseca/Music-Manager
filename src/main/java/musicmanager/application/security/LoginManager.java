package musicmanager.application.security;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class LoginManager {
    private static final String ALGORITHM = "AES";
    private static final String PADDING = "AES/ECB/PKCS5Padding";
    private static final String LOGINS_FILE = "src/datas/logins.enc";

    public boolean checkLogin(String username, String password) {
        String[][] logins = loadLogins(LOGINS_FILE);
        for (String[] login : logins) {
            if (login[0].equals(username) && login[1].equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void saveLogin(String username, String password) {
        String[][] logins = loadLogins(LOGINS_FILE);
        for (String[] login : logins) {
            if (login[0].equals(username)) {
                System.out.println("Username already exists. Cannot save login.");
                return;
            }
        }
        List<Login> loginList = new ArrayList<>();
        for (String[] login : logins) {
            loginList.add(new Login(login[0], login[1]));
        }
        loginList.add(new Login(username, password));
        saveLogins(loginList, LOGINS_FILE);
    }

    public void updateLogin(String username, String newPassword) {
        String[][] logins = loadLogins(LOGINS_FILE);
        List<Login> loginList = new ArrayList<>();
        for (String[] login : logins) {
            if (login[0].equals(username)) {
                loginList.add(new Login(login[0], newPassword));
            } else {
                loginList.add(new Login(login[0], login[1]));
            }
        }
        saveLogins(loginList, LOGINS_FILE);
    }

    public boolean deleteLogin(String username) {
        String[][] logins = loadLogins(LOGINS_FILE);
        List<Login> loginList = new ArrayList<>();
        boolean found = false;
        for (String[] login : logins) {
            if (login[0].equals(username)) {
                found = true;
            } else {
                loginList.add(new Login(login[0], login[1]));
            }
        }
        if (found) {
            saveLogins(loginList, LOGINS_FILE);
        }
        return found;
    }

    private void saveLogins(List<Login> loginList, String filename) {
        try (FileOutputStream fos = new FileOutputStream(filename);
             CipherOutputStream cos = createCipherOutputStream(fos)) {
            ObjectOutputStream oos = new ObjectOutputStream(cos);
            oos.writeObject(loginList);
            oos.flush();
        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            System.out.println(e.getMessage());
        }
    }

    private String[][] loadLogins(String filename) {
        try (FileInputStream fis = new FileInputStream(filename);
             CipherInputStream cis = createCipherInputStream(fis)) {
            ObjectInputStream ois = new ObjectInputStream(cis);
            List<Login> loginList = (List<Login>) ois.readObject();
            String[][] logins = new String[loginList.size()][2];
            for (int i = 0; i < loginList.size(); i++) {
                Login login = loginList.get(i);
                logins[i][0] = login.getUsername();
                logins[i][1] = login.getPassword();
            }
            return logins;
        } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            System.out.println(e.getMessage());
            return new String[0][0];
        }
    }

    private CipherOutputStream createCipherOutputStream(OutputStream outputStream) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(PADDING);
        SecretKey secretKey = generateSecretKey();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return new CipherOutputStream(outputStream, cipher);
    }

    private CipherInputStream createCipherInputStream(InputStream inputStream) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(PADDING);
        SecretKey secretKey = generateSecretKey();
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new CipherInputStream(inputStream, cipher);
    }

    private SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        byte[] keyBytes = "MySecretKey12345".getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ALGORITHM);
        return secretKeySpec;
    }

    private static class Login implements Serializable {
        private final String username;
        private final String password;

        public Login(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}
