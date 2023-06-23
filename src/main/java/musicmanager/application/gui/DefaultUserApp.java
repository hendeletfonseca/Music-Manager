package musicmanager.application.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import musicmanager.application.model.DefaultUser;
import musicmanager.application.model.Music;
import musicmanager.application.model.MusicCollection;
import musicmanager.application.model.MusicCollectionArrayList;
import musicmanager.application.persistence.MusicCollectionPersistence;
import musicmanager.application.persistence.UserPersistence;
import musicmanager.application.security.LoginManager;

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
        Button showMusics = new Button("See All Musics");

        // Set event handler for the button
        createMusicCollection.setOnAction(event -> handleCreateMusicCollectionButton());
        addMusic.setOnAction(event -> handleAddMusicButton());
        deleteMusic.setOnAction(event -> handleDeleteMusicButton());
        searchMusicFromParticularCollection.setOnAction(event -> handleSearchMusicFromParticularCollectionButton());
        searchMusicFromAllMusics.setOnAction(event -> handleSearchMusicFromAllMusicsButton());
        deleteAccount.setOnAction(event -> handleDeleteAccountButton());
        showMusics.setOnAction(event -> handleShowMusicsButton());

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
        root.add(showMusics,0,6);

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
        titleDialog.setTitle("Add Music");
        titleDialog.setHeaderText("Enter Music Title");
        titleDialog.setContentText("Title:");

        Optional<String> titleResult = titleDialog.showAndWait();

        if (titleResult.isPresent()) {
            String title = titleResult.get();
            MusicCollection allMusics = MusicCollectionPersistence.load(MusicCollectionPersistence.ALL_MUSICS_DIR);
            if (allMusics != null) {
                System.out.println("allMusics is not null");
                Music music = allMusics.searchMusic(title);
                if (music != null) {
                    System.out.println("music is not null");
                    MusicCollection particularCollection = MusicCollectionPersistence.load(dir);
                    if (particularCollection == null){
                        particularCollection = new MusicCollectionArrayList();
                    }
                    particularCollection.addMusic(music);
                    MusicCollectionPersistence.save(particularCollection, dir);
                    alert.setContentText("Music added successfully!");
                    alert.showAndWait();
                }else {
                    alert.setContentText("Music not found!");
                    alert.showAndWait();
                }
            }else {
                alert.setContentText("Music not found!");
                alert.showAndWait();
            }
        }
    }
    public void handleDeleteMusicButton() {
        String dir = MusicCollectionPersistence.PARTICULAR_COL_DIR + defaultUser.getId() + ".bin";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Remove Music");
        TextInputDialog titleDialog = new TextInputDialog();
        titleDialog.setTitle("Remove Music");
        titleDialog.setHeaderText("Enter Music Title");
        titleDialog.setContentText("Title:");

        Optional<String> titleResult = titleDialog.showAndWait();

        if (titleResult.isPresent()) {
            String title = titleResult.get();
            MusicCollection particularCollection = MusicCollectionPersistence.load(dir);
            if (particularCollection == null) {
                alert.setContentText("Music not found!");
                alert.showAndWait();
                return;
            }
            Music music = particularCollection.removeMusic(particularCollection.searchMusic(title));
            if (music != null) {
                MusicCollectionPersistence.save(particularCollection, dir);
                alert.setContentText("Music removed successfully!");
                alert.showAndWait();
            }else {
                alert.setContentText("Music not found!");
                alert.showAndWait();
            }
        }
    }
    public void handleSearchMusicFromParticularCollectionButton() {
        String dir = MusicCollectionPersistence.PARTICULAR_COL_DIR + defaultUser.getId() + ".bin";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Search Music");
        TextInputDialog titleDialog = new TextInputDialog();
        titleDialog.setTitle("Search Music");
        titleDialog.setHeaderText("Enter Music Title");
        titleDialog.setContentText("Title:");

        Optional<String> titleResult = titleDialog.showAndWait();

        if (titleResult.isPresent()) {
            String title = titleResult.get();
            MusicCollection particularCollection = MusicCollectionPersistence.load(dir);
            if (particularCollection != null) {
                Music music = particularCollection.searchMusic(title);
                if (music != null) {
                    alert.setContentText("Music found!\n" + music.toString());
                    alert.showAndWait();
                }else {
                    alert.setContentText("Music not found!");
                    alert.showAndWait();
                }
            }else {
                alert.setContentText("Music not found!");
                alert.showAndWait();
            }
        }
    }
    public void handleSearchMusicFromAllMusicsButton() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Search Music");
        TextInputDialog titleDialog = new TextInputDialog();
        titleDialog.setTitle("Search Music");
        titleDialog.setHeaderText("Enter Music Title");
        titleDialog.setContentText("Title:");

        Optional<String> titleResult = titleDialog.showAndWait();

        if (titleResult.isPresent()) {
            String title = titleResult.get();
            MusicCollection allMusics = MusicCollectionPersistence.load(MusicCollectionPersistence.ALL_MUSICS_DIR);
            Music music = allMusics.searchMusic(title);
            if (music != null) {
                alert.setContentText("Music found!\n" + music.toString());
                alert.showAndWait();
            }else {
                alert.setContentText("Music not found!");
                alert.showAndWait();
            }
        }
    }
    public void handleDeleteAccountButton() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Delete Account");
        alert.setHeaderText("Delete Account");
        alert.setContentText("Are you sure you want to delete your account?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            UserPersistence.deleteUser(defaultUser.getLogin());
            LoginManager.deleteLogin(defaultUser.getLogin());
            boolean ok = MusicCollectionPersistence.delete(MusicCollectionPersistence.PARTICULAR_COL_DIR + defaultUser.getId() + ".bin");
            System.out.println(ok);
            alert.setContentText("Account deleted successfully!");
            alert.showAndWait();
            System.exit(0);
        } else {
            alert.setContentText("Account not deleted!");
            alert.showAndWait();
        }
    }

    public void handleShowMusicsButton() {
        String dir = MusicCollectionPersistence.PARTICULAR_COL_DIR + defaultUser.getId() + ".bin";
        MusicCollection particularCollection = MusicCollectionPersistence.load(dir);
        MusicCollection allMusic = MusicCollectionPersistence.load(MusicCollectionPersistence.ALL_MUSICS_DIR);

        Stage stage = new Stage();
        stage.setTitle("Music Collection");

        VBox content = new VBox();
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);

        if ((particularCollection != null) && (allMusic != null)) {
            Label labelMyMusics = new Label("My collection musics:\n");
            content.getChildren().add(labelMyMusics);
            for (Music music : particularCollection.getMusics()) {
                Label label = new Label(music.toString() + "\n");
                content.getChildren().add(label);
            }
            Label labelAllMusics = new Label("All musics:\n");
            content.getChildren().add(labelAllMusics);
            for (Music music : allMusic.getMusics()) {
                Label label = new Label(music.toString() + "\n");
                content.getChildren().add(label);
            }
        } else {
            Label label = new Label("No music found in the collection.");
            content.getChildren().add(label);
        }

        Scene scene = new Scene(scrollPane, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

}
