package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entities.Coach;
import com.p4zd4n.globogym.entities.Employee;
import com.p4zd4n.globogym.entities.User;
import com.p4zd4n.globogym.enums.ClassesType;
import com.p4zd4n.globogym.panes.LeftPane;
import com.p4zd4n.globogym.panes.TopPane;
import com.p4zd4n.globogym.utils.BoldDescriptorLabel;
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
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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
    private Button updateDataButton;

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

        idLabel = new BoldDescriptorLabel("UID ", String.valueOf(user.getId()));
        usernameLabel = new BoldDescriptorLabel("Username: ", user.getUsername());
        emailLabel = new BoldDescriptorLabel("Email: ", user.getEmail());
        firstNameLabel = new BoldDescriptorLabel("First name: ", user.getFirstName());
        lastNameLabel = new BoldDescriptorLabel("Last name: ", user.getLastName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        birthDateLabel = new BoldDescriptorLabel("Birth date: ", user.getBirthDate().format(formatter));

        updateDataButton = new Button("Update data");
        updateDataButton.setOnAction(e -> updateData());

        profilePicturePane.getChildren().addAll(profilePictureView, changeProfilePictureButton);
        userDetailsPane.getChildren().addAll(idLabel, usernameLabel, emailLabel, firstNameLabel, lastNameLabel, birthDateLabel, updateDataButton);

        if (user instanceof Coach coach) {

            centerRightPane = new VBox();
            centerRightPane.setSpacing(10);
            centerRightPane.setAlignment(Pos.CENTER);

            Label specializesInLabel = new BoldDescriptorLabel("Specializes in: ", "");
            centerRightPane.getChildren().add(specializesInLabel);

            ClassesType.getAll()
                    .stream()
                    .map(type -> {
                        CheckBox checkBox = new CheckBox(type.getType());
                        checkBox.setSelected(coach.getSpecializations().contains(type));
                        checkBox.setOnAction(e -> {
                            if (checkBox.isSelected()) {
                                coach.addSpecialization(type);
                            } else {
                                coach.removeSpecialization(type);
                            }
                        });
                        return checkBox;
                    })
                    .forEach(centerRightPane.getChildren()::add);
        }

        if (user instanceof Employee employee) {

            centerRightPane = new VBox();
            centerRightPane.setSpacing(10);
            centerRightPane.setAlignment(Pos.CENTER);

            Label monthlySalaryLabel = new BoldDescriptorLabel("Monthly salary: ", employee.getSalary() + " zł");
            centerRightPane.getChildren().add(monthlySalaryLabel);
        }

        GridPane.setColumnIndex(profilePicturePane, 0);
        GridPane.setColumnIndex(userDetailsPane, 1);
        centerPane.getChildren().addAll(profilePicturePane, userDetailsPane);
        if (centerRightPane != null) {
            GridPane.setColumnIndex(centerRightPane, 2);
            centerPane.getChildren().add(centerRightPane);
        }
        centerPane.setHgap(80);

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
            main.showUserAccountScreen(user);
        }
    }

    private void updateData() {

        main.showUpdateUserScreen(user, user);
    }
}