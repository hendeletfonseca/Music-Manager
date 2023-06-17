package musicmanager.application.model;

import musicmanager.application.util.Random;

import java.io.Serializable;
import java.util.Date;

public abstract class Music implements Serializable{
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
        this.id = Random.getRandomInt();
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

    public String toString() {
        return title + " - " + authors + " id: " + id;
    }
}
