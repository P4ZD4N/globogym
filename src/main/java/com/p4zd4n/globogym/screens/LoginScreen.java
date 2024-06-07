package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.User;
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

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10, 10, 10, 10));

        GridPane centerContainer = new GridPane();
        centerContainer.getStyleClass().add("container");
        centerContainer.setPadding(new Insets(10, 10, 10, 10));
        centerContainer.setVgap(5);
        centerContainer.setHgap(5);

        usernameOrEmailLabel = new Label("Email / Username:");
        usernameOrEmailField = new TextField();

        passwordLabel = new Label("Password:");
        passwordField = new PasswordField();

        loginButton = new Button("Login");
        loginButton.setOnAction(e -> login());

        backButton = new Button("Back");
        backButton.setOnAction(e -> main.showMainScreen());

        centerContainer.add(usernameOrEmailLabel, 0, 0);
        centerContainer.add(usernameOrEmailField, 1, 0);
        centerContainer.add(passwordLabel, 0, 1);
        centerContainer.add(passwordField, 1, 1);
        centerContainer.add(loginButton, 1, 2);
        centerContainer.add(backButton, 1, 3);

        HBox bottomContainer = new HBox();
        bottomContainer.setAlignment(Pos.CENTER);
        bottomContainer.getChildren().add(backButton);
        bottomContainer.getChildren().add(loginButton);

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

            System.out.println("User not found");
            return;
        }

        if (!user.getPassword().equals(passwordField.getText())) {

            System.out.println("Invalid password");
            return;
        }

        main.showClubMemberDashboardScreen();
    }
}
