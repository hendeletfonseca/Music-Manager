package music_manager.collections;

import music_manager.entities.Music;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class MusicCollectionArrayList extends MusicCollection{
    public Collection<Music> musics;
    public MusicCollectionArrayList(){
        super();
        this.musics = new ArrayList<Music>();
    }
    public void add(Music music) {
        musics.add(music);
    }
    public Music search(String title){
        for (Music m: musics) {
            if (m.getTitle().equals(title)){
                return m;
            }
        }
        return null;
    }
    public Music search(int id){
        for (Music m: musics) {
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }
    public Music remove(int id){
        for (Music m: musics) {
            if (m.getId() == id) {
                musics.remove(m);
                return m;
            }
        }
        return null;
    }
    @Override
    public void show() {
        System.out.println("printing music collection");
        for (Music m: musics) {
            System.out.println("printing music");
            System.out.println(m.toString());
        }
    }

    @Override
    public void save(String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {

            for (Music m: musics) {
                outputStream.writeObject(m);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            boolean endOfFile = false;

            while (!endOfFile) {
                try {
                    Music music = (Music) inputStream.readObject();
                    musics.add(music);
                } catch (EOFException e) {
                    endOfFile = true;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("MusicCollectionArrayList - load: particular collection not found");
        }
    }

}
