package musicmanager.application;

import musicmanager.application.model.*;
import musicmanager.application.persistence.UserPersistence;
import musicmanager.application.security.LoginManager;
import musicmanager.application.service.Logger;

import java.util.Date;

public class tests {

    public static void main(String[] args) {
        Lyric lyric = new Lyric("lyricExample");
        Date date = new Date();
        Duration duration = new Duration(3, 30);
        Song song = new Song("title", duration, "authors", date, "genre", lyric);
        InstrumentalMusic im = new InstrumentalMusic("title", duration, "authors", date, "genre", "sheetMusicExample");
        String usersFileName = "src/datas/users.bin";

        //boolean ok = Logger.createUser("Login", "login", "password", TYPE_USER.DEFAULT_USER);

        DefaultUser user = (DefaultUser) Logger.userCreator("login", "password");
        System.out.println(user.toString());
        String path = "src/datas/particularcollections/" + user.getId() + ".bin";
        user.createMusicCollection(path);
        MusicCollection mc = user.loadMusicCollection(path);
        System.out.println(mc);
    }

}
