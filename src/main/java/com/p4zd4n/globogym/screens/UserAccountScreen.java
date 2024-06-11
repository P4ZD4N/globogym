package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.User;
import com.p4zd4n.globogym.panes.CenterPane;
import com.p4zd4n.globogym.panes.LeftPane;
import com.p4zd4n.globogym.panes.TopPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;

public class UserAccountScreen {

    private Main main;

    private User user;

    private BorderPane borderPane;
    private CenterPane centerPane;

    private Label usernameLabel;
    private Label emailLabel;
    private Label firstNameLabel;
    private Label lastNameLabel;
    private Label birthDateLabel;
    private Label balanceLabel;

    public UserAccountScreen(Main main, User user) {

        this.main = main;
        this.user = user;
    }

    public Pane getView() {

        centerPane = new CenterPane();

        usernameLabel = new Label("Username: " + user.getUsername());
        emailLabel = new Label("Email: " + user.getEmail());
        firstNameLabel = new Label("First name: " + user.getFirstName());
        lastNameLabel = new Label("Last name: " + user.getLastName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        birthDateLabel = new Label();
        birthDateLabel.setText("Birth date: " + user.getBirthDate().format(formatter));
        balanceLabel = new Label("Balance: " + user.getBalance() + " zł");

        centerPane.getChildren().addAll(usernameLabel, emailLabel, firstNameLabel, lastNameLabel, birthDateLabel, balanceLabel);

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.setTop(new TopPane(main, user));
        borderPane.setLeft(new LeftPane(main, user));
        borderPane.setCenter(centerPane);

        return borderPane;
    }
}
