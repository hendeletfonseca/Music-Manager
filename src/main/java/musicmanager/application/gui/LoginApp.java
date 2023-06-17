package musicmanager.application.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import musicmanager.application.model.AdminUser;
import musicmanager.application.model.DefaultUser;
import musicmanager.application.model.TYPE_USER;
import musicmanager.application.persistence.UserPersistence;
import musicmanager.application.security.LoginManager;
import musicmanager.application.service.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class LoginApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login App");

        // Create labels and text fields
        TextField loginField = new TextField();
        PasswordField passwordField = new PasswordField();

        // Create buttons
        Button loginButton = createButton("Login");
        Button createAccountButton = createButton("Criar Conta");
        Button recoverAccountButton = createButton("Recuperar Conta");

        // Set event handlers for the buttons
        loginButton.setOnAction(event -> {
            try {
                handleLoginButton(loginField.getText(), passwordField.getText());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        createAccountButton.setOnAction(event -> {
            try {
                handleCreateAccountButton(loginField.getText(), passwordField.getText());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                //System.out.println(loginField.getText() + " " + passwordField.getText());
            }
        });
        recoverAccountButton.setOnAction(event -> {
            try {
                handleRecoverAccountButton(loginField.getText(), passwordField.getText());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        // Create grid pane layout
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(10));

        // Add labels and text fields to the grid pane
        root.add(loginField, 0, 0);
        root.add(passwordField, 0, 1);

        // Add buttons to the grid pane
        root.add(loginButton, 0, 2);
        root.add(createAccountButton, 0, 3);
        root.add(recoverAccountButton, 0, 4);

        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(150);
        return button;
    }

    private void handleLoginButton(String login, String password) {
        boolean loginValid = LoginManager.checkLogin(login, password);
        if (loginValid) {
            TYPE_USER type = UserPersistence.getUserType(login);

            switch (type) {
                case DEFAULT_USER -> {
                    DefaultUser DefaultUser = (DefaultUser) UserPersistence.loadUser(login);
                    DefaultUserApp app = new DefaultUserApp(DefaultUser);
                    try {
                        app.start(new Stage(), DefaultUser);
                    }catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case ADMIN_USER -> {
                    AdminUser adminUser = (AdminUser) UserPersistence.loadUser(login);
                    AdminUserApp adminApp = new AdminUserApp(adminUser);
                    try {
                        adminApp.start(new Stage(), adminUser);
                    }catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText("Login failed");
            alert.setContentText("Invalid login or password");
            alert.showAndWait();
        }
    }

    private void handleCreateAccountButton(String login, String password) {
        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Create Account");
        nameDialog.setHeaderText("Enter Your Name");
        nameDialog.setContentText("Name:");

        Optional<String> nameResult = nameDialog.showAndWait();

        if (nameResult.isPresent()) {
            String name = nameResult.get();

            if (!UserPersistence.userAlreadyExist(login)) {
                List<TYPE_USER> userTypes = Arrays.asList(TYPE_USER.DEFAULT_USER, TYPE_USER.ADMIN_USER);

                ChoiceDialog<TYPE_USER> dialog = new ChoiceDialog<>(TYPE_USER.DEFAULT_USER, userTypes);
                dialog.setTitle("Create Account");
                dialog.setHeaderText("Select User Type");
                dialog.setContentText("User Type:");

                Optional<TYPE_USER> result = dialog.showAndWait();
                result.ifPresent(userType -> {
                    switch (userType) {
                        case DEFAULT_USER:
                            Logger.createUser(name, login, password, TYPE_USER.DEFAULT_USER);
                            LoginManager.saveLogin(login, password);

                            break;
                        case ADMIN_USER:
                            Logger.createUser(name, login, password, TYPE_USER.ADMIN_USER);
                            LoginManager.saveLogin(login, password);

                            break;
                    }
                });
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Create Account Error");
                alert.setHeaderText("Create Account failed");
                alert.setContentText("Username already exists");
                alert.showAndWait();
            }
        }
    }


    private void handleRecoverAccountButton(String login, String password) {
        boolean userExists = UserPersistence.userAlreadyExist(login);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Recover Account");
        if (userExists) {
            LoginManager.updateLogin(login, password);
            alert.setContentText("Account recovered successfully");
            alert.showAndWait();
        }
        else {
            alert.setContentText("Account not found");
            alert.showAndWait();
        }
    }
}
