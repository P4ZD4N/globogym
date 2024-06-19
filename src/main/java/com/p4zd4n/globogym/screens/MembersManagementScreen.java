package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.ClubMember;
import com.p4zd4n.globogym.entity.Coach;
import com.p4zd4n.globogym.entity.Employee;
import com.p4zd4n.globogym.entity.User;
import com.p4zd4n.globogym.panes.CenterPane;
import com.p4zd4n.globogym.panes.LeftPane;
import com.p4zd4n.globogym.panes.TopPane;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MembersManagementScreen {

    private Main main;

    private Employee employee;

    private BorderPane borderPane;
    private CenterPane centerPane;

    private Button addMemberButton;
    private Button findMemberButton;

    private TableView<ClubMember> tableView;

    public MembersManagementScreen(Main main, Employee employee) {

        this.main = main;
        this.employee = employee;
    }

    public Pane getView() {

        centerPane = new CenterPane();

        addMemberButton = new Button("Add member");
        addMemberButton.setOnAction(e -> main.showRegistrationScreen());

        findMemberButton = new Button("Find member");
        findMemberButton.setOnAction(e -> main.showRegistrationScreen());

        ObservableList<ClubMember> usersObservableList = FXCollections.observableArrayList();
        List<ClubMember> clubMembers = User.getUsers()
                .stream().filter(user -> user instanceof ClubMember)
                .map(user -> (ClubMember) user)
                .toList();
        usersObservableList.addAll(clubMembers);

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

    private void initTable(ObservableList<ClubMember> usersObservableList) {

        tableView = new TableView<>(usersObservableList);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TableColumn<ClubMember, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<ClubMember, String> accountTypeCol = new TableColumn<>("Type");
        accountTypeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClass().getSimpleName()));

        TableColumn<ClubMember, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<ClubMember, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<ClubMember, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<ClubMember, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<ClubMember, String> birthDateCol = new TableColumn<>("Birth date");
        birthDateCol.setCellValueFactory(new PropertyValueFactory<>("birthDate"));

        TableColumn<ClubMember, String> membershipCardStatusCol = new TableColumn<>("Card");
        membershipCardStatusCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                    cellData.getValue().getMembershipCard() == null
                            ? "No card"
                            : cellData.getValue().getMembershipCard().getMembershipCardStatus().toString()
                ));

        TableColumn<ClubMember, String> activeCol = new TableColumn<>("Active");
        activeCol.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Coach coach) {
                return new SimpleStringProperty(coach.isActive() ? "Yes" : "No");
            } else {
                return new SimpleStringProperty("");
            }
        });

        TableColumn<ClubMember, Void> memberDetailsCol = new TableColumn<>();
        memberDetailsCol.setCellFactory(param -> createButtonTableCell("Details", this::displayDetails));

        TableColumn<ClubMember, Void> updateMemberCol = new TableColumn<>();
        updateMemberCol.setCellFactory(param -> createButtonTableCell("Update", this::update));

        TableColumn<ClubMember, Void> removeMemberCol = new TableColumn<>();
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
        tableView.getColumns().add(memberDetailsCol);
        tableView.getColumns().add(updateMemberCol);
        tableView.getColumns().add(removeMemberCol);

        tableView.setPrefHeight(150);
    }

    private TableCell<ClubMember, Void> createButtonTableCell(String buttonText, Consumer<ClubMember> action) {
        return new TableCell<>() {
            private final Button actionButton = new Button(buttonText);

            {
                switch (buttonText) {
                    case "Details" -> actionButton.getStyleClass().add("button-details");
                    case "Update" -> actionButton.getStyleClass().add("button-update");
                    case "Remove" -> actionButton.getStyleClass().add("button-remove");
                }

                actionButton.setOnAction(event -> {

                    ClubMember clubMember = getTableView().getItems().get(getIndex());
                    action.accept(clubMember);
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

    private void displayDetails(ClubMember clubMember) {

        System.out.println("Details for: " + clubMember.getUsername());
    }

    private void update(ClubMember clubMember) {

        System.out.println("Updated: " + clubMember.getUsername());
    }

    private void remove(ClubMember clubMember) {

        System.out.println("Deleted: " + clubMember.getUsername());
    }
}
