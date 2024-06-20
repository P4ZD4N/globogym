package com.p4zd4n.globogym.forms;

import lombok.Getter;

@Getter
public class RegistrationForm extends Form {

    public RegistrationForm() {

        super();

        clubMemberRadioButton.setToggleGroup(group);
        clubMemberRadioButton.setSelected(true);

        coachRadioButton.setToggleGroup(group);

        add(clubMemberRadioButton, 0, 0);
        add(coachRadioButton, 1, 0);
        add(usernameLabel, 0, 1);
        add(usernameField, 1, 1);
        add(emailLabel, 0, 2);
        add(emailField, 1, 2);
        add(passwordLabel, 0, 3);
        add(passwordField, 1, 3);
        add(confirmPasswordLabel, 0, 4);
        add(confirmPasswordField, 1, 4);
        add(firstNameLabel, 0, 5);
        add(firstNameField, 1, 5);
        add(lastNameLabel, 0, 6);
        add(lastNameField, 1, 6);
        add(birthDateLabel, 0, 7);
        add(birthDateField, 1, 7);
    }
}
