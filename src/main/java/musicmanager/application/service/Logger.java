package musicmanager.application.service;

import musicmanager.application.model.AdminUser;
import musicmanager.application.model.DefaultUser;
import musicmanager.application.model.TYPE_USER;
import musicmanager.application.model.User;
import musicmanager.application.persistence.UserPersistence;
import musicmanager.application.security.LoginManager;
import musicmanager.application.util.Random;

public class Logger {

    public static User userCreator(String login, String password) {

        if (LoginManager.checkLogin(login, password)) {
            return UserPersistence.loadUser(login);
        }
        return null;
    }
    public static boolean createUser(String name, String login, String password, TYPE_USER userType) {
        if (!UserPersistence.userAlreadyExist(login)) {
            if (userType == TYPE_USER.DEFAULT_USER) {
                DefaultUser user = new DefaultUser(Random.getRandomInt(), login, name);
                LoginManager.saveLogin(login, password);
                UserPersistence.saveUser(user);
            } else if (userType == TYPE_USER.ADMIN_USER) {
                AdminUser user = new AdminUser(Random.getRandomInt(), login, name);
                LoginManager.saveLogin(login, password);
                UserPersistence.saveUser(user);
            }
            return true;
        }
        else {
            return false;
        }

    }
    public static TYPE_USER getUserType(String login) {
        return UserPersistence.getUserType(login);
    }
}