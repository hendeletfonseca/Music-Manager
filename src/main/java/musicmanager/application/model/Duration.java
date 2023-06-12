package musicmanager.application.model;

import java.io.Serializable;

public class Duration implements Serializable {

    private int min;
    private int seconds;

    public Duration(int min, int seconds) {
        this.min = min;
        this.seconds = seconds;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public String toString() {
        return "Duracao " + getMin() + ":" + getSeconds();
    }

}
