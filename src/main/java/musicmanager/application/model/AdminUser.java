package musicmanager.application.model;

import musicmanager.application.persistence.UserPersistence;
import musicmanager.application.security.LoginManager;

public class AdminUser extends User{

    public AdminUser(int id, String login, String name) {
        super(id, login, name, TYPE_USER.ADMIN_USER);
    }

    public boolean updateMusic(MusicCollection musicCollection, Music music) {
        return musicCollection.updateMusic(music);
    }
    public boolean deleteUserAccount(String login) {
        LoginManager lm = new LoginManager();
        UserPersistence up = new UserPersistence();
        up.deleteUser("src/datas/usersdata.bin", login);
        return lm.deleteLogin(login);
    }
}
