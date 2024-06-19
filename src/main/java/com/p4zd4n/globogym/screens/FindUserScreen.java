package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.ClubMember;
import com.p4zd4n.globogym.entity.Coach;
import com.p4zd4n.globogym.entity.Employee;
import com.p4zd4n.globogym.entity.User;
import com.p4zd4n.globogym.forms.FindUserForm;
import com.p4zd4n.globogym.forms.RegistrationForm;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class FindUserScreen {

    private Main main;

    private Employee employee;

    private List<User> foundUsers;

    private BorderPane borderPane;
    private FindUserForm form;
    private HBox bottomContainer;

    private Button findButton;

    public FindUserScreen(Main main, Employee employee) {

        this.foundUsers = new ArrayList<>();
        this.main = main;
        this.employee = employee;
    }

    public Pane getView() {

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));

        form = new FindUserForm();
        form.getStyleClass().add("container");
        form.setVgap(10);
        form.setHgap(10);

        findButton = new Button("Find");
        findButton.getStyleClass().add("button");
        findButton.setOnAction(e -> findUsers());

        bottomContainer = new HBox(10);
        bottomContainer.setAlignment(Pos.CENTER);
        bottomContainer.getChildren().add(findButton);

        borderPane.setCenter(form);
        borderPane.setBottom(bottomContainer);

        return borderPane;
    }

    private void findUsers() {

        findUsersByAccountType();

        main.showMembersManagementScreen(employee, foundUsers);
    }

    private void findUsersByAccountType() {

        if (!form.getClubMemberCheckBox().isSelected() && !form.getCoachCheckbox().isSelected()) {
            User.getUsers().stream()
                    .filter(user -> user instanceof ClubMember && !(user instanceof Coach))
                    .forEach(foundUsers::add);
            User.getUsers().stream()
                    .filter(user -> user instanceof Coach)
                    .forEach(foundUsers::add);
            return;
        }

        List<User> usersFoundByAccountType = new ArrayList<>();

        if (form.getClubMemberCheckBox().isSelected()) {
            User.getUsers().stream()
                    .filter(user -> user instanceof ClubMember && !(user instanceof Coach))
                    .forEach(usersFoundByAccountType::add);
        }

        if (form.getCoachCheckbox().isSelected()) {
            User.getUsers().stream()
                    .filter(user -> user instanceof Coach)
                    .forEach(usersFoundByAccountType::add);
        }

        foundUsers.addAll(usersFoundByAccountType);
    }
}
