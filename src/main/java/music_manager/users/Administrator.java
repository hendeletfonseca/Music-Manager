package music_manager.users;

import music_manager.entities.Music;

public class Administrator extends AbstractUser{

    public Administrator(String name, int id, String login, String password){
        super(name, id, login, password);
    }

    public void registerMusic() {

    }

    public void searchMusic() {

    }

    public void updateMusic() {

    }

    public Music removeMusic(Music music) {
        return music;
    }
}
