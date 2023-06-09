package music_manager;

import music_manager.entities.Duration;
import music_manager.entities.InstrumentalMusic;
import music_manager.entities.Song;
import music_manager.users.User;

import java.util.Date;

import static java.util.Calendar.JANUARY;

public class tests {

    public static void main(String[] args) {
        User user = new User("Hendel");
        //user.createParticularMusicCollection();
        //user.addMusic(new InstrumentalMusic("Vida Loka", new Duration(3, 30), "Racionais", new Date(2000, JANUARY, 1), "Rap", "filename"));
        //user.getParticularMusicCollection().save(user.getDIR());
        user.showMusicCollection();
    }

}
