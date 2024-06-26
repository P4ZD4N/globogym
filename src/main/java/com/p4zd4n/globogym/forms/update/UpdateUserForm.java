package com.p4zd4n.globogym.forms.update;

import com.p4zd4n.globogym.entity.Coach;
import com.p4zd4n.globogym.entity.Employee;
import com.p4zd4n.globogym.entity.Manager;
import com.p4zd4n.globogym.entity.User;
import com.p4zd4n.globogym.forms.Form;
import lombok.Getter;

@Getter
public class UpdateUserForm extends Form {

    private User updatingUser;
    private User updatedUser;

    public UpdateUserForm(User updatingUser, User updatedUser) {

        super();

        this.updatingUser = updatingUser;
        this.updatedUser = updatedUser;

        add(usernameLabel, 0, 0);
        usernameField.setText(updatedUser.getUsername());
        add(usernameField, 1, 0);
        add(emailLabel, 0, 1);
        emailField.setText(updatedUser.getEmail());
        add(emailField, 1, 1);
        add(passwordLabel, 0, 2);
        passwordField.setText(updatedUser.getPassword());
        add(passwordField, 1, 2);
        add(confirmPasswordLabel, 0, 3);
        confirmPasswordField.setText(updatedUser.getPassword());
        add(confirmPasswordField, 1, 3);
        add(firstNameLabel, 0, 4);
        firstNameField.setText(updatedUser.getFirstName());
        add(firstNameField, 1, 4);
        add(lastNameLabel, 0, 5);
        lastNameField.setText(updatedUser.getLastName());
        add(lastNameField, 1, 5);
        add(birthDateLabel, 0, 6);
        birthDateField.setValue(updatedUser.getBirthDate());
        add(birthDateField, 1, 6);
        if (updatedUser instanceof Employee employee && updatingUser instanceof Manager) {
            add(salaryLabel, 0, 7);
            salaryTextField.setText(String.valueOf(employee.getSalary()));
            add(salaryTextField, 1, 7);
        }
        if (updatedUser instanceof Coach coach && updatingUser instanceof Employee) {
            add(activeLabel, 0, 8);
            activeCheckbox.setSelected(coach.isActive());
            add(activeCheckbox, 1, 8);
        }
    }
}
