package musicmanager.application.persistence;

import musicmanager.application.model.DefaultUser;
import musicmanager.application.model.TYPE_USER;
import musicmanager.application.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserPersistence {

    public static void saveUsers(List<User> users, String filename) {
        try (FileOutputStream fos = new FileOutputStream(filename);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<User> loadUsers(String filename) {
        try (FileInputStream fis = new FileInputStream(filename);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static DefaultUser loadDefaultUser(String filename, String login) {
        List<User> users = loadUsers(filename);
        if (users != null) {
            for (User user : users) {
                if (user.getLogin().equals(login)) {
                    return (DefaultUser) user;
                }
            }
        }
        return null;
    }
    public static User loadUser(String filename, String login) {
        List<User> users = loadUsers(filename);
        if (users != null) {
            for (User user : users) {
                if (user.getLogin().equals(login)) {
                    return user;
                }
            }
        }
        return null;
    }
    public static boolean saveUser(User user, String filename) {
        List<User> users = loadUsers(filename);
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
        saveUsers(users, filename);
        return true;
    }

    public static boolean deleteUser(String filename, String login) {
        List<User> users = loadUsers(filename);
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
                saveUsers(users, filename);
                return true;
            }
        }
        return false;
    }

    public static boolean deleteUsers(String filename) {
        File file = new File(filename);
        return file.delete();
    }
    public static TYPE_USER getUserType(String filename, String login) {
        List<User> users = loadUsers(filename);
        if (users != null) {
            for (User user : users) {
                if (user.getLogin().equals(login)) {
                    return user.getType();
                }
            }
        }
        return null;
    }
    public static boolean userAlreadyExist(String filename, String login) {
        List<User> users = loadUsers(filename);
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
