package com.p4zd4n.globogym.screens.auth;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.ClubMember;
import com.p4zd4n.globogym.entity.Coach;
import com.p4zd4n.globogym.entity.User;
import com.p4zd4n.globogym.forms.auth.RegistrationForm;
import com.p4zd4n.globogym.util.Validatable;
import com.p4zd4n.globogym.util.Validation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import lombok.Getter;

@Getter
public class RegistrationScreen implements Validatable {

    private Main main;

    private BorderPane borderPane;
    private HBox topContainer;
    private RegistrationForm form;
    private HBox bottomContainer;

    private Label errorLabel;

    private Button registerButton;
    private Button backButton;

    private Validation validation;

    public RegistrationScreen(Main main) {

        this.main = main;
    }

    public Pane getView() {

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));

        errorLabel = new Label();
        errorLabel.getStyleClass().add("label");
        errorLabel.getStyleClass().add("error");

        topContainer = new HBox();
        topContainer.setAlignment(Pos.CENTER);
        topContainer.getChildren().add(errorLabel);

        form = new RegistrationForm();
        form.getStyleClass().add("container");
        form.setVgap(10);
        form.setHgap(10);

        registerButton = new Button("Register");
        registerButton.getStyleClass().add("button");
        registerButton.setOnAction(e -> registerUser());

        backButton = new Button("Back");
        backButton.getStyleClass().add("button");
        backButton.setOnAction(e -> main.showMainScreen());

        bottomContainer = new HBox(10);
        bottomContainer.setAlignment(Pos.CENTER);
        bottomContainer.getChildren().add(backButton);
        bottomContainer.getChildren().add(registerButton);

        borderPane.setTop(topContainer);
        borderPane.setCenter(form);
        borderPane.setBottom(bottomContainer);

        validation = new Validation(form, this);

        return borderPane;
    }

    private void registerUser() {

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
            User.serializeUsers();
            System.out.println("Coach registered successfully!");
        }

        main.showMainScreen();
    }


}
