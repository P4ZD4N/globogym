package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.ClubMember;
import com.p4zd4n.globogym.entity.Coach;
import com.p4zd4n.globogym.entity.User;
import com.p4zd4n.globogym.forms.Form;
import com.p4zd4n.globogym.forms.RegistrationForm;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationScreen {

    private Main main;

    private BorderPane borderPane;
    private HBox topContainer;
    private RegistrationForm form;
    private HBox bottomContainer;

    private Label errorLabel;

    private Button registerButton;
    private Button backButton;

    private Pattern pattern;
    private Matcher matcher;

    public RegistrationScreen(Main main) {

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

        form = new RegistrationForm();
        form.getStyleClass().add("container");
        form.setVgap(10);
        form.setHgap(10);

        registerButton = new Button("Register");
        registerButton.getStyleClass().add("button");
        registerButton.setOnAction(e -> registerUser());

        backButton = new Button("Back");
        backButton.getStyleClass().add("button");
        backButton.setOnAction(e -> main.showMainScreen());

        bottomContainer = new HBox(10);
        bottomContainer.setAlignment(Pos.CENTER);
        bottomContainer.getChildren().add(backButton);
        bottomContainer.getChildren().add(registerButton);

        borderPane.setTop(topContainer);
        borderPane.setCenter(form);
        borderPane.setBottom(bottomContainer);

        return borderPane;
    }

    private void registerUser() {

        if (!areAllFieldsFilled()) {
            errorLabel.setText("Not all fields have been filled!");
            return;
        }

        if (!isDataValid()) {
            return;
        }

        if (form.getGroup().getSelectedToggle().equals(form.getClubMemberRadioButton())) {
            ClubMember clubMember = new ClubMember(
                    form.getUsernameField().getText(),
                    form.getEmailField().getText(),
                    form.getPasswordField().getText(),
                    form.getFirstNameField().getText(),
                    form.getLastNameField().getText(),
                    form.getBirthDateField().getValue());
            User.serializeUsers();
            System.out.println("Club member registered successfully!");
        } else {
            Coach coach = new Coach(
                    form.getUsernameField().getText(),
                    form.getEmailField().getText(),
                    form.getPasswordField().getText(),
                    form.getFirstNameField().getText(),
                    form.getLastNameField().getText(),
                    form.getBirthDateField().getValue());
            User.serializeUsers();
            System.out.println("Coach registered successfully!");
        }

        main.showMainScreen();
    }

    private boolean areAllFieldsFilled() {

        return form.getUsernameField().getText() != null &&
                form.getEmailField().getText() != null &&
                form.getPasswordField().getText() != null &&
                form.getFirstNameField().getText() != null &&
                form.getLastNameField().getText() != null &&
                form.getBirthDateField().getValue() != null;
    }

    private boolean isDataValid() {

        if (!isUsernameValid()) {
            return false;
        }

        if (!isEmailValid()) {
            return false;
        }

        if (!isPasswordValid()) {
            return false;
        }

        if (!isFirstNameAndLastNameValid()) {
            return false;
        }

        if (!isBirthDateValid()) {
            return false;
        }

        return true;
    }

    private boolean isUsernameValid() {

        Optional<User> existingUser = User.getUsers().stream()
                                                    .filter(user -> user.getUsername().equals(form.getUsernameField().getText()))
                                                    .findFirst();

        if (existingUser.isPresent()) {
            errorLabel.setText("User with this username already exists!");
            return false;
        }

        if (form.getUsernameField().getText().length() <= 4) {
            errorLabel.setText("Username must consist of at least 5 characters!");
            return false;
        }

        String usernamePattern = "[^a-zA-Z0-9]";
        pattern = Pattern.compile(usernamePattern);
        matcher = pattern.matcher(form.getUsernameField().getText());
        boolean doesUsernameContainSpecialCharacters = matcher.find();

        if (doesUsernameContainSpecialCharacters) {
            errorLabel.setText("Username shouldn't contain special characters!");
            return false;
        }

        return true;
    }

    private boolean isEmailValid() {

        Optional<User> existingUser = User.getUsers().stream()
                                                    .filter(user -> user.getEmail().equals(form.getEmailField().getText()))
                                                    .findFirst();

        if (existingUser.isPresent()) {
            errorLabel.setText("User with this email already exists!");
            return false;
        }

        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        pattern = Pattern.compile(emailPattern);
        matcher = pattern.matcher(form.getEmailField().getText());
        boolean isEmailValid = matcher.find();

        if (!isEmailValid) {
            errorLabel.setText("Invalid email!");
            return false;
        }

        return true;
    }

    private boolean isPasswordValid() {

        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).+$";
        pattern = Pattern.compile(passwordPattern);
        matcher = pattern.matcher(form.getPasswordField().getText());
        boolean isPasswordValid = matcher.find();

        if (!isPasswordValid) {
            errorLabel.setText("Password must consist of lowercase and uppercase letters, numbers and special characters!");
            return false;
        }

        if (form.getPasswordField().getText().length() < 8) {
            errorLabel.setText("Password must be at least 8 characters long!");
            return false;
        }

        if (!form.getPasswordField().getText().equals(form.getConfirmPasswordField().getText())) {
            errorLabel.setText("Entered passwords are different!");
            return false;
        }

        return true;
    }

    private boolean isFirstNameAndLastNameValid() {

        String namePattern = "^[A-Z][a-zA-Z]*$";
        pattern = Pattern.compile(namePattern);
        matcher = pattern.matcher(form.getFirstNameField().getText());
        boolean isFirstNameValid = matcher.find();

        if (!isFirstNameValid) {
            errorLabel.setText("First name must start with a capital letter and consist only of letters!");
            return false;
        }

        matcher = pattern.matcher(form.getLastNameField().getText());
        boolean isLastNameValid = matcher.find();

        if (!isLastNameValid) {
            errorLabel.setText("Last name must start with a capital letter and consist only of letters!");
            return false;
        }

        return true;
    }

    private boolean isBirthDateValid() {

        LocalDate birthDate = form.getBirthDateField().getValue();
        LocalDate todayDate = LocalDate.now();
        LocalDate thresholdDate = todayDate.minusYears(18);

        if (birthDate.isAfter(thresholdDate)) {
            errorLabel.setText("You must be at least 18 years old!");
            return false;
        }

        return true;
    }
}
