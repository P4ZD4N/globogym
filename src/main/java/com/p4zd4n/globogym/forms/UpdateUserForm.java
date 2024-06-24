package com.p4zd4n.globogym.forms;

import com.p4zd4n.globogym.entity.Coach;
import com.p4zd4n.globogym.entity.User;

public class UpdateUserForm extends Form {

    public UpdateUserForm(User user) {

        super();

        add(usernameLabel, 0, 0);
        usernameField.setText(user.getUsername());
        add(usernameField, 1, 0);
        add(emailLabel, 0, 1);
        emailField.setText(user.getEmail());
        add(emailField, 1, 1);
        add(passwordLabel, 0, 2);
        passwordField.setText(user.getPassword());
        add(passwordField, 1, 2);
        add(firstNameLabel, 0, 3);
        firstNameField.setText(user.getFirstName());
        add(firstNameField, 1, 3);
        add(lastNameLabel, 0, 4);
        lastNameField.setText(user.getLastName());
        add(lastNameField, 1, 4);
        add(birthDateLabel, 0, 5);
        birthDateField.setValue(user.getBirthDate());
        add(birthDateField, 1, 5);
        if (user instanceof Coach coach) {
            add(activeLabel, 0, 6);
            activeCheckbox.setSelected(coach.isActive());
            add(activeCheckbox, 1, 6);
        }
    }
}
