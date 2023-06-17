package musicmanager.application.model;

import java.util.Date;

public class Song extends Music{

    private Lyric lyric;
    public Song(String title, Duration duration, String authors, Date date, String genre, Lyric lyric) {
        super(title, duration, authors, date, genre);
        this.lyric = lyric;
    }

    public Lyric getLyric() {
        return lyric;
    }

    public void setLyric(Lyric lyric) {
        this.lyric = lyric;
    }

    public String toString() {
        return ("Song: " + super.toString() + "- lyric: " + this.lyric);
    }

}
