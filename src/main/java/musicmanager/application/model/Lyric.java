package musicmanager.application.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

public class Lyric implements Serializable {
    private String fileName;
    private String text;
    private static final String DIR = "src/datas/lyrics/";
    public Lyric(String fileName) {
        this.fileName = DIR + fileName + ".txt";
        this.text = getLyricText();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String getLyricText() {
        String lyricText = "";
        try (FileReader fr = new FileReader(fileName);
             BufferedReader br = new BufferedReader(fr)) {

            boolean eof = false;

            StringBuilder strBuilder = new StringBuilder(lyricText);
            do {
                String s = br.readLine();
                if (s == null) {
                    eof = true;
                } else {
                    strBuilder.append(s);
                    strBuilder.append("\n");
                }
            } while (!eof);
            return strBuilder.toString();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return "";
    }
    public String toString() {
        return text;
    }
}
