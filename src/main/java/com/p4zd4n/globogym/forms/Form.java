package com.p4zd4n.globogym.forms;

import com.p4zd4n.globogym.entity.MembershipCard;
import com.p4zd4n.globogym.enums.MembershipCardStatus;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import jfxtras.scene.control.CalendarTimePicker;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
    protected Label roomNumberLabel;
    protected Label roomCapacityLabel;
    protected Label minRoomCapacityLabel;
    protected Label maxRoomCapacityLabel;
    protected Label eventNameLabel;
    protected Label eventDescriptionLabel;
    protected Label eventStartDateLabel;
    protected Label eventEndDateLabel;
    protected Label eventStartTimeLabel;
    protected Label eventEndTimeLabel;

    protected TextField idTextField;
    protected TextField usernameField;
    protected TextField emailField;
    protected TextField firstNameField;
    protected TextField lastNameField;
    protected TextField roomNumberField;
    protected TextField roomCapacityField;
    protected TextField minRoomCapacityField;
    protected TextField maxRoomCapacityField;
    protected TextField eventNameTextField;
    protected TextField eventDescriptionTextField;

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
    protected DatePicker eventStartDateField;
    protected DatePicker eventEndDateField;

    protected ComboBox<MembershipCardStatus> membershipCardStatusComboBox;

    protected CalendarTimePicker eventStartTimePicker;
    protected CalendarTimePicker eventEndTimePicker;

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

        roomNumberLabel = new Label("Room number:");
        roomNumberLabel.getStyleClass().add("label");
        roomNumberField = new TextField();
        roomNumberField.getStyleClass().add("field");

        roomCapacityLabel = new Label("Room capacity:");
        roomCapacityLabel.getStyleClass().add("label");
        roomCapacityField = new TextField();
        roomCapacityField.getStyleClass().add("field");

        minRoomCapacityLabel = new Label("Min. room capacity:");
        minRoomCapacityLabel.getStyleClass().add("label");
        minRoomCapacityField = new TextField();
        minRoomCapacityField.getStyleClass().add("field");

        maxRoomCapacityLabel = new Label("Max. room capacity:");
        maxRoomCapacityLabel.getStyleClass().add("label");
        maxRoomCapacityField = new TextField();
        maxRoomCapacityField.getStyleClass().add("field");

        eventNameLabel = new Label("Event name:");
        eventNameLabel.getStyleClass().add("label");
        eventNameTextField = new TextField();
        eventNameTextField.getStyleClass().add("field");

        eventDescriptionLabel = new Label("Event description:");
        eventDescriptionLabel.getStyleClass().add("label");
        eventDescriptionTextField = new TextField();
        eventDescriptionTextField.getStyleClass().add("field");

        eventStartDateLabel = new Label("Start date:");
        eventStartDateLabel.getStyleClass().add("label");
        eventStartDateField = new DatePicker();
        eventStartDateField.getStyleClass().add("field");
        eventStartDateField.setDayCellFactory(picker -> new DateCell() {

            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #c03d3d;");
                }
            }
        });

        eventEndDateLabel = new Label("End date:");
        eventEndDateLabel.getStyleClass().add("label");
        eventEndDateField = new DatePicker();
        eventEndDateField.getStyleClass().add("field");
        eventEndDateField.setDisable(true);
        eventEndDateField.setDayCellFactory(picker -> new DateCell() {

            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                LocalDate startDate = eventStartDateField.getValue();
                if (startDate != null && date.isBefore(startDate)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #c03d3d;");
                }
            }
        });
        eventStartDateField.valueProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null && eventEndDateField.getValue() != null && newValue.isAfter(eventEndDateField.getValue())) {
                eventEndDateField.setValue(newValue);
            }

            if (newValue != null && !newValue.isBefore(LocalDate.now())) {
                eventEndDateField.setDisable(false);
            } else {
                eventEndDateField.setDisable(true);
                eventEndDateField.setValue(null);
            }
        });

        eventStartTimeLabel = new Label("Start time:");
        eventStartTimeLabel.getStyleClass().add("label");
        eventStartTimePicker = new CalendarTimePicker();
        eventStartTimePicker.getStyleClass().add("field");

        eventEndTimeLabel = new Label("End time:");
        eventEndTimeLabel.getStyleClass().add("label");
        eventEndTimePicker = new CalendarTimePicker();
        eventEndTimePicker.getStyleClass().add("field");
    }
}
