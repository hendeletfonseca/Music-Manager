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
import musicmanager.application.persistence.UserPersistence;
import musicmanager.application.security.LoginManager;
import musicmanager.application.service.Logger;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AdminUserApp extends Application {
    private AdminUser adminUser;
    private MusicCollection musicCollection;
    public AdminUserApp(AdminUser adminUser) {
        this.adminUser = adminUser;
        this.musicCollection = MusicCollectionPersistence.load(MusicCollectionPersistence.ALL_MUSICS_DIR);
    }

    public AdminUser getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(AdminUser adminUser) {
        this.adminUser = adminUser;
    }

    public MusicCollection getMusicCollection() {
        return musicCollection;
    }

    public void setMusicCollection(MusicCollection musicCollection) {
        this.musicCollection = musicCollection;
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
                                System.out.println("all musics: " + musicCollection);
                                musicCollection.add(song);
                                MusicCollectionPersistence.save(musicCollection, MusicCollectionPersistence.ALL_MUSICS_DIR);
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
                                System.out.println("all musics: " + musicCollection);
                                musicCollection.addMusic(instrumentalMusic);
                                MusicCollectionPersistence.save(musicCollection, MusicCollectionPersistence.ALL_MUSICS_DIR);
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
            if (musicCollection == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Music found");
                alert.showAndWait();
                return;
            }
            Music music = musicCollection.searchMusic(title);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            if (music == null) {
                alert.setHeaderText("Music not found");
                alert.setContentText("Music not found");
            }else {
                alert.setHeaderText("Music found");
                alert.setContentText(music.toString());
            }
            alert.showAndWait();
        }
    }
    public void handleUpdateMusicButton() {
        TextInputDialog titleDialog = new TextInputDialog();
        titleDialog.setTitle("Update Music");
        titleDialog.setHeaderText("Enter Music Title");
        titleDialog.setContentText("Title:");

        Optional<String> titleResult = titleDialog.showAndWait();

        if (titleResult.isPresent()) {
            String title = titleResult.get();
            if (musicCollection == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Music found");
                alert.showAndWait();
                return;
            }
            Music music = musicCollection.searchMusic(title);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            if (music == null) {
                alert.setHeaderText("Music not found");
                alert.setContentText("Music not found");
            }else {
                alert.setHeaderText("Music found and deleted");
                alert.setContentText(music.toString());
                musicCollection.removeMusic(music);
                handleRegisterMusicButton();
                MusicCollectionPersistence.save(musicCollection, MusicCollectionPersistence.ALL_MUSICS_DIR);
            }
        }
    }
    public void handleDeleteMusicButton() {
        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Delete Music");
        nameDialog.setHeaderText("Enter Music Title");
        nameDialog.setContentText("Title:");

        Optional<String> titleResult = nameDialog.showAndWait();

        if (titleResult.isPresent()) {
            String title = titleResult.get();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            Music music = musicCollection.searchMusic(title);
            if (musicCollection == null || music == null) {
                alert.setHeaderText("Music not found");
                alert.showAndWait();
                return;
            }
            musicCollection.removeMusic(music);
            alert.setHeaderText("Music found and deleted");
            alert.showAndWait();
        }
    }
    public void handleAddUserButton() {
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();

        Label loginLabel = new Label("Login:");
        TextField loginField = new TextField();

        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();

        GridPane dialogContent = new GridPane();
        dialogContent.setVgap(10);
        dialogContent.addRow(0, nameLabel, nameField);
        dialogContent.addRow(1, loginLabel, loginField);
        dialogContent.addRow(2, passwordLabel, passwordField);

        // Create dialog
        Alert dialog1 = new Alert(Alert.AlertType.CONFIRMATION);
        dialog1.setTitle("Register User");
        dialog1.setHeaderText("Add User Details");
        dialog1.getDialogPane().setContent(dialogContent);


        dialog1.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String name = nameField.getText();
                String login = loginField.getText();
                String password = passwordField.getText();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                if (!UserPersistence.userAlreadyExist(login)) {
                    List<TYPE_USER> userTypes = Arrays.asList(TYPE_USER.DEFAULT_USER, TYPE_USER.ADMIN_USER);

                    ChoiceDialog<TYPE_USER> dialog = new ChoiceDialog<>(TYPE_USER.DEFAULT_USER, userTypes);
                    dialog.setTitle("Create Account");
                    dialog.setHeaderText("Select User Type");
                    dialog.setContentText("User Type:");

                    Optional<TYPE_USER> result = dialog.showAndWait();
                    result.ifPresent(userType -> {
                        Logger.createUser(name, login, password, userType);
                        LoginManager.saveLogin(login, password);
                    });
                }else {
                    alert.setHeaderText("User already exist");
                    alert.showAndWait();
                }
            }
        });
    }
    public void handleSearchUserButton() {
        TextInputDialog loginDialog = new TextInputDialog();

        loginDialog.setTitle("Search User");
        loginDialog.setHeaderText("Enter User Login");
        loginDialog.setContentText("Login:");

        Optional<String> loginResult = loginDialog.showAndWait();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        if (loginResult.isPresent() && UserPersistence.userAlreadyExist(loginResult.get())) {
            alert.setHeaderText("User found");
            String userLogin = loginResult.get();
            User user = UserPersistence.loadUser(userLogin);
            if (user != null) {
                alert.setContentText(user.toString());
                alert.showAndWait();
            }
        }else {
            alert.setHeaderText("User not found");
            alert.showAndWait();
        }
    }
    public void handleDeleteUserButton() {
        TextInputDialog loginDialog = new TextInputDialog();

        loginDialog.setTitle("Delete User");
        loginDialog.setHeaderText("Enter User Login");
        loginDialog.setContentText("Login:");

        Optional<String> loginResult = loginDialog.showAndWait();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        if (loginResult.isPresent() && UserPersistence.userAlreadyExist(loginResult.get())) {
            LoginManager.deleteLogin(loginResult.get());
            UserPersistence.deleteUser(loginResult.get());
            alert.setHeaderText("User deleted");
            alert.showAndWait();
        }else {
            alert.setHeaderText("User not found");
            alert.showAndWait();
        }
    }
}
