package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainScreen {

    private Main main;
    private BorderPane borderPane;
    private VBox centerContainer;
    private HBox bottomContainer;
    private Image logo;
    private ImageView logoView;
    private Button loginButton;
    private Button registerButton;
    private Button exitButton;
    private Label authorLabel;

    public MainScreen(Main main) {

        this.main = main;
    }

    public Pane getView() {

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));

        centerContainer = new VBox();
        centerContainer.getStyleClass().add("container");
        centerContainer.setPadding(new Insets(10));
        centerContainer.setSpacing(10);

        logo = new Image(getClass().getResource("/img/logo-no-background.png").toString());
        logoView = new ImageView(logo);
        logoView.setFitHeight(96);
        logoView.setFitWidth(300);

        loginButton = new Button("Login");
        loginButton.getStyleClass().add("button");
        loginButton.setOnAction(e -> main.showLoginScreen());

        registerButton = new Button("Register");
        registerButton.getStyleClass().add("button");
        registerButton.setOnAction(e -> main.showRegistrationScreen());

        exitButton = new Button("Exit");
        exitButton.getStyleClass().add("button");
        exitButton.setOnAction(e -> main.exit());

        centerContainer.getChildren().addAll(logoView, loginButton, registerButton, exitButton);

        authorLabel = new Label("® 2024 Wiktor Chudy. All Rights Reserved.");
        authorLabel.getStyleClass().add("label");

        bottomContainer = new HBox();
        bottomContainer.setAlignment(Pos.CENTER);
        bottomContainer.getChildren().add(authorLabel);

        borderPane.setCenter(centerContainer);
        borderPane.setBottom(bottomContainer);

        return borderPane;
    }
}
