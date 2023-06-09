package music_manager.users;

import music_manager.collections.MusicCollection;
import music_manager.collections.MusicCollectionArrayList;
import music_manager.entities.Music;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
public class User extends AbstractUser{
    public static final String DIR = "src/datas/particular_collections/";
    private MusicCollection particularMusicCollection;
    public User(String name) {
        super(name);
        this. particularMusicCollection = new MusicCollectionArrayList();
        this.particularMusicCollection.load(getDIR());
    }
    public String getDIR() {
        return DIR + getName() + ".bin";
    }

    public MusicCollection getParticularMusicCollection() {
        return particularMusicCollection;
    }

    public void createParticularMusicCollection() {
        String fileName = getDIR();
        File file = new File(fileName);

        if (file.exists()) {
            System.out.println("User - createParticularMusicCollection: file already exists");
            return;
        }

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.close();
        } catch (IOException e) {
            System.out.println("User - createParticularMusicCollection: error creating file");
        }
    }
    public void addMusic(Music music) {
        particularMusicCollection.add(music);
    }
    public void removeMusic(Music music) {
        particularMusicCollection.remove(music.getId());
    }
    public Music searchMusic(String title) {
        return particularMusicCollection.search(title);
    }

    public Music searchMusic(int id) {
        return particularMusicCollection.search(id);
    }

    public void showMusicCollection() {
        particularMusicCollection.show();
    }
}