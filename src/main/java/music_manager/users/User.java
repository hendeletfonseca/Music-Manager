package music_manager.users;

import music_manager.entities.Music;
import music_manager.entities.MusicCollectionArrayList;

public class User extends AbstractUser{
    private MusicCollectionArrayList particularMusicCollection;

    public User(String name, int id, String login, String password) {
        super(name, id, login, password);
        this.particularMusicCollection = new MusicCollectionArrayList();
    }

    public MusicCollectionArrayList getParticularMusicCollection() {
        return particularMusicCollection;
    }

    public void addMusic(Music music) {
        particularMusicCollection.add(music);
    }

    public Music removeMusic(String title) {
        int id = particularMusicCollection.search(title).getId();
        return particularMusicCollection.remove(id);
    }

    public Music searchMusic(String title) {
        return particularMusicCollection.search(title);
    }

    public void viewMusic(String title) {
        particularMusicCollection.search(title).toString();
    }

    public void deleteParticularCollection() {
        particularMusicCollection = null;
    }
}
