package musicmanager.application.model;

import musicmanager.application.persistence.MusicCollectionPersistence;
import musicmanager.application.persistence.UserPersistence;
import musicmanager.application.security.LoginManager;

public class DefaultUser extends User{

    public DefaultUser(int id, String login, String name) {
        super(id, login, name, TYPE_USER.DEFAULT_USER);
    }
    public boolean createMusicCollection(String fileName) {
        MusicCollectionPersistence mcp = new MusicCollectionPersistence();
        mcp.createFile(fileName);
        return true;
    }
    public boolean deleteAccount() {
        LoginManager lm = new LoginManager();
        UserPersistence up = new UserPersistence();
        up.deleteUser("src/datas/usersdata.bin", getLogin());
        return lm.deleteLogin(getLogin());
    }
}
