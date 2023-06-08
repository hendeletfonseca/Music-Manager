package music_manager.users;

import music_manager.collections.MusicCollection;
import music_manager.collections.MusicCollectionArrayList;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class User extends AbstractUser{

    private MusicCollection particularMusicCollection;
    public User(String name, int id) {
        super(name, id);
        this. particularMusicCollection = new MusicCollectionArrayList();
        this.particularMusicCollection.load();
    }

    public MusicCollection getParticularMusicCollection() {
        return particularMusicCollection;
    }

    public boolean createParticularMusicCollection() {
        String fileName = getName() + ".bin";
        File file = new File(fileName);

        if (file.exists()) {
            System.out.println("Particular music collection file already exists.");
            return false;
        }

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
