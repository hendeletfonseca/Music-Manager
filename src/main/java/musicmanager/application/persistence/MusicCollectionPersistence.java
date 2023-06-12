package musicmanager.application.persistence;

import musicmanager.application.model.MusicCollection;

import java.io.*;

public class MusicCollectionPersistence {
    public boolean fileExists(String filename) {
        File file = new File(filename);
        return file.exists();
    }

    public void createFile(String filename) {
        if (fileExists(filename)) {
            return;
        }
        try {
            File file = new File(filename);
            file.createNewFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void save(MusicCollection musicCollection, String filename) {
        if (!fileExists(filename)) {
            createFile(filename);
        }
        try (FileOutputStream fos = new FileOutputStream(filename);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(musicCollection);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public MusicCollection load(String filename) {
        if (!fileExists(filename)) {
            return null;
        }
        try (FileInputStream fis = new FileInputStream(filename);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (MusicCollection) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean delete(String filename) {
        if (!fileExists(filename)) {
            return true;
        }
        File file = new File(filename);
        return file.delete();
    }
}
