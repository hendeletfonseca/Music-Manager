package musicmanager.application.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import musicmanager.application.model.DefaultUser;
import musicmanager.application.model.Music;
import musicmanager.application.model.MusicCollection;
import musicmanager.application.model.MusicCollectionArrayList;
import musicmanager.application.persistence.MusicCollectionPersistence;

import java.util.Optional;

public class DefaultUserApp extends Application {
    private DefaultUser defaultUser;
    public DefaultUserApp(DefaultUser defaultUser) {
        this.defaultUser = defaultUser;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        System.out.println("Default User App started");
    }
    public void start(Stage primaryStage, DefaultUser defaultUser) {
        primaryStage.setTitle("Default User App");

        // Create a button
        Button createMusicCollection = new Button("Create Music Collection");
        Button addMusic = new Button("Add Music");
        Button deleteMusic = new Button("Delete Music");
        Button searchMusicFromParticularCollection = new Button("Search Music From Particular Collection");
        Button searchMusicFromAllMusics = new Button("Search Music From All Musics");
        Button deleteAccount = new Button("Delete Account");

        // Set event handler for the button
        createMusicCollection.setOnAction(event -> handleCreateMusicCollectionButton());
        addMusic.setOnAction(event -> handleAddMusicButton());
        deleteMusic.setOnAction(event -> handleDeleteMusicButton());
        searchMusicFromParticularCollection.setOnAction(event -> handleSearchMusicFromParticularCollectionButton());
        searchMusicFromAllMusics.setOnAction(event -> handleSearchMusicFromAllMusicsButton());
        deleteAccount.setOnAction(event -> handleDeleteAccountButton());

        // Create a layout pane and add the button
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(10));

        root.add(createMusicCollection, 0, 0);
        root.add(addMusic,0,1);
        root.add(deleteMusic,0,2);
        root.add(searchMusicFromParticularCollection,0,3);
        root.add(searchMusicFromAllMusics,0,4);
        root.add(deleteAccount,0,5);

        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }
    public void handleCreateMusicCollectionButton() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Create Particular Collection");
        alert.setHeaderText("Create Particular Collection");
        if (!MusicCollectionPersistence.fileExists(MusicCollectionPersistence.PARTICULAR_COL_DIR + defaultUser.getId() + ".bin")) {
            MusicCollectionPersistence.createFile(MusicCollectionPersistence.PARTICULAR_COL_DIR + defaultUser.getId() + ".bin");
            alert.setContentText("Particular Collection created successfully!");
            alert.showAndWait();
        }
        else {
            alert.setContentText("Particular Collection already exists!");
            alert.showAndWait();
        }
    }
    public void handleAddMusicButton() {
        String dir = MusicCollectionPersistence.PARTICULAR_COL_DIR + defaultUser.getId() + ".bin";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Music");
        TextInputDialog titleDialog = new TextInputDialog();
        titleDialog.setTitle("Add music");
        titleDialog.setHeaderText("Enter Music Title");
        titleDialog.setContentText("Title:");

        Optional<String> titleResult = titleDialog.showAndWait();

        if (titleResult.isPresent()) {
            String title = titleResult.get();
            MusicCollection allmusics = MusicCollectionPersistence.load(MusicCollectionPersistence.ALL_MUSICS_DIR);
            if (allmusics != null){
                Music music = allmusics.searchMusic(title);
                if (music != null) {
                    MusicCollection particularmusics = MusicCollectionPersistence.load(dir);
                    if (particularmusics != null) {
                        particularmusics.addMusic(music);
                        MusicCollectionPersistence.save(particularmusics, dir);
                    }else {
                        MusicCollectionArrayList mcal = new MusicCollectionArrayList();
                        mcal.addMusic(music);
                        MusicCollectionPersistence.save(mcal, dir);
                    }

                }else {
                    alert.setContentText("Music not found!");
                    alert.showAndWait();
                }
            }
        }
        MusicCollectionPersistence.load(dir).show();
    }
    public void handleDeleteMusicButton() {
        System.out.println("Delete Music Button clicked");
        // Add custom logic for delete music
    }
    public void handleSearchMusicFromParticularCollectionButton() {
        System.out.println("Search Music From Particular Collection Button clicked");
        // Add custom logic for search music from particular collection
    }
    public void handleSearchMusicFromAllMusicsButton() {
        System.out.println("Search Music From All Musics Button clicked");
        // Add custom logic for search music from all musics
    }
    public void handleDeleteAccountButton() {
        System.out.println("Delete Account Button clicked");
        // Add custom logic for delete account
    }
}
