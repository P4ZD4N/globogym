package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.Coach;
import com.p4zd4n.globogym.entity.User;
import com.p4zd4n.globogym.enums.CoachSpecialization;
import com.p4zd4n.globogym.panes.LeftPane;
import com.p4zd4n.globogym.panes.TopPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

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

    private TextField usernameTextField;
    private TextField emailTextField;
    private TextField firstNameTextField;
    private TextField lastNameTextField;

    private DatePicker birthDatePicker;

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

        idLabel = new Label("MID " + user.getId());
        idLabel.getStyleClass().add("bold");

        VBox usernameContainer = createEditableField("Username", user.getUsername(), (newValue) -> user.setUsername(newValue));
        usernameLabel = (Label) usernameContainer.getChildren().get(1);
        usernameTextField = (TextField) usernameContainer.getChildren().get(2);

        VBox emailContainer = createEditableField("Email", user.getEmail(), (newValue) -> user.setEmail(newValue));
        emailLabel = (Label) emailContainer.getChildren().get(1);
        emailTextField = (TextField) emailContainer.getChildren().get(2);

        VBox firstNameContainer = createEditableField("First name", user.getFirstName(), (newValue) -> user.setFirstName(newValue));
        firstNameLabel = (Label) firstNameContainer.getChildren().get(1);
        firstNameTextField = (TextField) firstNameContainer.getChildren().get(2);

        VBox lastNameContainer = createEditableField("Last name", user.getLastName(), (newValue) -> user.setLastName(newValue));
        lastNameLabel = (Label) lastNameContainer.getChildren().get(1);
        lastNameTextField = (TextField) lastNameContainer.getChildren().get(2);

        VBox birthDateContainer = createEditableDateField("Birth date", user.getBirthDate(), newValue -> user.setBirthDate(newValue));
        birthDateLabel = (Label) birthDateContainer.getChildren().get(1);
        birthDatePicker = (DatePicker) birthDateContainer.getChildren().get(2);

        profilePicturePane.getChildren().addAll(profilePictureView, changeProfilePictureButton);
        userDetailsPane.getChildren().addAll(idLabel, usernameContainer, emailContainer, firstNameContainer, lastNameContainer, birthDateContainer);

        if (user instanceof Coach coach) {

            centerRightPane = new VBox();
            centerRightPane.setSpacing(10);
            centerRightPane.setAlignment(Pos.CENTER);

            Label specializationsLabel = new Label("Specializations");
            specializationsLabel.getStyleClass().add("bold");
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
        centerPane.setHgap(100);

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

    private VBox createEditableField(String labelText, String initialValue, Consumer<String> updateFunction) {

        Label fieldLabel = new Label(labelText);
        fieldLabel.getStyleClass().add("bold");

        Label label = new Label(initialValue);

        TextField textField = new TextField(initialValue);
        textField.setVisible(false);

        label.setOnMouseClicked(e -> {

            label.setVisible(false);
            textField.setVisible(true);
            textField.setManaged(true);
            textField.requestFocus();
        });

        textField.setOnAction(e -> {

            String newValue = textField.getText();

            label.setText(newValue);
            label.setVisible(true);
            textField.setVisible(false);
            updateFunction.accept(newValue);

            User.serializeUsers();
        });

        VBox container = new VBox(fieldLabel, label, textField);

        container.setAlignment(Pos.CENTER);

        return container;
    }

    private VBox createEditableDateField(String labelText, LocalDate initialValue, Consumer<LocalDate> updateFunction) {

        Label fieldLabel = new Label(labelText);
        fieldLabel.getStyleClass().add("bold");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Label label = new Label(initialValue.format(formatter));
        DatePicker datePicker = new DatePicker(initialValue);
        datePicker.setVisible(false);

        LocalDate today = LocalDate.now();
        LocalDate minDate = today.minusYears(18);

        datePicker.setDayCellFactory(picker -> new DateCell() {

            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (date.isAfter(minDate)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #c03d3d;");
                }
            }
        });

        label.setOnMouseClicked(e -> {

            label.setVisible(false);
            datePicker.setVisible(true);
            datePicker.setManaged(true);
            datePicker.requestFocus();
        });

        datePicker.setOnAction(e -> {

            LocalDate newValue = datePicker.getValue();

            label.setText(newValue.format(formatter));
            label.setVisible(true);
            datePicker.setVisible(false);
            updateFunction.accept(newValue);

            User.serializeUsers();
        });

        VBox container = new VBox(fieldLabel, label, datePicker);
        container.setAlignment(Pos.CENTER);

        return container;
    }
}
