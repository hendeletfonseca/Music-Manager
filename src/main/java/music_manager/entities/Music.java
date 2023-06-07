package music_manager.entities;

import java.util.Date;
public class Music {

    private static int qtdMusics = 0;
    private int id;
    private String title;
    private Duration duration;
    private String authors;
    private Date date;
    private String genre;

    public Music(String title, Duration duration, String authors, Date date, String genre) {
        this.title = title;
        this.duration = duration;
        this.authors = authors;
        this.date = date;
        this.genre = genre;
        qtdMusics++;
        this.id = qtdMusics;
    }

    public static int getQtdMusics() {
        return qtdMusics;
    }

    public static void setQtdMusics(int qtdMusics) {
        Music.qtdMusics = qtdMusics;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Musica{" + "titulo=" + title + ", duracao=" + duration + ", autores=" + authors + ", data=" + date + ", genero=" + genre + '}';
    }
}
