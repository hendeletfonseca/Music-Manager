package musicmanager.application.persistence;

import musicmanager.application.model.DefaultUser;
import musicmanager.application.model.TYPE_USER;
import musicmanager.application.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserPersistence {

    public static String usersDataDir = "src/datas/usersdata.bin";

    public static void saveUsers(List<User> users) {
        try (FileOutputStream fos = new FileOutputStream(UserPersistence.usersDataDir);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<User> loadUsers() {
        try (FileInputStream fis = new FileInputStream(UserPersistence.usersDataDir);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public static User loadUser(String login) {
        List<User> users = loadUsers();
        if (users != null) {
            for (User user : users) {
                if (user.getLogin().equals(login)) {
                    return user;
                }
            }
        }
        return null;
    }
    public static boolean saveUser(User user) {
        List<User> users = loadUsers();
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
        saveUsers(users);
        return true;
    }

    public static boolean deleteUser(String login) {
        List<User> users = loadUsers();
        if (users != null) {
            User userToDelete = null;
            for (User user : users) {
                if (user.getLogin().equals(login)) {
                    userToDelete = user;
                    break;
                }
            }
            if (userToDelete != null) {
                users.remove(userToDelete);
                saveUsers(users);
                return true;
            }
        }
        return false;
    }

    public static boolean deleteUsers() {
        File file = new File(UserPersistence.usersDataDir);
        return file.delete();
    }
    public static TYPE_USER getUserType(String login) {
        List<User> users = loadUsers();
        if (users != null) {
            for (User user : users) {
                if (user.getLogin().equals(login)) {
                    return user.getType();
                }
            }
        }
        return null;
    }
    public static boolean userAlreadyExist(String login) {
        List<User> users = loadUsers();
        if (users != null) {
            for (User user : users) {
                if (user.getLogin().equals(login)) {
                    return true;
                }
            }
        }
        return false;
    }
}
