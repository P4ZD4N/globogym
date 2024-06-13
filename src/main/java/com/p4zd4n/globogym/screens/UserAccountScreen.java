package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.Coach;
import com.p4zd4n.globogym.entity.User;
import com.p4zd4n.globogym.enums.CoachSpecialization;
import com.p4zd4n.globogym.panes.CenterPane;
import com.p4zd4n.globogym.panes.LeftPane;
import com.p4zd4n.globogym.panes.TopPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.time.format.DateTimeFormatter;

public class UserAccountScreen {

    private Main main;

    private User user;

    private BorderPane borderPane;
    private GridPane centerPane;
    private VBox profilePicturePane;
    private VBox userDetailsPane;
    private VBox centerRightPane;

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

        centerPane = new GridPane();
        centerPane.getStyleClass().add("container");

        profilePicturePane = new VBox();
        profilePicturePane.setSpacing(10);
        profilePicturePane.setAlignment(Pos.CENTER);

        userDetailsPane = new VBox();
        userDetailsPane.setSpacing(10);
        userDetailsPane.setAlignment(Pos.CENTER);

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

        profilePicturePane.getChildren().addAll(profilePictureView, changeProfilePictureButton);
        userDetailsPane.getChildren().addAll(idLabel, usernameLabel, emailLabel, firstNameLabel, lastNameLabel, birthDateLabel);

        if (user instanceof Coach coach) {

            centerRightPane = new VBox();
            centerRightPane.setSpacing(10);
            centerRightPane.setAlignment(Pos.CENTER);

            Label specializationsLabel = new Label("Specializations");
            centerRightPane.getChildren().add(specializationsLabel);

            CoachSpecialization.getAllSpecializations()
                    .stream()
                    .map(coachSpecialization -> {
                        CheckBox checkBox = new CheckBox(coachSpecialization.name());
                        checkBox.setSelected(coach.getSpecializations().contains(coachSpecialization));
                        checkBox.setOnAction(e -> {
                            if (checkBox.isSelected()) {
                                coach.addSpecialization(coachSpecialization);
                            } else {
                                coach.removeSpecialization(coachSpecialization);
                            }
                        });
                        return checkBox;
                    })
                    .forEach(centerRightPane.getChildren()::add);
        }

        GridPane.setColumnIndex(profilePicturePane, 0);
        GridPane.setColumnIndex(userDetailsPane, 1);
        centerPane.getChildren().addAll(profilePicturePane, userDetailsPane);
        if (centerRightPane != null) {
            GridPane.setColumnIndex(centerRightPane, 2);
            centerPane.getChildren().add(centerRightPane);
        }
        centerPane.setHgap(30);

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
