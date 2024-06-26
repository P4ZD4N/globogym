package com.p4zd4n.globogym.forms;

import lombok.Getter;

@Getter
public class FindUserForm extends Form {

    public FindUserForm() {

        super();

        add(idLabel, 0, 0);
        add(idTextField, 1, 0);
        add(usernameLabel, 0, 1);
        add(usernameField, 1, 1);
        add(emailLabel, 0, 2);
        add(emailField, 1, 2);
        add(firstNameLabel, 0, 3);
        add(firstNameField, 1, 3);
        add(lastNameLabel, 0, 4);
        add(lastNameField, 1, 4);
        add(minDateLabel, 0, 5);
        add(minDateField, 1, 5);
        add(maxDateLabel, 0, 6);
        add(maxDateField, 1, 6);
        add(membershipCardStatusLabel, 0, 7);
        add(membershipCardStatusComboBox, 1, 7);
        add(activeCheckbox, 0, 8);
        add(inactiveCheckbox, 1, 8);
        add(clubMemberCheckBox, 0, 9);
        add(coachCheckbox, 1, 9);
    }
}
