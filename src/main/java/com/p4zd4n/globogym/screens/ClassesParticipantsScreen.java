package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.*;
import com.p4zd4n.globogym.panes.CenterPane;
import com.p4zd4n.globogym.panes.LeftPane;
import com.p4zd4n.globogym.panes.TopPane;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ClassesParticipantsScreen {

    private Main main;

    private Classes classes;

    private Employee employee;

    private BorderPane borderPane;
    private CenterPane centerPane;

    private Label classesTitleLabel;
    private Label placesStatusLabel;

    private Button addParticipantButton;

    private TableView<ClubMember> tableView;

    public ClassesParticipantsScreen(Main main, Employee employee, Classes classes) {

        this.main = main;
        this.employee = employee;
        this.classes = classes;
    }

    public Pane getView() {

        centerPane = new CenterPane();

        classesTitleLabel = new Label(classes.getName() + " participants");
        placesStatusLabel = new Label("Places status: " + classes.getParticipants().size() + " / " + classes.getRoom().getCapacity());

        addParticipantButton = new Button("Add participant");
        addParticipantButton.setOnAction(e -> showUsersList());

        ObservableList<ClubMember> participantsObservableList = FXCollections.observableArrayList();
        participantsObservableList.addAll(classes.getParticipants());
        initTable(participantsObservableList);

        ScrollPane scrollPane = new ScrollPane(tableView);
        scrollPane.setFitToWidth(true);

        centerPane.getChildren().addAll(classesTitleLabel, placesStatusLabel, addParticipantButton, scrollPane);

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.setTop(new TopPane(main, employee));
        borderPane.setCenter(centerPane);
        borderPane.setLeft(new LeftPane(main, employee));

        return borderPane;
    }

    private void initTable(ObservableList<ClubMember> participantsObservableList) {

        tableView = new TableView<>(participantsObservableList);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TableColumn<ClubMember, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<ClubMember, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<ClubMember, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<ClubMember, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<ClubMember, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<ClubMember, Void> removeMemberCol = new TableColumn<>();
        removeMemberCol.setCellFactory(param -> createButtonTableCell(this::remove));

        tableView.getColumns().add(idCol);
        tableView.getColumns().add(usernameCol);
        tableView.getColumns().add(firstNameCol);
        tableView.getColumns().add(lastNameCol);
        tableView.getColumns().add(emailCol);
        tableView.getColumns().add(removeMemberCol);

        tableView.setPrefHeight(300);
    }

    private TableCell<ClubMember, Void> createButtonTableCell(Consumer<ClubMember> action) {
        return new TableCell<>() {
            private final Button actionButton = new Button("Remove");

            {
                actionButton.getStyleClass().add("button-remove");

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

    private void remove(ClubMember clubMember) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to remove this member from classes?");
        alert.setContentText("This action cannot be undone.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            classes.getParticipants().remove(clubMember);
            clubMember.removeClassesParticipatedIn(classes);
            User.serializeUsers();
            Event.serializeEvents();
        }
    }

    private void showUsersList() {

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(main.getPrimaryStage());

        VBox dialogVbox = new VBox(20);
        dialogVbox.setPadding(new Insets(10));

        TextField searchField = new TextField();
        searchField.setPromptText("Search by last name");

        ListView<String> usersListView = new ListView<>();
        ObservableList<String> users = FXCollections.observableArrayList(
                User.getUsers().stream()
                        .filter(user -> user instanceof ClubMember)
                        .map(user -> user.getUsername() + " (" + user.getFirstName() + " " + user.getLastName() + ")")
                        .collect(Collectors.toList())
        );
        usersListView.setItems(users);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {

            List<String> filteredUsers = User.getUsers().stream()
                    .filter(user -> user instanceof ClubMember)
                    .filter(user -> user.getLastName().toLowerCase().contains(newValue.toLowerCase()))
                    .map(user -> user.getUsername() + " (" + user.getFirstName() + " " + user.getLastName() + ")")
                    .collect(Collectors.toList());

            usersListView.setItems(FXCollections.observableArrayList(filteredUsers));
        });

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {

            String selectedUser = usersListView.getSelectionModel().getSelectedItem();

            if (selectedUser != null) {

                String username = selectedUser.split(" ")[0];
                ClubMember clubMember = (ClubMember) User.findByUsername(username);

                if (clubMember != null && !classes.getParticipants().contains(clubMember)) {
                    classes.getParticipants().add(clubMember);
                    updatePlacesStatusLabel();
                    tableView.getItems().add(clubMember);
                    clubMember.addClassesParticipatedIn(classes);
                    User.serializeUsers();
                    Event.serializeEvents();
                    dialog.close();
                }
            }
        });

        dialogVbox.getChildren().addAll(new Label("Select a user to add:"), searchField, usersListView, addButton);

        Scene dialogScene = new Scene(dialogVbox, 300, 300);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void updatePlacesStatusLabel() {
        placesStatusLabel.setText("Places status: " + classes.getParticipants().size() + " / " + classes.getRoom().getCapacity());
    }
}
