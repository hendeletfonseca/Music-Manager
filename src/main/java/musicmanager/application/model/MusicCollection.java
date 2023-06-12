package musicmanager.application.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public abstract class MusicCollection implements Collection<Music>, Serializable {

    private int qtdMusics;
    protected Collection<Music> musics;

    public MusicCollection(){
        qtdMusics = 0;
    }

    public int getQtdMusics() {
        return qtdMusics;
    }

    public void setQtdMusics(int qtdMusics) {
        this.qtdMusics = qtdMusics;
    }

    public Collection<Music> getMusics() {
        return musics;
    }

    public void setMusics(Collection<Music> musics) {
        this.musics = musics;
    }
    public abstract Music searchMusic(String title);
    public abstract boolean addMusic(Music music);
    public abstract Music removeMusic(Music music);
    public abstract boolean updateMusic(Music music);
    public abstract void show();

}
