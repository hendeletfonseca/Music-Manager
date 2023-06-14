package musicmanager.application.service;

import musicmanager.application.model.AdminUser;
import musicmanager.application.model.DefaultUser;
import musicmanager.application.model.TYPE_USER;
import musicmanager.application.model.User;
import musicmanager.application.persistence.UserPersistence;
import musicmanager.application.security.LoginManager;
import musicmanager.application.util.Random;

public class Logger {
    public static final String USERS_DIR = "src/datas/usersdata.bin";

    public static User userCreator(String login, String password) {

        if (LoginManager.checkLogin(login, password)) {
            TYPE_USER type = UserPersistence.getUserType(USERS_DIR, login);
            if (type == TYPE_USER.DEFAULT_USER) {
                return UserPersistence.loadUser(USERS_DIR, login);
            }
            if (type == TYPE_USER.ADMIN_USER) {
                return UserPersistence.loadUser(USERS_DIR, login);
            }
        }
        return null;
    }
    public static boolean createUser(String name, String login, String password, TYPE_USER userType) {
        if (!UserPersistence.userAlreadyExist(USERS_DIR, login)) {
            if (userType == TYPE_USER.DEFAULT_USER) {
                DefaultUser user = new DefaultUser(Random.getRandomInt(), login, name);
                LoginManager.saveLogin(login, password);
                UserPersistence.saveUser(user, USERS_DIR);
            } else if (userType == TYPE_USER.ADMIN_USER) {
                AdminUser user = new AdminUser(Random.getRandomInt(), login, name);
                LoginManager.saveLogin(login, password);
                UserPersistence.saveUser(user, USERS_DIR);
            }
            return true;
        }
        else {
            return false;
        }

    }
    public static TYPE_USER getUserType(String login) {
        return UserPersistence.getUserType(USERS_DIR, login);
    }
}