package com.p4zd4n.globogym.forms.add;

import com.p4zd4n.globogym.entities.Employee;
import com.p4zd4n.globogym.entities.Manager;
import com.p4zd4n.globogym.forms.Form;

public class AddUserForm extends Form {

    public AddUserForm(Employee employee) {

        super();

        clubMemberRadioButton.setToggleGroup(group);
        clubMemberRadioButton.setSelected(true);

        coachRadioButton.setToggleGroup(group);
        coachRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> addOrRemoveActiveFields(newValue));

        employeeRadioButton.setToggleGroup(group);
        employeeRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> addOrRemoveSalaryFields(newValue));

        managerRadioButton.setToggleGroup(group);
        managerRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> addOrRemoveSalaryFields(newValue));

        add(clubMemberRadioButton, 0, 0);
        coachRadioButton.setText("Coach");
        add(coachRadioButton, 0, 1);
        if (employee instanceof Manager) {
            add(employeeRadioButton, 1, 0);
            add(managerRadioButton, 1, 1);
        }
        add(usernameLabel, 0, 2);
        add(usernameField, 1, 2);
        add(emailLabel, 0, 3);
        add(emailField, 1, 3);
        add(passwordLabel, 0, 4);
        add(passwordField, 1, 4);
        add(firstNameLabel, 0, 5);
        add(firstNameField, 1, 5);
        add(lastNameLabel, 0, 6);
        add(lastNameField, 1, 6);
        add(birthDateLabel, 0, 7);
        add(birthDateField, 1, 7);
    }

    private void addOrRemoveActiveFields(Boolean newValue) {

        if (newValue) {
            add(activeLabel, 0, 8);
            add(activeCheckbox, 1, 8);
        } else {
            getChildren().removeAll(activeLabel, activeCheckbox);
        }
    }

    private void addOrRemoveSalaryFields(Boolean newValue) {

        if (newValue) {
            add(salaryLabel, 0, 8);
            add(salaryTextField, 1, 8);
        } else {
            getChildren().removeAll(salaryLabel, salaryTextField);
        }
    }
}
