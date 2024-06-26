package com.p4zd4n.globogym.screens.update;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entities.*;
import com.p4zd4n.globogym.forms.update.UpdateRoomForm;
import com.p4zd4n.globogym.panes.LeftPane;
import com.p4zd4n.globogym.panes.TopPane;
import com.p4zd4n.globogym.utils.Validatable;
import com.p4zd4n.globogym.utils.Validation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import lombok.Getter;

@Getter
public class UpdateRoomScreen implements Validatable {

    private Main main;

    private Manager manager;
    private Room room;

    private BorderPane borderPane;
    private BorderPane centerContainer;
    private HBox centerTopContainer;
    private UpdateRoomForm form;
    private HBox bottomContainer;

    private Label errorLabel;

    private Button updateButton;

    private Validation validation;

    public UpdateRoomScreen(Main main, Manager manager, Room room) {

        this.main = main;
        this.manager = manager;
        this.room = room;
    }

    public Pane getView() {

        errorLabel = new Label();
        errorLabel.getStyleClass().add("label");
        errorLabel.getStyleClass().add("error");

        centerTopContainer = new HBox();
        centerTopContainer.setAlignment(Pos.CENTER);
        centerTopContainer.getChildren().add(errorLabel);

        form = new UpdateRoomForm(room);
        form.getStyleClass().add("container");
        form.setVgap(10);
        form.setHgap(10);

        centerContainer = new BorderPane();
        centerContainer.setTop(centerTopContainer);
        centerContainer.setCenter(form);

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.setTop(new TopPane(main, manager));
        borderPane.setCenter(centerContainer);
        borderPane.setLeft(new LeftPane(main, manager));

        updateButton = new Button("Update");
        updateButton.getStyleClass().add("button");
        updateButton.setOnAction(e -> updateRoom());

        bottomContainer = new HBox(10);
        bottomContainer.setAlignment(Pos.CENTER);
        bottomContainer.getChildren().add(updateButton);

        borderPane.setBottom(bottomContainer);

        validation = new Validation(form, this);

        return borderPane;
    }

    public void updateRoom() {

        boolean areAllFieldsFilled = validation.areAllFieldsFilled();
        boolean isDataValid = validation.isDataValid();

        if (!areAllFieldsFilled) {
            errorLabel.setText("Not all fields have been filled!");
            return;
        }

        if (!isDataValid) {
            return;
        }

        updateRoomNumber();
        updateRoomCapacity();

        Room.serializeRooms();
        main.showRoomsManagementScreen(manager, null);
    }

    private void updateRoomNumber() {

        if (!form.getRoomNumberField().getText().equals(String.valueOf(room.getNumber()))) {

            room.setNumber(Integer.parseInt(form.getRoomNumberField().getText()));
        }
    }

    private void updateRoomCapacity() {

        if (!form.getRoomCapacityField().getText().equals(String.valueOf(room.getCapacity()))) {

            room.setCapacity(Integer.parseInt(form.getRoomCapacityField().getText()));
        }
    }
}
