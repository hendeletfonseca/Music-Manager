package musicmanager.application.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import musicmanager.application.model.DefaultUser;

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
        Button defaultUserButton = new Button("Default User Button");

        // Set event handler for the button
        defaultUserButton.setOnAction(event -> handleDefaultUserButton());

        // Create a layout pane and add the button
        StackPane root = new StackPane();
        root.getChildren().add(defaultUserButton);

        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }
    private void handleDefaultUserButton() {
        System.out.println("Default User Button clicked");
        System.out.println("Default User: " + defaultUser.getName());
        // Add custom logic for default user actions
    }
}
