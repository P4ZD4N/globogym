package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.ClubMember;
import com.p4zd4n.globogym.entity.Coach;
import com.p4zd4n.globogym.entity.Employee;
import com.p4zd4n.globogym.entity.User;
import com.p4zd4n.globogym.forms.AddUserForm;
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
public class AddUserScreen implements Validatable {

    private Main main;

    private Employee employee;

    private BorderPane borderPane;
    private BorderPane centerContainer;
    private HBox centerTopContainer;
    private AddUserForm form;
    private HBox bottomContainer;

    private Label errorLabel;

    private Button addButton;

    private Validation validation;

    public AddUserScreen(Main main, Employee employee) {

        this.main = main;
        this.employee = employee;
    }

    public Pane getView() {

        errorLabel = new Label();
        errorLabel.getStyleClass().add("label");
        errorLabel.getStyleClass().add("error");

        centerTopContainer = new HBox();
        centerTopContainer.setAlignment(Pos.CENTER);
        centerTopContainer.getChildren().add(errorLabel);

        form = new AddUserForm();
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

        addButton = new Button("Add");
        addButton.getStyleClass().add("button");
        addButton.setOnAction(e -> addUser());

        bottomContainer = new HBox(10);
        bottomContainer.setAlignment(Pos.CENTER);
        bottomContainer.getChildren().add(addButton);

        borderPane.setBottom(bottomContainer);

        validation = new Validation(form, this);

        return borderPane;
    }

    public void addUser() {

        boolean areAllFieldsFilled = validation.areAllFieldsFilled();
        boolean isDataValid = validation.isDataValid();

        if (!areAllFieldsFilled) {
            errorLabel.setText("Not all fields have been filled!");
            return;
        }

        if (!isDataValid) {
            return;
        }

        if (form.getGroup().getSelectedToggle().equals(form.getClubMemberRadioButton())) {

            ClubMember clubMember = new ClubMember(
                    form.getUsernameField().getText(),
                    form.getEmailField().getText(),
                    form.getPasswordField().getText(),
                    form.getFirstNameField().getText(),
                    form.getLastNameField().getText(),
                    form.getBirthDateField().getValue());

            User.serializeUsers();

            System.out.println("Club member registered successfully!");

        } else {

            Coach coach = new Coach(
                    form.getUsernameField().getText(),
                    form.getEmailField().getText(),
                    form.getPasswordField().getText(),
                    form.getFirstNameField().getText(),
                    form.getLastNameField().getText(),
                    form.getBirthDateField().getValue());

            if (form.getActiveCheckbox().isSelected()) {
                coach.setActive(true);
            }

            User.serializeUsers();

            System.out.println("Coach registered successfully!");
        }

        main.showMembersManagementScreen(employee, null);
    }
}
