package music_manager.credentials;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Credential {

    long PASSWORD_SIZE = 25;
    long LOGIN_SIZE = 25;
    long USER_TYPE_SIZE = 4;
    long ALL_DATA_SIZE = PASSWORD_SIZE + LOGIN_SIZE + USER_TYPE_SIZE;
    String fileDir = "../../datas/credentials.bin";

    private int byteArrayToInt(byte[] bytes) {
        int value = 0;
        for (int i = 0; i < bytes.length; i++) {
            value += (bytes[i] & 0xFF) << (8 * i);
        }
        return value;
    }

    public int verifyCredential(String login, String password) {
        try (RandomAccessFile file = new RandomAccessFile(fileDir, "r")) {
            long fileSize = file.length();
            long offset = 0;

            while (offset < fileSize) {
                file.seek(offset);

                byte[] storedLoginBytes = new byte[(int) LOGIN_SIZE];
                file.read(storedLoginBytes);
                String storedLogin = new String(storedLoginBytes);

                if (login.equals(storedLogin)) {
                    byte[] storedPasswordBytes = new byte[(int) PASSWORD_SIZE];
                    file.read(storedPasswordBytes);
                    String storedPassword = new String(storedPasswordBytes);

                    if (password.equals(storedPassword)) {
                        byte[] storedUserType = new byte[(int) USER_TYPE_SIZE];
                        file.read(storedUserType);
                        return byteArrayToInt(storedUserType);
                    } else {
                        return -1; // Incorrect password
                    }
                }

                offset += ALL_DATA_SIZE;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1; // Login not found
    }

    public boolean loginAlreadyExist(String login) {
        try (RandomAccessFile file = new RandomAccessFile(fileDir, "r")) {
            long fileSize = file.length();
            long offset = 0;

            while (offset < fileSize) {
                file.seek(offset);

                byte[] storedLoginBytes = new byte[(int) LOGIN_SIZE];
                file.read(storedLoginBytes);
                String storedLogin = new String(storedLoginBytes);

                if (login.equals(storedLogin)) {
                    return true;
                }

                offset += ALL_DATA_SIZE - LOGIN_SIZE;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean newCredential(String login, String password, int userType, String fileDir) {
        if (loginAlreadyExist(login)) {
            return false;
        }
        try (RandomAccessFile file = new RandomAccessFile(fileDir, "rw")) {
            long fileSize = file.length();
            long offset = ALL_DATA_SIZE - USER_TYPE_SIZE;

            while (offset < fileSize) {
                file.seek(offset);

                byte[] storedUserType = new byte[4];
                file.read(storedUserType);
                int storedId = byteArrayToInt(storedUserType);

                if (storedId == -1) {
                    long writeOffset = offset - ALL_DATA_SIZE;
                    file.seek(writeOffset);

                    file.write(login.getBytes());
                    file.write(password.getBytes());
                    file.writeInt(userType);

                    return true;
                }

                offset += ALL_DATA_SIZE;
            }

            file.seek(fileSize);
            file.write(login.getBytes());
            file.write(password.getBytes());
            file.writeInt(userType);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}