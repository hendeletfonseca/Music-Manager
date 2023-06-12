package musicmanager.application.model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

public class InstrumentalMusic extends Music{

    private String sheetMusicFileName;
    private static final String DIR = "src/datas/sheet_musics/";

    public InstrumentalMusic(String title, Duration duration, String authors, Date date, String genre, String sheetMusicFileName) {
        super(title, duration, authors, date, genre);
        this.sheetMusicFileName = DIR + sheetMusicFileName + ".png";
    }

    public InstrumentalMusic(int id, String title, Duration duration, String authors, Date date, String genre, String sheetMusicFileName) {
        super(id, title, duration, authors, date, genre);
        this.sheetMusicFileName = DIR + sheetMusicFileName + ".png";
    }

    public String getSheetMusicFileName() {
        return sheetMusicFileName;
    }

    public void setSheetMusicFileName(String sheetMusicFileName) {
        this.sheetMusicFileName = sheetMusicFileName;
    }
    public String toString() {
        return "\nSheet Music: " + super.toString() + " - sheetMusicFileName" + sheetMusicFileName;
    }

}
