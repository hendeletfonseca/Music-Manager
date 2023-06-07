package music_manager.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Music Manager");

        // Create labels and text fields
        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();

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

        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }
}
