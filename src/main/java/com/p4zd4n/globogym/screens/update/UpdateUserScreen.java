package com.p4zd4n.globogym.screens.update;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entities.*;
import com.p4zd4n.globogym.forms.update.UpdateUserForm;
import com.p4zd4n.globogym.panes.LeftPane;
import com.p4zd4n.globogym.panes.TopPane;
import com.p4zd4n.globogym.utils.Validatable;
import com.p4zd4n.globogym.utils.Validation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import lombok.Getter;

@Getter
public class UpdateUserScreen implements Validatable {

    private Main main;
    private User updatingUser;
    private User updatedUser;
    private BorderPane borderPane;
    private BorderPane centerContainer;
    private HBox centerTopContainer;
    private HBox bottomContainer;
    private UpdateUserForm form;
    private Label errorLabel;
    private Button updateButton;
    private Validation validation;

    public UpdateUserScreen(Main main, User updatingUser, User updatedUser) {

        this.main = main;
        this.updatingUser = updatingUser;
        this.updatedUser = updatedUser;
    }

    public Pane getView() {

        errorLabel = new Label();
        errorLabel.getStyleClass().add("label");
        errorLabel.getStyleClass().add("error");

        centerTopContainer = new HBox();
        centerTopContainer.setAlignment(Pos.CENTER);
        centerTopContainer.getChildren().add(errorLabel);

        form = new UpdateUserForm(updatingUser, updatedUser);
        form.getStyleClass().add("container");
        form.setVgap(10);
        form.setHgap(10);

        centerContainer = new BorderPane();
        centerContainer.setTop(centerTopContainer);
        centerContainer.setCenter(form);

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.setTop(new TopPane(main, updatingUser));
        borderPane.setCenter(centerContainer);
        borderPane.setLeft(new LeftPane(main, updatingUser));

        updateButton = new Button("Update");
        updateButton.getStyleClass().add("button");
        updateButton.setOnAction(e -> updateUser());

        bottomContainer = new HBox(10);
        bottomContainer.setAlignment(Pos.CENTER);
        bottomContainer.getChildren().add(updateButton);

        borderPane.setBottom(bottomContainer);

        validation = new Validation(form, this);

        return borderPane;
    }

    public void updateUser() {

        boolean areAllFieldsFilled = validation.areAllFieldsFilled();
        boolean isDataValid = validation.isDataValid();

        if (!areAllFieldsFilled) {
            errorLabel.setText("Not all fields have been filled!");
            return;
        }

        if (!isDataValid) {
            return;
        }

        updateUsername();
        updateEmail();
        updatePassword();
        updateFirstName();
        updateLastName();
        updateBirthDate();

        if (updatedUser instanceof Employee && updatingUser instanceof Manager) {
            updateSalary();
        }

        if (updatedUser instanceof Coach) {
            updateActiveStatus();
        }

        User.serializeUsers();
        if (updatingUser instanceof Employee employee) {
            main.showMembersManagementScreen(employee, null);
        } else {
            main.showUserAccountScreen(updatingUser);
        }
    }

    public void updateUsername() {

        if (!form.getUsernameField().getText().equals(updatedUser.getUsername())) {
            updatedUser.setUsername(form.getUsernameField().getText());
        }
    }

    public void updateEmail() {

        if (!form.getEmailField().getText().equals(updatedUser.getEmail())) {
            updatedUser.setEmail(form.getEmailField().getText());
        }
    }

    public void updatePassword() {

        if (!form.getPasswordField().getText().equals(updatedUser.getPassword())) {
            updatedUser.setPassword(form.getPasswordField().getText());
        }
    }

    public void updateFirstName() {

        if (!form.getFirstNameField().getText().equals(updatedUser.getFirstName())) {
            updatedUser.setFirstName(form.getFirstNameField().getText());
        }
    }

    public void updateLastName() {

        if (!form.getLastNameField().getText().equals(updatedUser.getLastName())) {
            updatedUser.setLastName(form.getLastNameField().getText());
        }
    }

    public void updateBirthDate() {

        if (!form.getBirthDateField().getValue().equals(updatedUser.getBirthDate())) {
            updatedUser.setBirthDate(form.getBirthDateField().getValue());
        }
    }

    private void updateSalary() {

        ((Employee) updatedUser).setSalary(Double.valueOf(form.getSalaryTextField().getText()));
    }

    public void updateActiveStatus() {

        ((Coach) updatedUser).setActive(form.getActiveCheckbox().isSelected());
    }
}
