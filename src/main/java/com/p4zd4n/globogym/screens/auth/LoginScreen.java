package com.p4zd4n.globogym.screens.auth;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entities.Coach;
import com.p4zd4n.globogym.entities.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class LoginScreen {

    private Main main;

    private BorderPane borderPane;
    private HBox topContainer;
    private GridPane centerContainer;
    private HBox bottomContainer;

    private Label errorLabel;
    private Label usernameOrEmailLabel;
    private Label passwordLabel;

    private TextField usernameOrEmailField;
    private PasswordField passwordField;

    private Button loginButton;
    private Button backButton;

    public LoginScreen(Main main) {

        this.main = main;
    }

    public Pane getView() {

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));

        errorLabel = new Label();
        errorLabel.getStyleClass().add("label");
        errorLabel.getStyleClass().add("error");

        topContainer = new HBox();
        topContainer.setAlignment(Pos.CENTER);
        topContainer.getChildren().add(errorLabel);

        centerContainer = new GridPane();
        centerContainer.getStyleClass().add("container");
        centerContainer.setPadding(new Insets(10, 10, 10, 10));
        centerContainer.setVgap(5);
        centerContainer.setHgap(5);

        usernameOrEmailLabel = new Label("Email / Username:");
        usernameOrEmailLabel.getStyleClass().add("label");
        usernameOrEmailField = new TextField();
        usernameOrEmailField.getStyleClass().add("field");

        passwordLabel = new Label("Password:");
        passwordLabel.getStyleClass().add("label");
        passwordField = new PasswordField();
        passwordField.getStyleClass().add("field");

        loginButton = new Button("Login");
        loginButton.getStyleClass().add("button");
        loginButton.setOnAction(e -> login());

        backButton = new Button("Back");
        backButton.getStyleClass().add("button");
        backButton.setOnAction(e -> main.showMainScreen());

        centerContainer.add(usernameOrEmailLabel, 0, 0);
        centerContainer.add(usernameOrEmailField, 1, 0);
        centerContainer.add(passwordLabel, 0, 1);
        centerContainer.add(passwordField, 1, 1);
        centerContainer.add(loginButton, 1, 2);
        centerContainer.add(backButton, 1, 3);

        bottomContainer = new HBox(10);
        bottomContainer.setAlignment(Pos.CENTER);
        bottomContainer.getChildren().add(backButton);
        bottomContainer.getChildren().add(loginButton);

        borderPane.setTop(topContainer);
        borderPane.setCenter(centerContainer);
        borderPane.setBottom(bottomContainer);

        return borderPane;
    }

    private void login() {

        User user = User.findByUsername(usernameOrEmailField.getText());

        if (user == null) {

            user = User.findByEmail(usernameOrEmailField.getText());
        }

        if (user == null) {

            errorLabel.setText("User not found");
            return;
        }

        if (!user.getPassword().equals(passwordField.getText())) {

            errorLabel.setText("Invalid password");
            return;
        }

        if (user instanceof Coach coach && !coach.isActive()) {
            errorLabel.setText("Your account is inactive");
            return;
        }

        main.showHomeScreen(user);
        usernameOrEmailField.clear();
        passwordField.clear();
        errorLabel.setText("");
    }
}
