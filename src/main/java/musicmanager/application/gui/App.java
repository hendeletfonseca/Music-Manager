package musicmanager.application.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import musicmanager.application.model.AdminUser;
import musicmanager.application.model.DefaultUser;
import musicmanager.application.model.TYPE_USER;
import musicmanager.application.persistence.UserPersistence;
import musicmanager.application.service.Logger;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Music Manager");

        // Create labels and text fields
        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();

        // Create buttons
        Button loginButton = new Button("Login");
        Button newUserButton = new Button("New User");

        // Set event handlers for the buttons
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String login = usernameField.getText();
                String password = passwordField.getText();
                TYPE_USER userType = UserPersistence.getUserType(login);

                if (userType == TYPE_USER.DEFAULT_USER) {
                    DefaultUser user = (DefaultUser) Logger.userCreator(login, password);
                    System.out.println("Bem vindo " + user.getName());
                }
                if (userType == TYPE_USER.ADMIN_USER) {
                    AdminUser user = (AdminUser) Logger.userCreator(login, password);
                    System.out.println("Bem vindo administrador" + user.getName());
                }
                System.out.println("login");
            }
        });

        newUserButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String login = usernameField.getText();
                String password = passwordField.getText();
                boolean ok = Logger.createUser(login, login, password, TYPE_USER.DEFAULT_USER);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("User Creation");

                if (ok) {
                    alert.setHeaderText("Success");
                    alert.setContentText("User created.");
                } else {
                    alert.setHeaderText("Error");
                    alert.setContentText("Failed to create user.");
                }

                alert.showAndWait();
            }
        });

        // Create grid pane layout
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(10));

        // Add labels and text fields to the grid pane
        root.add(usernameLabel, 0, 0);
        root.add(usernameField, 1, 0);
        root.add(passwordLabel, 0, 1);
        root.add(passwordField, 1, 1);

        // Create an HBox for the buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(loginButton, newUserButton);

        // Create a VBox to hold the grid pane and buttons
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(root, buttonBox);

        primaryStage.setScene(new Scene(vbox, 400, 400));
        primaryStage.show();
    }
}
