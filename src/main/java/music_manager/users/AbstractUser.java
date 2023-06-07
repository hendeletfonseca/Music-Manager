package music_manager.users;
import java.io.IOException;
import java.io.RandomAccessFile;

public abstract class AbstractUser {
    private String name;
    private int id;
    private String login;

    public AbstractUser(String name, int id, String login, String password) {
        this.name = name;
        this.id = id;
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public static void saveUser(int id, String login, String password) {
        int PASSWORD_SIZE = 20;
        int LOGIN_SIZE = 15;

        try (RandomAccessFile file = new RandomAccessFile("users.bin", "rw")) {
            int position = id * (PASSWORD_SIZE + LOGIN_SIZE);
            file.seek(position);
            byte[] loginBytes = padString(login, LOGIN_SIZE).getBytes();
            byte[] passwordBytes = padString(password, PASSWORD_SIZE).getBytes();
            file.write(loginBytes);
            file.write(passwordBytes);
            System.out.println("Usuário salvo com sucesso.");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao salvar o usuário: " + e.getMessage());
        }
    }

    private static String padString(String str, int length) {
        if (str.length() < length) {
            StringBuilder paddedStr = new StringBuilder(str);
            while (paddedStr.length() < length) {
                paddedStr.append(" ");
            }
            return paddedStr.toString();
        }
        return str.substring(0, length);
    }
}
