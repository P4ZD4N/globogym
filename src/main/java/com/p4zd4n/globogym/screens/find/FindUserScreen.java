package com.p4zd4n.globogym.screens.find;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entities.*;
import com.p4zd4n.globogym.enums.MembershipCardStatus;
import com.p4zd4n.globogym.forms.find.FindUserForm;
import com.p4zd4n.globogym.panes.LeftPane;
import com.p4zd4n.globogym.panes.TopPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.time.LocalDate;
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

        form = new FindUserForm(employee);
        form.getStyleClass().add("container");
        form.setVgap(10);
        form.setHgap(10);

        findButton = new Button("Find");
        findButton.getStyleClass().add("button");
        findButton.setOnAction(e -> findUsers());

        bottomContainer = new HBox(10);
        bottomContainer.setAlignment(Pos.CENTER);
        bottomContainer.getChildren().add(findButton);

        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.setTop(new TopPane(main, employee));
        borderPane.setLeft(new LeftPane(main, employee));
        borderPane.setCenter(form);
        borderPane.setBottom(bottomContainer);

        return borderPane;
    }

    private void findUsers() {

        findUsersByAccountType();
        findUserById();
        findUsersByUsername();
        findUsersByEmail();
        findUsersByFirstName();
        findUsersByLastName();
        findUsersByBirthDate();
        findUsersByMembershipCardStatus();
        findUsersByActiveStatus();

        main.showMembersManagementScreen(employee, foundUsers);
    }

    private void findUsersByAccountType() {

        if (!form.getClubMemberCheckBox().isSelected() &&
            !form.getCoachCheckbox().isSelected() &&
            !form.getEmployeeCheckbox().isSelected()) {
            User.getUsers().stream()
                    .filter(user -> user instanceof ClubMember && !(user instanceof Coach))
                    .forEach(foundUsers::add);
            User.getUsers().stream()
                    .filter(user -> user instanceof Coach)
                    .forEach(foundUsers::add);
            User.getUsers().stream()
                    .filter(user -> user instanceof Employee && !(user instanceof Manager))
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

        if (form.getEmployeeCheckbox().isSelected()) {
            User.getUsers().stream()
                    .filter(user -> user instanceof Employee && !(user instanceof Manager))
                    .forEach(usersFoundByAccountType::add);
        }

        foundUsers.addAll(usersFoundByAccountType);
    }

    private void findUserById() {

        if (form.getIdTextField().getText().isBlank()) {
            return;
        }

        List<User> userFoundById = User.getUsers()
                .stream()
                .filter(user -> user.getId().equals(Long.valueOf(form.getIdTextField().getText())))
                .toList();

        foundUsers.retainAll(userFoundById);
    }

    private void findUsersByUsername() {

        if (form.getUsernameField().getText().isBlank()) {
            return;
        }

        List<User> usersFoundByUsername = User.getUsers()
                .stream()
                .filter(user -> user.getUsername().toLowerCase().contains(form.getUsernameField().getText().toLowerCase()))
                .toList();

        foundUsers.retainAll(usersFoundByUsername);
    }

    private void findUsersByEmail() {

        if (form.getEmailField().getText().isBlank()) {
            return;
        }

        List<User> usersFoundByEmail = User.getUsers()
                .stream()
                .filter(user -> user.getEmail().toLowerCase().contains(form.getEmailField().getText().toLowerCase()))
                .toList();

        foundUsers.retainAll(usersFoundByEmail);
    }

    private void findUsersByFirstName() {

        if (form.getFirstNameField().getText().isBlank()) {
            return;
        }

        List<User> usersFoundByFirstName = User.getUsers()
                .stream()
                .filter(user -> user.getFirstName().toLowerCase().contains(form.getFirstNameField().getText().toLowerCase()))
                .toList();

        foundUsers.retainAll(usersFoundByFirstName);
    }

    private void findUsersByLastName() {

        if (form.getLastNameField().getText().isBlank()) {
            return;
        }

        List<User> usersFoundByLastName = User.getUsers()
                .stream()
                .filter(user -> user.getLastName().toLowerCase().contains(form.getLastNameField().getText().toLowerCase()))
                .toList();

        foundUsers.retainAll(usersFoundByLastName);
    }

    private void findUsersByBirthDate() {

        LocalDate minDate = form.getMinDateField().getValue();
        LocalDate maxDate = form.getMaxDateField().getValue();

        if (minDate == null && maxDate == null) {
            return;
        }

        List<User> usersFoundByBirthDate = new ArrayList<>();

        if (minDate != null) {

            User.getUsers()
                    .stream()
                    .filter(user -> user.getBirthDate().isAfter(minDate) || user.getBirthDate().isEqual(minDate))
                    .forEach(usersFoundByBirthDate::add);
        }

        if (form.getMaxDateField().getValue() != null) {

            User.getUsers()
                    .stream()
                    .filter(user -> user.getBirthDate().isBefore(maxDate) || user.getBirthDate().isEqual(maxDate))
                    .forEach(usersFoundByBirthDate::add);
        }

        foundUsers.retainAll(usersFoundByBirthDate);
    }

    private void findUsersByMembershipCardStatus() {

        if (form.getMembershipCardStatusComboBox().getValue() == null) {
            return;
        }

        if (form.getMembershipCardStatusComboBox().getValue().equals(MembershipCardStatus.NO_CARD)) {

            List<User> usersFoundByMembershipCardStatus = User.getUsers()
                    .stream()
                    .filter(user ->  user instanceof ClubMember)
                    .filter(user -> ((ClubMember) user).getMembershipCard() == null)
                    .toList();

            foundUsers.retainAll(usersFoundByMembershipCardStatus);
            return;
        }

        if (form.getMembershipCardStatusComboBox().getValue().equals(MembershipCardStatus.ACTIVE)) {

            List<User> usersFoundByMembershipCardStatus = User.getUsers()
                    .stream()
                    .filter(user ->  user instanceof ClubMember)
                    .filter(user -> ((ClubMember) user).getMembershipCard() != null)
                    .filter(user -> ((ClubMember) user).getMembershipCard().getMembershipCardStatus().equals(MembershipCardStatus.ACTIVE))
                    .toList();

            foundUsers.retainAll(usersFoundByMembershipCardStatus);
            return;
        }

        if (form.getMembershipCardStatusComboBox().getValue().equals(MembershipCardStatus.EXPIRED)) {

            List<User> usersFoundByMembershipCardStatus = User.getUsers()
                    .stream()
                    .filter(user ->  user instanceof ClubMember)
                    .filter(user -> ((ClubMember) user).getMembershipCard() != null)
                    .filter(user -> ((ClubMember) user).getMembershipCard().getMembershipCardStatus().equals(MembershipCardStatus.EXPIRED))
                    .toList();

            foundUsers.retainAll(usersFoundByMembershipCardStatus);
        }
    }

    private void findUsersByActiveStatus() {

        if (
                (form.getActiveCheckbox().isSelected() && form.getInactiveCheckbox().isSelected()) ||
                (!form.getActiveCheckbox().isSelected() && !form.getInactiveCheckbox().isSelected())
        ) {

            return;
        }

        if (form.getActiveCheckbox().isSelected()) {

            List<User> usersFoundByActiveStatus = User.getUsers()
                    .stream()
                    .filter(user -> user instanceof Coach)
                    .filter(user -> ((Coach) user).isActive())
                    .toList();

            foundUsers.retainAll(usersFoundByActiveStatus);
            return;
        }


        if (form.getInactiveCheckbox().isSelected()) {
            List<User> usersFoundByActiveStatus = User.getUsers()
                    .stream()
                    .filter(user -> user instanceof Coach)
                    .filter(user -> !((Coach) user).isActive())
                    .toList();

            foundUsers.retainAll(usersFoundByActiveStatus);
        }
    }
}
