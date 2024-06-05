package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.User;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class RegistrationScreen {

    private Main main;

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

        GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("container");
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

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

        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(emailLabel, 0, 1);
        gridPane.add(emailField, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(confirmPasswordLabel, 0, 3);
        gridPane.add(confirmPasswordField, 1, 3);
        gridPane.add(firstNameLabel, 0, 4);
        gridPane.add(firstNameField, 1, 4);
        gridPane.add(lastNameLabel, 0, 5);
        gridPane.add(lastNameField, 1, 5);
        gridPane.add(birthDateLabel, 0, 6);
        gridPane.add(birthDateField, 1, 6);
        gridPane.add(registerButton, 1, 7);
        gridPane.add(backButton, 1, 8);

        return gridPane;
    }

    private void registerUser() {

        if (!areAllFieldsFilled()) {
            System.out.println("Not all fields have been filled!");
            return;
        }

        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            System.out.println("Entered passwords are different!");
            return;
        }

        User user = new User(
                usernameField.getText(),
                emailField.getText(),
                passwordField.getText(),
                firstNameField.getText(),
                lastNameField.getText(),
                birthDateField.getValue());
        User.getUsers().add(user);
        User.serializeUsers();

        System.out.println("User registered successfully!");

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
