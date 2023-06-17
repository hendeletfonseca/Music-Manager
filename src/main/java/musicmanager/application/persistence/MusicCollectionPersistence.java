package musicmanager.application.persistence;

import musicmanager.application.model.MusicCollection;

import java.io.*;

public class MusicCollectionPersistence {
    public static final String PARTICULAR_COL_DIR = "src/datas/particularcollections/";
    public static final String ALL_MUSICS_DIR = "src/datas/allmusics.bin";
    public static boolean fileExists(String filename) {
        File file = new File(filename);
        return file.exists();
    }

    public static void createFile(String filename) {
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

    public static void save(MusicCollection musicCollection, String filename) {
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

    public static MusicCollection load(String filename) {
        if (!fileExists(filename)) {
            createFile(filename);
        }
        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return (MusicCollection) ois.readObject();
        }catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            //return new MusicCollection();
        }
        return null;
    }

    public static boolean delete(String filename) {
        if (!fileExists(filename)) {
            return true;
        }
        File file = new File(filename);
        return file.delete();
    }
}
