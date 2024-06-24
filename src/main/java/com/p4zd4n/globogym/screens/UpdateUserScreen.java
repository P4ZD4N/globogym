package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.ClubMember;
import com.p4zd4n.globogym.entity.Coach;
import com.p4zd4n.globogym.entity.Employee;
import com.p4zd4n.globogym.entity.User;
import com.p4zd4n.globogym.forms.UpdateUserForm;
import com.p4zd4n.globogym.panes.LeftPane;
import com.p4zd4n.globogym.panes.TopPane;
import com.p4zd4n.globogym.util.Validatable;
import com.p4zd4n.globogym.util.Validation;
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

    private Employee employee;
    private User user;

    private BorderPane borderPane;
    private BorderPane centerContainer;
    private HBox centerTopContainer;
    private UpdateUserForm form;
    private HBox bottomContainer;

    private Label errorLabel;

    private Button updateButton;

    private Validation validation;

    public UpdateUserScreen(Main main, Employee employee, User user) {

        this.main = main;
        this.employee = employee;
        this.user = user;
    }

    public Pane getView() {

        errorLabel = new Label();
        errorLabel.getStyleClass().add("label");
        errorLabel.getStyleClass().add("error");

        centerTopContainer = new HBox();
        centerTopContainer.setAlignment(Pos.CENTER);
        centerTopContainer.getChildren().add(errorLabel);

        form = new UpdateUserForm(user);
        form.getStyleClass().add("container");
        form.setVgap(10);
        form.setHgap(10);

        centerContainer = new BorderPane();
        centerContainer.setTop(centerTopContainer);
        centerContainer.setCenter(form);

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.setTop(new TopPane(main, employee));
        borderPane.setCenter(centerContainer);
        borderPane.setLeft(new LeftPane(main, employee));

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
        boolean isDataValid = validation.isDataValid(user);

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
        if (user instanceof Coach) {
            updateActiveStatus();
        }

        User.serializeUsers();
        main.showMembersManagementScreen(employee, null);
    }

    public void updateUsername() {

        if (!form.getUsernameField().getText().equals(user.getUsername())) {

            user.setUsername(form.getUsernameField().getText());
        }
    }

    public void updateEmail() {

        if (!form.getEmailField().getText().equals(user.getEmail())) {

            user.setEmail(form.getEmailField().getText());
        }
    }

    public void updatePassword() {

        if (!form.getPasswordField().getText().equals(user.getPassword())) {

            user.setPassword(form.getPasswordField().getText());
        }
    }

    public void updateFirstName() {

        if (!form.getFirstNameField().getText().equals(user.getFirstName())) {

            user.setFirstName(form.getFirstNameField().getText());
        }
    }

    public void updateLastName() {

        if (!form.getLastNameField().getText().equals(user.getLastName())) {

            user.setLastName(form.getLastNameField().getText());
        }
    }

    public void updateBirthDate() {

        if (!form.getBirthDateField().getValue().equals(user.getBirthDate())) {

            user.setBirthDate(form.getBirthDateField().getValue());
        }
    }

    public void updateActiveStatus() {

        ((Coach) user).setActive(form.getActiveCheckbox().isSelected());
    }
}
