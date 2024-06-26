package com.p4zd4n.globogym.screens.add;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.Employee;
import com.p4zd4n.globogym.entity.Event;
import com.p4zd4n.globogym.entity.User;
import com.p4zd4n.globogym.forms.add.AddOtherEventForm;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

@Getter
public class AddOtherEventScreen implements Validatable {

    private Main main;

    private Employee employee;

    private BorderPane borderPane;
    private BorderPane centerContainer;
    private HBox centerTopContainer;
    private AddOtherEventForm form;
    private HBox bottomContainer;

    private Label errorLabel;

    private Button addButton;

    private Validation validation;

    public AddOtherEventScreen(Main main, Employee employee) {

        this.main = main;
        this.employee = employee;
    }

    public Pane getView() {

        errorLabel = new Label();
        errorLabel.getStyleClass().add("label");
        errorLabel.getStyleClass().add("error");

        centerTopContainer = new HBox();
        centerTopContainer.setAlignment(Pos.CENTER);
        centerTopContainer.getChildren().add(errorLabel);

        form = new AddOtherEventForm();
        form.getStyleClass().add("container");
        form.setVgap(10);
        form.setHgap(10);

        centerContainer = new BorderPane();
        centerContainer.setTop(centerTopContainer);
        centerContainer.setCenter(form);

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.setTop(new TopPane(main, employee));
        borderPane.setCenter(centerContainer);
        borderPane.setLeft(new LeftPane(main, employee));

        addButton = new Button("Add");
        addButton.getStyleClass().add("button");
        addButton.setOnAction(e -> addEvent());

        bottomContainer = new HBox(10);
        bottomContainer.setAlignment(Pos.CENTER);
        bottomContainer.getChildren().add(addButton);

        borderPane.setBottom(bottomContainer);

        validation = new Validation(form, this);

        return borderPane;
    }

    public void addEvent() {

        boolean areAllFieldsFilled = validation.areAllFieldsFilled();
        boolean isDataValid = validation.isDataValid();

        if (!areAllFieldsFilled) {
            errorLabel.setText("Not all fields have been filled!");
            return;
        }

        if (!isDataValid) {
            return;
        }

        LocalDate startDate = form.getEventStartDateField().getValue();
        LocalDate endDate = form.getEventEndDateField().getValue();

        LocalTime startTime = LocalTime.ofInstant(form.getEventStartTimePicker().getCalendar().getTime().toInstant(), ZoneId.systemDefault());
        LocalTime endTime = LocalTime.ofInstant(form.getEventEndTimePicker().getCalendar().getTime().toInstant(), ZoneId.systemDefault());

        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

        Event event = new Event(
                form.getEventNameTextField().getText(),
                form.getEventDescriptionTextField().getText(),
                startDateTime,
                endDateTime
        );

        employee.addEventCreatedByEmployee(event);

        User.serializeUsers();
        Event.serializeEvents();
        System.out.println("Added event successfully!");

        main.showEventsManagementScreen(employee, null);
    }
}
