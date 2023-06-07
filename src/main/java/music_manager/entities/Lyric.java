package music_manager.entities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Lyric {
    private String fileName;
    private String text;

    public Lyric(String fileName) {
        this.fileName = fileName;
        this.text = "";
        try (FileReader fr = new FileReader(fileName);
             BufferedReader br = new BufferedReader(fr)) {

            boolean eof = false;

            StringBuilder strBuilder = new StringBuilder(this.text);
            do {
                String s = br.readLine();
                if (s == null) {
                    eof = true;
                } else {
                    strBuilder.append(s);
                    strBuilder.append("\n");
                }
            } while (!eof);
            this.text = strBuilder.toString();

        } catch (IOException ex) {
            Logger.getLogger(Lyric.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "Lyric{" + "fileName=" + fileName + ", text=" + text + '}';
    }

}
