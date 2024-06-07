package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.ClubMember;
import com.p4zd4n.globogym.entity.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class RegistrationScreen {

    private Main main;

    private Label errorLabel;
    private Label usernameLabel;
    private Label emailLabel;
    private Label passwordLabel;
    private Label confirmPasswordLabel;
    private Label firstNameLabel;
    private Label lastNameLabel;
    private Label birthDateLabel;

    private TextField usernameField;
    private TextField emailField;
    private TextField firstNameField;
    private TextField lastNameField;

    private PasswordField passwordField;
    private PasswordField confirmPasswordField;

    private DatePicker birthDateField;

    private Button registerButton;
    private Button backButton;

    public RegistrationScreen(Main main) {

        this.main = main;
    }

    public Pane getView() {

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10, 10, 10, 10));

        errorLabel = new Label();
        errorLabel.getStyleClass().add("error");

        HBox topContainer = new HBox();
        topContainer.setAlignment(Pos.CENTER);
        topContainer.getChildren().add(errorLabel);

        GridPane centerContainer = new GridPane();
        centerContainer.getStyleClass().add("container");
        centerContainer.setVgap(5);
        centerContainer.setHgap(5);

        usernameLabel = new Label("Username:");
        usernameField = new TextField();

        emailLabel = new Label("Email:");
        emailField = new TextField();

        passwordLabel = new Label("Password:");
        passwordField = new PasswordField();

        confirmPasswordLabel = new Label("Confirm password: ");
        confirmPasswordField = new PasswordField();

        firstNameLabel = new Label("First name:");
        firstNameField = new TextField();

        lastNameLabel = new Label("Last name:");
        lastNameField = new TextField();

        birthDateLabel = new Label("Birth date:");
        birthDateField = new DatePicker();

        registerButton = new Button("Register");
        registerButton.setOnAction(e -> registerUser());

        backButton = new Button("Back");
        backButton.setOnAction(e -> main.showMainScreen());

        centerContainer.add(usernameLabel, 0, 0);
        centerContainer.add(usernameField, 1, 0);
        centerContainer.add(emailLabel, 0, 1);
        centerContainer.add(emailField, 1, 1);
        centerContainer.add(passwordLabel, 0, 2);
        centerContainer.add(passwordField, 1, 2);
        centerContainer.add(confirmPasswordLabel, 0, 3);
        centerContainer.add(confirmPasswordField, 1, 3);
        centerContainer.add(firstNameLabel, 0, 4);
        centerContainer.add(firstNameField, 1, 4);
        centerContainer.add(lastNameLabel, 0, 5);
        centerContainer.add(lastNameField, 1, 5);
        centerContainer.add(birthDateLabel, 0, 6);
        centerContainer.add(birthDateField, 1, 6);

        HBox bottomContainer = new HBox();
        bottomContainer.setAlignment(Pos.CENTER);
        bottomContainer.getChildren().add(backButton);
        bottomContainer.getChildren().add(registerButton);

        borderPane.setTop(topContainer);
        borderPane.setCenter(centerContainer);
        borderPane.setBottom(bottomContainer);

        return borderPane;
    }

    private void registerUser() {

        if (!areAllFieldsFilled()) {
            errorLabel.setText("Not all fields have been filled!");
            return;
        }

        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            errorLabel.setText("Entered passwords are different!");
            return;
        }

        ClubMember clubMember = new ClubMember(
                usernameField.getText(),
                emailField.getText(),
                passwordField.getText(),
                firstNameField.getText(),
                lastNameField.getText(),
                birthDateField.getValue());
        User.getUsers().add(clubMember);
        User.serializeUsers();

        System.out.println("Club member registered successfully!");

        main.showMainScreen();
    }

    private boolean areAllFieldsFilled() {

        return usernameField.getText() != null &&
                passwordField.getText() != null &&
                confirmPasswordField.getText() != null &&
                firstNameField.getText() != null &&
                lastNameField.getText() != null &&
                birthDateField.getValue() != null;
    }
}
