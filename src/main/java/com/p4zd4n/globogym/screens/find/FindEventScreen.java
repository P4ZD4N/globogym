package com.p4zd4n.globogym.screens.find;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entities.*;
import com.p4zd4n.globogym.enums.ClassesType;
import com.p4zd4n.globogym.forms.find.FindEventForm;
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

public class FindEventScreen {

    private Main main;
    private Employee employee;
    private List<Event> foundEvents;
    private BorderPane borderPane;
    private FindEventForm form;
    private HBox bottomContainer;
    private Button findButton;

    public FindEventScreen(Main main, Employee employee) {

        this.foundEvents = new ArrayList<>();
        this.main = main;
        this.employee = employee;
    }

    public Pane getView() {

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));

        form = new FindEventForm();
        form.getStyleClass().add("container");
        form.setVgap(10);
        form.setHgap(10);

        findButton = new Button("Find");
        findButton.getStyleClass().add("button");
        findButton.setOnAction(e -> findEvents());

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

    private void findEvents() {

        findEventsByType();
        findEventById();
        findEventsByName();
        findEventsByStartDate();
        findEventsByClassesType();
        findEventsByCoach();
        findEventsByRoom();

        main.showEventsManagementScreen(employee, foundEvents);
    }

    private void findEventsByType() {

        if (!form.getEventCheckbox().isSelected() && !form.getClassesCheckbox().isSelected()) {
            Event.getEvents().stream()
                    .filter(event -> event != null && !(event instanceof Classes))
                    .forEach(foundEvents::add);
            Event.getEvents().stream()
                    .filter(user -> user instanceof Classes)
                    .forEach(foundEvents::add);
            return;
        }

        List<Event> eventsFoundByType = new ArrayList<>();

        if (form.getEventCheckbox().isSelected()) {
            Event.getEvents().stream()
                    .filter(event -> event instanceof Event && !(event instanceof Classes))
                    .forEach(eventsFoundByType::add);
        }

        if (form.getClassesCheckbox().isSelected()) {
            Event.getEvents().stream()
                    .filter(event -> event instanceof Classes)
                    .forEach(eventsFoundByType::add);
        }

        foundEvents.addAll(eventsFoundByType);
    }

    private void findEventById() {

        if (form.getIdTextField().getText().isBlank()) {
            return;
        }

        List<Event> eventFoundById = Event.getEvents()
                .stream()
                .filter(event -> event.getId().equals(Long.valueOf(form.getIdTextField().getText())))
                .toList();

        foundEvents.retainAll(eventFoundById);
    }

    private void findEventsByName() {

        if (form.getEventNameTextField().getText().isBlank()) {
            return;
        }

        List<Event> eventsFoundByName = Event.getEvents()
                .stream()
                .filter(event -> event.getName().toLowerCase().contains(form.getEventNameTextField().getText().toLowerCase()))
                .toList();

        foundEvents.retainAll(eventsFoundByName);
    }

    private void findEventsByStartDate() {

        LocalDate minStartDate = form.getMinDateField().getValue();
        LocalDate maxStartDate = form.getMaxDateField().getValue();

        if (minStartDate == null && maxStartDate == null) {
            return;
        }

        List<Event> eventsFoundByMinStartDate = new ArrayList<>();

        if (minStartDate != null) {

            Event.getEvents()
                    .stream()
                    .filter(event ->
                            event.getStartDateTime().toLocalDate().isAfter(minStartDate) ||
                            event.getStartDateTime().toLocalDate().isEqual(minStartDate)
                    )
                    .forEach(eventsFoundByMinStartDate::add);
        }

        List<Event> eventsFoundByMaxStartDate = new ArrayList<>();

        if (maxStartDate != null) {

            Event.getEvents()
                    .stream()
                    .filter(event ->
                            event.getStartDateTime().toLocalDate().isBefore(maxStartDate) ||
                            event.getStartDateTime().toLocalDate().isEqual(maxStartDate)
                    )
                    .forEach(eventsFoundByMaxStartDate::add);
        }

        eventsFoundByMinStartDate.retainAll(eventsFoundByMaxStartDate);

        foundEvents.retainAll(eventsFoundByMinStartDate);
    }

    private void findEventsByClassesType() {

        String selectedType = form.getClassesTypeComboBox().getValue();

        if (selectedType == null) {
            return;
        }

        List<Event> eventsFoundByClassesType = Event.getEvents()
                .stream()
                .filter(event -> event instanceof Classes classes && classes.getClassesType().equals(ClassesType.getByString(selectedType)))
                .toList();

        foundEvents.retainAll(eventsFoundByClassesType);
    }

    private void findEventsByCoach() {

        String selectedCoach = form.getCoachComboBox().getValue();

        if (selectedCoach == null) {
            return;
        }

        String[] stringElements = selectedCoach.split(" ");
        Long coachId = Long.parseLong(stringElements[1].substring(0, stringElements[1].length() - 1));

        List<Event> eventsFoundByCoach = Event.getEvents()
                .stream()
                .filter(event -> event instanceof Classes classes && classes.getCoach().getId().equals(coachId))
                .toList();

        foundEvents.retainAll(eventsFoundByCoach);
    }

    private void findEventsByRoom() {

        Integer selectedRoomNumber = form.getRoomComboBox().getValue();

        if (selectedRoomNumber == null) {
            return;
        }

        List<Event> eventsFoundByRoom = Event.getEvents()
                .stream()
                .filter(event -> event instanceof Classes classes && classes.getRoom().getNumber().equals(selectedRoomNumber))
                .toList();

        foundEvents.retainAll(eventsFoundByRoom);
    }
}
