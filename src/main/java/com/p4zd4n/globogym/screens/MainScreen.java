package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainScreen {

    private Main main;

    private VBox vBox;

    private Image logo;
    private ImageView logoView;

    private Button loginButton;
    private Button registerButton;
    private Button exitButton;

    public MainScreen(Main main) {

        this.main = main;
    }

    public Pane getView() {

        vBox = new VBox();
        vBox.getStyleClass().add("container");
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);

        logo = new Image(getClass().getResource("/img/logo-no-background.png").toString());
        logoView = new ImageView(logo);
        logoView.setFitHeight(96);
        logoView.setFitWidth(300);

        loginButton = new Button("Login");
        loginButton.setOnAction(e -> main.showLoginScreen());

        registerButton = new Button("Register");
        registerButton.setOnAction(e -> main.showRegistrationScreen());

        exitButton = new Button("Exit");
        exitButton.setOnAction(e -> main.exit());

        vBox.getChildren().addAll(logoView, loginButton, registerButton, exitButton);

        return vBox;
    }
}
