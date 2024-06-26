package com.p4zd4n.globogym.screens.management;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.*;
import com.p4zd4n.globogym.panes.CenterPane;
import com.p4zd4n.globogym.panes.LeftPane;
import com.p4zd4n.globogym.panes.TopPane;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class UsersManagementScreen {

    private Main main;

    private List<User> users;

    private Employee employee;

    private BorderPane borderPane;
    private CenterPane centerPane;

    private Button addMemberButton;
    private Button findMemberButton;

    private TableView<User> tableView;

    public UsersManagementScreen(Main main, Employee employee, List<User> users) {


        if (users != null && employee instanceof Manager) {
            this.users = users;
        }

        if (users != null && !(employee instanceof Manager)) {
            this.users = users.stream()
                    .filter(user -> user instanceof ClubMember)
                    .map(user -> (ClubMember) user)
                    .collect(Collectors.toList());
        }
        this.main = main;
        this.employee = employee;
    }

    public Pane getView() {

        centerPane = new CenterPane();

        addMemberButton = new Button("Add member");
        addMemberButton.setOnAction(e -> main.showAddUserScreen(employee));

        findMemberButton = new Button("Find member");
        findMemberButton.setOnAction(e -> main.showFindUserScreen(employee));

        ObservableList<User> usersObservableList = FXCollections.observableArrayList();
        if (users == null && employee instanceof Manager) {
            users = User.getUsers();
        }

        if (users == null && !(employee instanceof Manager)) {
            users = User.getUsers()
                    .stream()
                    .filter(user -> user instanceof ClubMember)
                    .toList();
        }
        usersObservableList.addAll(users);

        initTable(usersObservableList);

        ScrollPane scrollPane = new ScrollPane(tableView);
        scrollPane.setFitToWidth(true);

        centerPane.getChildren().addAll(addMemberButton, findMemberButton, scrollPane);

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.setTop(new TopPane(main, employee));
        borderPane.setCenter(centerPane);
        borderPane.setLeft(new LeftPane(main, employee));

        return borderPane;
    }

    private void initTable(ObservableList<User> usersObservableList) {

        tableView = new TableView<>(usersObservableList);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TableColumn<User, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<User, String> accountTypeCol = new TableColumn<>("Type");
        accountTypeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClass().getSimpleName()));

        TableColumn<User, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<User, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<User, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<User, String> birthDateCol = new TableColumn<>("Birth date");
        birthDateCol.setCellValueFactory(new PropertyValueFactory<>("birthDate"));

        TableColumn<User, String> membershipCardStatusCol = new TableColumn<>("Card");
        membershipCardStatusCol.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof ClubMember clubMember) {
                return new SimpleStringProperty(clubMember.getMembershipCard() == null
                        ? "No card"
                        : clubMember.getMembershipCard().getMembershipCardStatus().toString());
            } else {
                return new SimpleStringProperty("");
            }
        });

        TableColumn<User, String> activeCol = new TableColumn<>("Active");
        activeCol.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Coach coach) {
                return new SimpleStringProperty(coach.isActive() ? "Yes" : "No");
            } else {
                return new SimpleStringProperty("");
            }
        });

        TableColumn<User, Void> updateMemberCol = new TableColumn<>();
        updateMemberCol.setCellFactory(param -> createButtonTableCell("Update", this::update));

        TableColumn<User, Void> removeMemberCol = new TableColumn<>();
        removeMemberCol.setCellFactory(param -> createButtonTableCell("Remove", this::remove));

        tableView.getColumns().add(idCol);
        tableView.getColumns().add(accountTypeCol);
        tableView.getColumns().add(usernameCol);
        tableView.getColumns().add(firstNameCol);
        tableView.getColumns().add(lastNameCol);
        tableView.getColumns().add(emailCol);
        tableView.getColumns().add(birthDateCol);
        tableView.getColumns().add(membershipCardStatusCol);
        tableView.getColumns().add(activeCol);
        tableView.getColumns().add(updateMemberCol);
        tableView.getColumns().add(removeMemberCol);

        tableView.setPrefHeight(300);
    }

    private TableCell<User, Void> createButtonTableCell(String buttonText, Consumer<User> action) {
        return new TableCell<>() {
            private final Button actionButton = new Button(buttonText);

            {
                switch (buttonText) {
                    case "Update" -> actionButton.getStyleClass().add("button-update");
                    case "Remove" -> actionButton.getStyleClass().add("button-remove");
                }

                actionButton.setOnAction(event -> {

                    User user = getTableView().getItems().get(getIndex());
                    action.accept(user);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null);
                else setGraphic(actionButton);
            }
        };
    }

    private void update(User user) {

        main.showUpdateUserScreen(employee, user);
    }

    private void remove(User user) {

        Optional<User> userToRemove = User.getUsers()
                .stream()
                .filter(u -> u.equals(user))
                .findFirst();

        userToRemove.ifPresent(u -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Are you sure you want to delete this user?");
            alert.setContentText("This action cannot be undone.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                User.getUsers().remove(user);
                User.serializeUsers();
            }
        });
    }
}
