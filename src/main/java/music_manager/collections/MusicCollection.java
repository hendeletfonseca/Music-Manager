package music_manager.collections;

import music_manager.entities.Music;

public abstract class MusicCollection {

    private int qtdMusics;

    public MusicCollection(){
        qtdMusics = 0;
    }

    public int getQtdMusics() {
        return qtdMusics;
    }

    public void setQtdMusics(int qtdMusics) {
        this.qtdMusics = qtdMusics;
    }

    public abstract void add(Music music);
    public abstract Music search(String title);
    public abstract Music search(int id);
    public abstract Music remove(int id);
    public abstract void print();
    public abstract void load();
}
