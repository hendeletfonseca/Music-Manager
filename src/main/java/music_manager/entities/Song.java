package music_manager.entities;

import java.util.Date;

public class Song extends Music {
    private Lyric lyric;

    public Song(String title, Duration duration, String authors, Date date, String genre, Lyric lyric) {
        super(title, duration, authors, date, genre);
        this.lyric = lyric;
    }

    public String toString() {
        String s = super.toString();
        return "Cancao{" + s + "letra = " + lyric + "}";
    }

    public Lyric getLyric() {
        return lyric;
    }

    public void setLyric(Lyric lyric) {
        this.lyric = lyric;
    }
}
