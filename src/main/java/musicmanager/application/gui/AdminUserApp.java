package musicmanager.application.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import musicmanager.application.model.*;
import musicmanager.application.persistence.MusicCollectionPersistence;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AdminUserApp extends Application {
    private AdminUser adminUser;
    public AdminUserApp(AdminUser adminUser) {
        this.adminUser = adminUser;
    }
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        System.out.println("Default User App started");
    }
    public void start(Stage primaryStage, AdminUser adminUser) {
        primaryStage.setTitle("Admin User App");

        Button registerMusic = new Button("Register Music");
        Button searchMusic = new Button("Search Music");
        Button updateMusic = new Button("Update Music");
        Button deleteMusic = new Button("Delete Music");
        Button addUser = new Button("Add User");
        Button searchUser = new Button("Search User");
        Button deleteUser = new Button("Delete User");

        registerMusic.setOnAction(event -> handleRegisterMusicButton());
        searchMusic.setOnAction(event -> handleSearchMusicButton());
        updateMusic.setOnAction(event -> handleUpdateMusicButton());
        deleteMusic.setOnAction(event -> handleDeleteMusicButton());
        addUser.setOnAction(event -> handleAddUserButton());
        searchUser.setOnAction(event -> handleSearchUserButton());
        deleteUser.setOnAction(event -> handleDeleteUserButton());

        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(10));

        root.add(registerMusic, 0, 0);
        root.add(searchMusic, 0, 1);
        root.add(updateMusic, 0, 2);
        root.add(deleteMusic, 0, 3);
        root.add(addUser, 0, 4);
        root.add(searchUser, 0, 5);
        root.add(deleteUser, 0, 6);

        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();

    }
    public void handleRegisterMusicButton() {
        // Create dialog components
        Label titleLabel = new Label("Title:");
        TextField titleField = new TextField();

        Label durationLabel = new Label("Duration:");
        TextField durationField = new TextField();

        Label authorLabel = new Label("Author:");
        TextField authorField = new TextField();

        Label dateLabel = new Label("Date:");
        TextField dateField = new TextField();

        Label genreLabel = new Label("Genre:");
        TextField genreField = new TextField();

        GridPane dialogContent = new GridPane();
        dialogContent.setVgap(10);
        dialogContent.addRow(0, titleLabel, titleField);
        dialogContent.addRow(1, durationLabel, durationField);
        dialogContent.addRow(2, authorLabel, authorField);
        dialogContent.addRow(3, dateLabel, dateField);
        dialogContent.addRow(4, genreLabel, genreField);

        // Create dialog
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle("Register Music");
        dialog.setHeaderText("Add Music Details");
        dialog.getDialogPane().setContent(dialogContent);

        // Show dialog and handle user's response
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String title = titleField.getText();
                String durationText = durationField.getText();
                String author = authorField.getText();
                String dateText = dateField.getText();
                String genre = genreField.getText();
                durationText = durationText.replace(":", "");
                dateText = dateText.replace("/", "");
                int min = Integer.parseInt(durationText.substring(0,1));
                int seg = Integer.parseInt(durationText.substring(2,3));
                int day = Integer.parseInt(dateText.substring(0,1));
                int month = Integer.parseInt(dateText.substring(2,3));
                int year = Integer.parseInt(dateText.substring(4,7));
                Duration duration = new Duration(min,seg);
                Date date = new Date(year,month,day);

                List<String> musicTypes = Arrays.asList("Song", "Instrumental Music");
                ChoiceDialog<String> dialogMusicType = new ChoiceDialog<>("Song", musicTypes);
                dialog.setTitle("Select Music Type");
                dialog.setHeaderText("Select Music Type");
                dialog.setContentText("Music Type:");
                Optional<String> result = dialogMusicType.showAndWait();
                result.ifPresent(musicType -> {
                    switch (musicType) {
                        case "Song":
                            TextInputDialog dialogLyric = new TextInputDialog();
                            dialog.setTitle("Lyric");
                            dialog.setHeaderText("Add Lyric fileName");
                            dialog.setContentText("Lyric fileName:");
                            Optional<String> resultLyric = dialogLyric.showAndWait();
                            Lyric lyric;
                            if (resultLyric.isPresent()) {
                                lyric = new Lyric(resultLyric.get());
                                Song song = new Song(title, duration, author, date, genre, lyric);
                                MusicCollection allmusics = MusicCollectionPersistence.load(MusicCollectionPersistence.ALL_MUSICS_DIR);
                                System.out.println("all musics: " + allmusics);
                                allmusics.addMusic(song);
                                MusicCollectionPersistence.save(allmusics, MusicCollectionPersistence.ALL_MUSICS_DIR);
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Information Dialog");
                                alert.setHeaderText(null);
                                alert.setContentText("Music Registered!");
                                alert.showAndWait();
                            }
                            break;
                        case "Instrumental Music":
                            TextInputDialog dialogSheetMusic = new TextInputDialog();
                            dialog.setTitle("Sheet Music");
                            dialog.setHeaderText("Add Sheet Music FileName");
                            dialog.setContentText("Sheet Music File Name:");
                            Optional<String> resultSheetMusic = dialogSheetMusic.showAndWait();
                            if (resultSheetMusic.isPresent()) {
                                InstrumentalMusic instrumentalMusic = new InstrumentalMusic(title, duration, author, date, genre, resultSheetMusic.get());
                                MusicCollection allmusics = MusicCollectionPersistence.load(MusicCollectionPersistence.ALL_MUSICS_DIR);
                                allmusics.addMusic(instrumentalMusic);
                                MusicCollectionPersistence.save(allmusics, MusicCollectionPersistence.ALL_MUSICS_DIR);
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Information Dialog");
                                alert.setHeaderText(null);
                                alert.setContentText("Music Registered!");
                                alert.showAndWait();
                            }
                            break;
                    }
                });

            }
        });
    }

    public void handleSearchMusicButton() {
        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Search Music");
        nameDialog.setHeaderText("Enter Music Title");
        nameDialog.setContentText("Title:");

        Optional<String> titleResult = nameDialog.showAndWait();

        if (titleResult.isPresent()) {
            String title = titleResult.get();
            MusicCollection mc = MusicCollectionPersistence.load(MusicCollectionPersistence.ALL_MUSICS_DIR);
            if (mc == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Music found");
                alert.showAndWait();
                return;
            }
            Music music = mc.searchMusic(title);
            if (music == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Music not found");
                alert.setContentText("Music not found");
                alert.showAndWait();
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Music found");
                alert.setContentText(music.toString());
                alert.showAndWait();
            }
        }
    }
    public void handleUpdateMusicButton() {
        System.out.println();
    }
    public void handleDeleteMusicButton() {
        System.out.println("Delete Music Button Clicked");
    }
    public void handleAddUserButton() {
        System.out.println("Add User Button Clicked");
    }
    public void handleSearchUserButton() {
        System.out.println("Search User Button Clicked");
    }
    public void handleDeleteUserButton() {
        System.out.println("Delete User Button Clicked");
    }
}
