package com.p4zd4n.globogym.forms;

import com.p4zd4n.globogym.entity.MembershipCard;
import com.p4zd4n.globogym.enums.MembershipCardStatus;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import lombok.Getter;

@Getter
public abstract class Form extends GridPane {

    protected Label idLabel;
    protected Label usernameLabel;
    protected Label emailLabel;
    protected Label passwordLabel;
    protected Label confirmPasswordLabel;
    protected Label firstNameLabel;
    protected Label lastNameLabel;
    protected Label birthDateLabel;
    protected Label minBirthDateLabel;
    protected Label maxBirthDateLabel;
    protected Label membershipCardStatusLabel;
    protected Label activeLabel;

    protected TextField idTextField;
    protected TextField usernameField;
    protected TextField emailField;
    protected TextField firstNameField;
    protected TextField lastNameField;

    protected PasswordField passwordField;
    protected PasswordField confirmPasswordField;

    protected ToggleGroup group;

    protected RadioButton clubMemberRadioButton;
    protected RadioButton coachRadioButton;

    protected CheckBox clubMemberCheckBox;
    protected CheckBox coachCheckbox;
    protected CheckBox activeCheckbox;
    protected CheckBox inactiveCheckbox;

    protected DatePicker birthDateField;
    protected DatePicker minBirthDateField;
    protected DatePicker maxBirthDateField;

    protected ComboBox<MembershipCardStatus> membershipCardStatusComboBox;

    public Form() {

        idLabel = new Label("ID:");
        idLabel.getStyleClass().add("label");
        idTextField = new TextField();
        idTextField.getStyleClass().add("field");

        usernameLabel = new Label("Username:");
        usernameLabel.getStyleClass().add("label");
        usernameField = new TextField();
        usernameField.getStyleClass().add("field");

        emailLabel = new Label("Email:");
        emailLabel.getStyleClass().add("label");
        emailField = new TextField();
        emailField.getStyleClass().add("field");

        passwordLabel = new Label("Password:");
        passwordLabel.getStyleClass().add("label");
        passwordField = new PasswordField();
        passwordField.getStyleClass().add("field");

        confirmPasswordLabel = new Label("Confirm password: ");
        confirmPasswordLabel.getStyleClass().add("label");
        confirmPasswordField = new PasswordField();
        confirmPasswordField.getStyleClass().add("field");

        firstNameLabel = new Label("First name:");
        firstNameLabel.getStyleClass().add("label");
        firstNameField = new TextField();
        firstNameField.getStyleClass().add("field");

        lastNameLabel = new Label("Last name:");
        lastNameLabel.getStyleClass().add("label");
        lastNameField = new TextField();
        lastNameField.getStyleClass().add("field");

        group = new ToggleGroup();

        clubMemberRadioButton = new RadioButton("Club member");
        clubMemberRadioButton.getStyleClass().add("radio-button");

        coachRadioButton = new RadioButton("Coach (approval required!)");
        coachRadioButton.getStyleClass().add("radio-button");

        clubMemberCheckBox = new CheckBox("Club member");
        clubMemberCheckBox.getStyleClass().add("checkbox");

        coachCheckbox = new CheckBox("Coach");
        coachCheckbox.getStyleClass().add("checkbox");

        birthDateLabel = new Label("Birth date:");
        birthDateLabel.getStyleClass().add("label");
        birthDateField = new DatePicker();
        birthDateField.getStyleClass().add("field");

        minBirthDateLabel = new Label("Min. birth date:");
        minBirthDateLabel.getStyleClass().add("label");
        minBirthDateField = new DatePicker();
        minBirthDateField.getStyleClass().add("field");

        maxBirthDateLabel = new Label("Max. birth date:");
        maxBirthDateLabel.getStyleClass().add("label");
        maxBirthDateField = new DatePicker();
        maxBirthDateField.getStyleClass().add("field");

        membershipCardStatusLabel = new Label("Card status:");
        membershipCardStatusLabel.getStyleClass().add("label");
        membershipCardStatusComboBox = new ComboBox<>();
        membershipCardStatusComboBox.getItems().addAll(
                null, MembershipCardStatus.NO_CARD, MembershipCardStatus.ACTIVE, MembershipCardStatus.EXPIRED);

        activeLabel = new Label("Active:");
        activeLabel.getStyleClass().add("label");
        activeCheckbox = new CheckBox("Active");
        activeCheckbox.getStyleClass().add("checkbox");
        inactiveCheckbox = new CheckBox("Inactive");
        inactiveCheckbox.getStyleClass().add("checkbox");
    }
}
