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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class EventsManagementScreen {

    private Main main;

    private List<Event> events;

    private Employee employee;

    private BorderPane borderPane;
    private CenterPane centerPane;

    private Button addClassesButton;
    private Button addOtherEventButton;
    private Button findEventButton;

    private TableView<Event> tableView;

    public EventsManagementScreen(Main main, Employee employee, List<Event> events) {

        if (events != null) {
            this.events = events;
        }
        this.main = main;
        this.employee = employee;
    }

    public Pane getView() {

        centerPane = new CenterPane();

        addClassesButton = new Button("Add classes");
        addClassesButton.setOnAction(e -> main.showScheduleScreen(employee));

        addOtherEventButton = new Button("Add other event");
        addOtherEventButton.setOnAction(e -> main.showAddOtherEventScreen(employee));

        findEventButton = new Button("Find event");
        findEventButton.setOnAction(e -> main.showFindEventScreen(employee));

        ObservableList<Event> eventsObservableList = FXCollections.observableArrayList();
        if (events == null) {
            events = Event.getEvents();
        }
        eventsObservableList.addAll(events);

        initTable(eventsObservableList);

        ScrollPane scrollPane = new ScrollPane(tableView);
        scrollPane.setFitToWidth(true);

        centerPane.getChildren().addAll(addClassesButton, addOtherEventButton, findEventButton, scrollPane);

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.setTop(new TopPane(main, employee));
        borderPane.setCenter(centerPane);
        borderPane.setLeft(new LeftPane(main, employee));

        return borderPane;
    }

    private void initTable(ObservableList<Event> eventsObservableList) {

        tableView = new TableView<>(eventsObservableList);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TableColumn<Event, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Event, String> eventTypeCol = new TableColumn<>("Type");
        eventTypeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClass().getSimpleName()));

        TableColumn<Event, String> classesTypeCol = new TableColumn<>("Classes type");
        classesTypeCol.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Classes classes) {
                return new SimpleStringProperty(classes.getClassesType().getType());
            } else {
                return new SimpleStringProperty("");
            }
        });
        TableColumn<Event, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Event, String> startDateCol = new TableColumn<>("Start date");
        startDateCol.setCellValueFactory(cellData -> {
            LocalDateTime startDateTime = cellData.getValue().getStartDateTime();
            return new SimpleStringProperty(startDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        });

        TableColumn<Event, String> endDateCol = new TableColumn<>("End date");
        endDateCol.setCellValueFactory(cellData -> {
            LocalDateTime endDateTime = cellData.getValue().getEndDateTime();
            return new SimpleStringProperty(endDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        });

        TableColumn<Event, String> coachCol = new TableColumn<>("Coach");
        coachCol.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Classes classes) {
                return new SimpleStringProperty("ID: " +
                        classes.getCoach().getId() + ", " +
                        classes.getCoach().getFirstName() + " " +
                        classes.getCoach().getLastName());
            } else {
                return new SimpleStringProperty("");
            }
        });

        TableColumn<Event, String> roomCol = new TableColumn<>("Room");
        roomCol.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Classes classes) {
                return new SimpleStringProperty(String.valueOf(classes.getRoom().getNumber()));
            } else {
                return new SimpleStringProperty("");
            }
        });

        TableColumn<Event, Void> participantsCol = new TableColumn<>();
        participantsCol.setCellFactory(param -> createButtonTableCell("Participants", this::showParticipants));

        TableColumn<Event, Void> removeEventCol = new TableColumn<>();
        removeEventCol.setCellFactory(param -> createButtonTableCell("Remove", this::remove));

        tableView.getColumns().add(idCol);
        tableView.getColumns().add(eventTypeCol);
        tableView.getColumns().add(classesTypeCol);
        tableView.getColumns().add(nameCol);
        tableView.getColumns().add(startDateCol);
        tableView.getColumns().add(endDateCol);
        tableView.getColumns().add(coachCol);
        tableView.getColumns().add(roomCol);
        tableView.getColumns().add(participantsCol);
        tableView.getColumns().add(removeEventCol);

        tableView.setPrefHeight(300);
    }

    private TableCell<Event, Void> createButtonTableCell(String buttonText, Consumer<Event> action) {
        return new TableCell<>() {
            private final Button actionButton = new Button(buttonText);

            {
                switch (buttonText) {
                    case "Participants" -> actionButton.getStyleClass().add("button-participants");
                    case "Remove" -> actionButton.getStyleClass().add("button-remove");
                }

                actionButton.setOnAction(e -> {

                    Event event = getTableView().getItems().get(getIndex());
                    action.accept(event);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {

                super.updateItem(item, empty);

                if (empty || getTableView().getItems().get(getIndex()) == null) {
                    setGraphic(null);
                    return;
                }

                Event event = getTableView().getItems().get(getIndex());

                if ("Participants".equals(buttonText)) {
                    if (event instanceof Classes) {
                        setGraphic(actionButton);
                    } else {
                        setGraphic(null);
                    }
                } else {
                    setGraphic(actionButton);
                }
            }
        };
    }

    private void showParticipants(Event event) {

        main.showClassesParticipantsScreen(employee, (Classes) event);
    }

    private void remove(Event event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to delete this event?");
        alert.setContentText("This action cannot be undone.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Event.getEvents().remove(event);
            Event.serializeEvents();
        }
    }
}
