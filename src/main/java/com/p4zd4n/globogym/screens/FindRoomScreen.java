package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.*;
import com.p4zd4n.globogym.forms.FindRoomForm;
import com.p4zd4n.globogym.panes.LeftPane;
import com.p4zd4n.globogym.panes.TopPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class FindRoomScreen {

    private Main main;

    private Employee employee;

    private List<Room> foundRooms;

    private BorderPane borderPane;
    private FindRoomForm form;
    private HBox bottomContainer;

    private Button findButton;

    public FindRoomScreen(Main main, Employee employee) {

        this.foundRooms = new ArrayList<>();
        this.main = main;
        this.employee = employee;
    }

    public Pane getView() {

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));

        form = new FindRoomForm();
        form.getStyleClass().add("container");
        form.setVgap(10);
        form.setHgap(10);

        findButton = new Button("Find");
        findButton.getStyleClass().add("button");
        findButton.setOnAction(e -> findRooms());

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

    private void findRooms() {

        foundRooms.addAll(Room.getRooms());

        findRoomByNumber();
        findRoomsByCapacity();

        main.showRoomsManagementScreen(employee, foundRooms);
    }

    private void findRoomByNumber() {

        if (form.getRoomNumberField().getText().isBlank()) {
            return;
        }

        List<Room> roomFoundByNumber = Room.getRooms()
                .stream()
                .filter(room -> room.getNumber().equals(Integer.valueOf(form.getRoomNumberField().getText())))
                .toList();

        foundRooms.retainAll(roomFoundByNumber);
    }

    private void findRoomsByCapacity() {

        String minCapacity = form.getMinRoomCapacityField().getText();
        String maxCapacity = form.getMaxRoomCapacityField().getText();

        if (minCapacity.isBlank() && maxCapacity.isBlank()) {
            return;
        }

        if (!form.getMinRoomCapacityField().getText().isBlank()) {

            List<Room> roomsFoundByMinCapacity = Room.getRooms()
                    .stream()
                    .filter(room -> room.getCapacity() >= Integer.parseInt(minCapacity))
                    .toList();

            foundRooms.retainAll(roomsFoundByMinCapacity);
        }

        if (!form.getMaxRoomCapacityField().getText().isBlank()) {

            List<Room> roomsFoundByMaxCapacity = Room.getRooms()
                    .stream()
                    .filter(room -> Integer.parseInt(maxCapacity) >= room.getCapacity())
                    .toList();

            foundRooms.retainAll(roomsFoundByMaxCapacity);
        }
    }
}
