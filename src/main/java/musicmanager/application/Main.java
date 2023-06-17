package musicmanager.application;

import javafx.application.Application;
import musicmanager.application.gui.LoginApp;
import musicmanager.application.model.*;
import musicmanager.application.persistence.MusicCollectionPersistence;
import musicmanager.application.persistence.UserPersistence;
import musicmanager.application.service.Logger;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Application.launch(LoginApp.class);
    }
}
