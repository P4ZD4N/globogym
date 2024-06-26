package com.p4zd4n.globogym.util;

import com.p4zd4n.globogym.entity.Room;
import com.p4zd4n.globogym.entity.User;
import com.p4zd4n.globogym.forms.*;
import com.p4zd4n.globogym.screens.RegistrationScreen;
import com.p4zd4n.globogym.screens.UpdateUserScreen;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    private Form form;

    private Validatable validatable;

    private Pattern pattern;
    private Matcher matcher;

    public Validation(Form form, Validatable validatable) {

        this.form = form;
        this.validatable = validatable;
    }

    public boolean areAllFieldsFilled() {

        if (form instanceof RegistrationForm || form instanceof AddUserForm || form instanceof UpdateUserForm) {
            return form.getUsernameField().getText() != null &&
                    form.getEmailField().getText() != null &&
                    form.getPasswordField().getText() != null &&
                    form.getFirstNameField().getText() != null &&
                    form.getLastNameField().getText() != null &&
                    form.getBirthDateField().getValue() != null;
        } else if (form instanceof AddRoomForm) {
            return form.getRoomNumberField().getText() != null &&
                    form.getRoomCapacityField().getText() != null;
        } else if (form instanceof AddOtherEventForm) {
            return form.getEventNameTextField().getText() != null &&
                    form.getEventDescriptionTextField().getText() != null &&
                    form.getEventStartDateField().getValue() != null &&
                    form.getEventEndDateField().getValue() != null;
        } else {
            return true;
        }
    }

    public boolean isDataValid() {

        if (form instanceof AddRoomForm) {
            return isRoomNumberValid() &&
                    isCapacityValid();
        }

        if (form instanceof RegistrationForm || form instanceof AddUserForm) {
            return isUsernameValid() &&
                    isEmailValid() &&
                    isPasswordValid() &&
                    isFirstNameAndLastNameValid() &&
                    isBirthDateValid();
        }

        if (form instanceof UpdateUserForm) {
            return isUsernameValid(((UpdateUserForm) form).getUpdatedUser()) &&
                    isEmailValid(((UpdateUserForm) form).getUpdatedUser()) &&
                    isPasswordValid() &&
                    isFirstNameAndLastNameValid() &&
                    isBirthDateValid();
        }

        if (form instanceof UpdateRoomForm) {
            return isRoomNumberValid(((UpdateRoomForm) form).getRoom()) &&
                    isCapacityValid();
        }

        if (form instanceof AddOtherEventForm) {
            return isEventNameValid() &&
                    isEventDescriptionValid() &&
                    isEventTimeValid();
        }

        return true;
    }

    private boolean isUsernameValid() {

        Optional<User> existingUser = User.getUsers().stream()
                .filter(user -> user.getUsername().equals(form.getUsernameField().getText()))
                .findFirst();

        return checkUsernameValidity(existingUser);
    }

    private boolean isUsernameValid(User u) {

        Optional<User> existingUser = User.getUsers().stream()
                .filter(user -> user.getUsername().equals(form.getUsernameField().getText()))
                .filter(user -> !user.equals(u))
                .findFirst();

        return checkUsernameValidity(existingUser);
    }

    private boolean checkUsernameValidity(Optional<User> existingUser) {

        if (existingUser.isPresent()) {
            validatable.getErrorLabel().setText("User with this username already exists!");
            return false;
        }

        if (form.getUsernameField().getText().length() <= 4) {
            validatable.getErrorLabel().setText("Username must consist of at least 5 characters!");
            return false;
        }

        String usernamePattern = "[^a-zA-Z0-9]";
        pattern = Pattern.compile(usernamePattern);
        matcher = pattern.matcher(form.getUsernameField().getText());
        boolean doesUsernameContainSpecialCharacters = matcher.find();

        if (doesUsernameContainSpecialCharacters) {
            validatable.getErrorLabel().setText("Username shouldn't contain special characters!");
            return false;
        }

        return true;
    }

    private boolean isEmailValid() {

        Optional<User> existingUser = User.getUsers().stream()
                .filter(user -> user.getEmail().equals(form.getEmailField().getText()))
                .findFirst();

        return checkEmailValidity(existingUser);
    }

    private boolean isEmailValid(User u) {

        Optional<User> existingUser = User.getUsers().stream()
                .filter(user -> user.getEmail().equals(form.getEmailField().getText()))
                .filter(user -> !user.equals(u))
                .findFirst();

        return checkEmailValidity(existingUser);
    }

    private boolean checkEmailValidity(Optional<User> existingUser) {

        if (existingUser.isPresent()) {
            validatable.getErrorLabel().setText("User with this email already exists!");
            return false;
        }

        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        pattern = Pattern.compile(emailPattern);
        matcher = pattern.matcher(form.getEmailField().getText());
        boolean isEmailValid = matcher.find();

        if (!isEmailValid) {
            validatable.getErrorLabel().setText("Invalid email!");
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
            validatable.getErrorLabel().setText("Password must consist of lowercase and uppercase letters, numbers and special characters!");
            return false;
        }

        if (form.getPasswordField().getText().length() < 8) {
            validatable.getErrorLabel().setText("Password must be at least 8 characters long!");
            return false;
        }

        if (
                !form.getPasswordField().getText().equals(form.getConfirmPasswordField().getText()) &&
                (validatable instanceof RegistrationScreen || validatable instanceof UpdateUserScreen)
        ) {
            validatable.getErrorLabel().setText("Entered passwords are different!");
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
            validatable.getErrorLabel().setText("First name must start with a capital letter and consist only of letters!");
            return false;
        }

        matcher = pattern.matcher(form.getLastNameField().getText());
        boolean isLastNameValid = matcher.find();

        if (!isLastNameValid) {
            validatable.getErrorLabel().setText("Last name must start with a capital letter and consist only of letters!");
            return false;
        }

        return true;
    }

    private boolean isBirthDateValid() {

        LocalDate birthDate = form.getBirthDateField().getValue();
        LocalDate todayDate = LocalDate.now();
        LocalDate thresholdDate = todayDate.minusYears(18);

        if (birthDate.isAfter(thresholdDate)) {
            validatable.getErrorLabel().setText("You must be at least 18 years old!");
            return false;
        }

        return true;
    }

    private boolean isRoomNumberValid() {

        Room foundRoom = getOptionalRoom();

        if (foundRoom != null) {
            validatable.getErrorLabel().setText("Room with this number already exist!");
            return false;
        }

        return true;
    }

    private boolean isRoomNumberValid(Room r) {

        Room foundRoom = getOptionalRoom();

        if (foundRoom != null && !foundRoom.equals(r)) {
            validatable.getErrorLabel().setText("Room with this number already exist!");
            return false;
        }

        return true;
    }

    private Room getOptionalRoom() {

        String enteredNumber = form.getRoomNumberField().getText();

        if (!isNumeric(enteredNumber)) {
            validatable.getErrorLabel().setText("Room number must be number!");
            return null;
        }

         return Room.findByRoomNumber(Integer.parseInt(enteredNumber));
    }


    private boolean isCapacityValid() {

        String enteredCapacity = form.getRoomCapacityField().getText();

        if (!isNumeric(enteredCapacity)) {
            validatable.getErrorLabel().setText("Room capacity must be number!");
            return false;
        }

        return true;
    }

    private boolean isNumeric(String string) {

        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isEventNameValid() {

        String enteredName = form.getEventNameTextField().getText();

        if (isNumeric(enteredName)) {
            validatable.getErrorLabel().setText("Name shouldn't be numeric!");
            return false;
        }

        return true;
    }

    private boolean isEventDescriptionValid() {

        String enteredDescription = form.getEventDescriptionTextField().getText();

        if (isNumeric(enteredDescription)) {
            validatable.getErrorLabel().setText("Description shouldn't be numeric!");
            return false;
        }

        if (enteredDescription.length() > 100) {
            validatable.getErrorLabel().setText("Description should consist of max. 100 characters! (Currently " + enteredDescription.length() + ")");
            return false;
        }

        return true;
    }

    private boolean  isEventTimeValid() {

        LocalDate startDate = form.getEventStartDateField().getValue();
        LocalDate endDate = form.getEventEndDateField().getValue();

        LocalTime startTime = LocalTime.ofInstant(form.getEventStartTimePicker().getCalendar().getTime().toInstant(), ZoneId.systemDefault());
        LocalTime endTime = LocalTime.ofInstant(form.getEventEndTimePicker().getCalendar().getTime().toInstant(), ZoneId.systemDefault());

        if (startDate.equals(endDate) && startTime.isAfter(endTime)) {
            validatable.getErrorLabel().setText("Invalid time!");
            return false;
        }

        return true;
    }
}
