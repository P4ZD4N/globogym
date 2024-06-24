package com.p4zd4n.globogym.util;

import com.p4zd4n.globogym.entity.User;
import com.p4zd4n.globogym.forms.Form;
import com.p4zd4n.globogym.screens.RegistrationScreen;

import java.time.LocalDate;
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

        return form.getUsernameField().getText() != null &&
                form.getEmailField().getText() != null &&
                form.getPasswordField().getText() != null &&
                form.getFirstNameField().getText() != null &&
                form.getLastNameField().getText() != null &&
                form.getBirthDateField().getValue() != null;
    }

    public boolean isDataValid() {

        return isUsernameValid() &&
                isEmailValid() &&
                isPasswordValid() &&
                isFirstNameAndLastNameValid() &&
                isBirthDateValid();
    }

    private boolean isUsernameValid() {

        Optional<User> existingUser = User.getUsers().stream()
                .filter(user -> user.getUsername().equals(form.getUsernameField().getText()))
                .findFirst();

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

        if (!form.getPasswordField().getText().equals(form.getConfirmPasswordField().getText()) && validatable instanceof RegistrationScreen) {
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
}
