package music_manager.collections;

import music_manager.entities.Music;

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
    public void print() {
        for (Music m: musics) {
            System.out.println(m.toString());
        }
    }

    public void load() {

    }
}
