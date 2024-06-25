package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.*;
import com.p4zd4n.globogym.panes.CenterPane;
import com.p4zd4n.globogym.panes.LeftPane;
import com.p4zd4n.globogym.panes.TopPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.function.Consumer;

public class RoomsManagementScreen {

    private Main main;

    private Manager manager;

    private List<Room> rooms;

    private BorderPane borderPane;
    private CenterPane centerPane;

    private Button addRoomButton;
    private Button findRoomButton;

    private TableView<Room> tableView;

    public RoomsManagementScreen(Main main, Manager manager, List<Room> rooms) {

        if (rooms != null) {
            this.rooms = rooms;
        }
        this.main = main;
        this.manager = manager;
    }

    public Pane getView() {

        centerPane = new CenterPane();

        addRoomButton = new Button("Add room");
        addRoomButton.setOnAction(e -> main.showAddUserScreen(manager));

        findRoomButton = new Button("Find room");
        findRoomButton.setOnAction(e -> main.showFindRoomScreen(manager));

        ObservableList<Room> roomsObservableList = FXCollections.observableArrayList();
        if (rooms == null) {
            rooms = Room.getRooms();
        }
        roomsObservableList.addAll(rooms);

        initTable(roomsObservableList);

        ScrollPane scrollPane = new ScrollPane(tableView);
        scrollPane.setFitToWidth(true);

        centerPane.getChildren().addAll(addRoomButton, findRoomButton, scrollPane);

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.setTop(new TopPane(main, manager));
        borderPane.setCenter(centerPane);
        borderPane.setLeft(new LeftPane(main, manager));

        return borderPane;
    }

    private void initTable(ObservableList<Room> roomsObservableList) {

        tableView = new TableView<>(roomsObservableList);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TableColumn<Room, String> roomNumberCol = new TableColumn<>("Number");
        roomNumberCol.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn<Room, String> capacityCol = new TableColumn<>("Capacity");
        capacityCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        TableColumn<Room, Void> updateRoomCol = new TableColumn<>();
        updateRoomCol.setCellFactory(param -> createButtonTableCell("Update", this::update));

        TableColumn<Room, Void> removeRoomCol = new TableColumn<>();
        removeRoomCol.setCellFactory(param -> createButtonTableCell("Remove", this::remove));

        tableView.getColumns().add(roomNumberCol);
        tableView.getColumns().add(capacityCol);
        tableView.getColumns().add(updateRoomCol);
        tableView.getColumns().add(removeRoomCol);

        tableView.setPrefHeight(300);
    }

    private TableCell<Room, Void> createButtonTableCell(String buttonText, Consumer<Room> action) {
        return new TableCell<>() {
            private final Button actionButton = new Button(buttonText);

            {
                switch (buttonText) {
                    case "Update" -> actionButton.getStyleClass().add("button-update");
                    case "Remove" -> actionButton.getStyleClass().add("button-remove");
                }

                actionButton.setOnAction(event -> {

                    Room room = getTableView().getItems().get(getIndex());
                    action.accept(room);
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

    private void update(Room room) {

        System.out.println("Update");
    }

    private void remove(Room room) {

        System.out.println("Remove");
    }
}
