package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.*;
import com.p4zd4n.globogym.forms.AddRoomForm;
import com.p4zd4n.globogym.panes.LeftPane;
import com.p4zd4n.globogym.panes.TopPane;
import com.p4zd4n.globogym.util.Validatable;
import com.p4zd4n.globogym.util.Validation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import lombok.Getter;

@Getter
public class AddRoomScreen implements Validatable {

    private Main main;

    private Manager manager;

    private BorderPane borderPane;
    private BorderPane centerContainer;
    private HBox centerTopContainer;
    private AddRoomForm form;
    private HBox bottomContainer;

    private Label errorLabel;

    private Button addButton;

    private Validation validation;


    public AddRoomScreen(Main main, Manager manager) {

        this.main = main;
        this.manager = manager;
    }

    public Pane getView() {

        errorLabel = new Label();
        errorLabel.getStyleClass().add("label");
        errorLabel.getStyleClass().add("error");

        centerTopContainer = new HBox();
        centerTopContainer.setAlignment(Pos.CENTER);
        centerTopContainer.getChildren().add(errorLabel);

        form = new AddRoomForm();
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

        addButton = new Button("Add");
        addButton.getStyleClass().add("button");
        addButton.setOnAction(e -> addRoom());

        bottomContainer = new HBox(10);
        bottomContainer.setAlignment(Pos.CENTER);
        bottomContainer.getChildren().add(addButton);

        borderPane.setBottom(bottomContainer);

        validation = new Validation(form, this);

        return borderPane;
    }

    public void addRoom() {

        boolean areAllFieldsFilled = validation.areAllFieldsFilled();
        boolean isDataValid = validation.isDataValid();

        if (!areAllFieldsFilled) {
            errorLabel.setText("Not all fields have been filled!");
            return;
        }

        if (!isDataValid) {
            return;
        }

        Room room = new Room(
                Integer.parseInt(form.getRoomNumberField().getText()),
                Integer.parseInt(form.getRoomCapacityField().getText()));

        Room.serializeRooms();
        System.out.println("Added room successfully!");

        main.showRoomsManagementScreen(manager, null);
    }
}
