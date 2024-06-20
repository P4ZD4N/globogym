package com.p4zd4n.globogym.forms;

public class AddUserForm extends Form {

    public AddUserForm() {

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
        add(firstNameLabel, 0, 4);
        add(firstNameField, 1, 4);
        add(lastNameLabel, 0, 5);
        add(lastNameField, 1, 5);
        add(birthDateLabel, 0, 6);
        add(birthDateField, 1, 6);
        add(activeLabel, 0, 7);
        add(activeCheckbox, 1, 7);
    }
}