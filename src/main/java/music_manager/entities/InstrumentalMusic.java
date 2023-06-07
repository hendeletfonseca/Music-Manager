package music_manager.entities;

import java.util.Date;

public class InstrumentalMusic extends Music{

    private String fileName;
    public InstrumentalMusic(String title, Duration duration, String authors, Date date, String genre, String fileName) {
        super(title, duration, authors, date, genre);
        this.fileName = fileName;
    }

    public String toString() {
        String s = super.toString();
        return "Musica Instrumental{" + s + "Nome do arquivo da partitura = " + fileName + "}";
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
