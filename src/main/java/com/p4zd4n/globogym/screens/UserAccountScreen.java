package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.User;
import com.p4zd4n.globogym.panes.CenterPane;
import com.p4zd4n.globogym.panes.LeftPane;
import com.p4zd4n.globogym.panes.TopPane;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.time.format.DateTimeFormatter;

public class UserAccountScreen {

    private Main main;

    private User user;

    private BorderPane borderPane;
    private CenterPane centerPane;

    private Image profilePicture;
    private ImageView profilePictureView;

    private Label idLabel;
    private Label usernameLabel;
    private Label emailLabel;
    private Label firstNameLabel;
    private Label lastNameLabel;
    private Label birthDateLabel;

    private Button changeProfilePictureButton;

    public UserAccountScreen(Main main, User user) {

        this.main = main;
        this.user = user;
    }

    public Pane getView() {

        centerPane = new CenterPane();

        profilePicture = new Image(new File(user.getProfilePicturePath()).toURI().toString());
        profilePictureView = new ImageView(profilePicture);
        profilePictureView.setFitHeight(100);
        profilePictureView.setFitWidth(100);

        changeProfilePictureButton = new Button("Change picture");
        changeProfilePictureButton.setOnAction(e -> changeProfilePicture());

        idLabel = new Label("ID: " + user.getId());
        usernameLabel = new Label("Username: " + user.getUsername());
        emailLabel = new Label("Email: " + user.getEmail());
        firstNameLabel = new Label("First name: " + user.getFirstName());
        lastNameLabel = new Label("Last name: " + user.getLastName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        birthDateLabel = new Label();
        birthDateLabel.setText("Birth date: " + user.getBirthDate().format(formatter));

        centerPane.getChildren().addAll(
                profilePictureView,
                changeProfilePictureButton,
                idLabel,
                usernameLabel,
                emailLabel,
                firstNameLabel,
                lastNameLabel, birthDateLabel);

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.setTop(new TopPane(main, user));
        borderPane.setLeft(new LeftPane(main, user));
        borderPane.setCenter(centerPane);

        return borderPane;
    }

    private void changeProfilePicture() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose image");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpeg", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(main.getPrimaryStage());

        if (selectedFile != null) {
            String path = selectedFile.getAbsolutePath();
            user.setProfilePicturePath(path);
            User.serializeUsers();
        }
    }
}
