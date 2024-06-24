package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.util.CustomAppointment;
import com.p4zd4n.globogym.entity.*;
import com.p4zd4n.globogym.panes.LeftPane;
import com.p4zd4n.globogym.panes.TopPane;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.StringConverter;
import jfxtras.scene.control.agenda.Agenda;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

public class ScheduleScreen {

    private Main main;

    private User user;

    private BorderPane borderPane;
    private VBox centerPane;
    private HBox navigationBox;

    private Agenda agenda;

    private LocalDateTime currentDisplayedWeek;
    private LocalDateTime startOfWeek;

    private Button previousWeekButton;
    private Button buttonAdd;
    private Button nextWeekButton;

    private DatePicker eventDate;

    private ComboBox<Integer> startHourComboBox;
    private ComboBox<Integer> startMinuteComboBox;
    private ComboBox<Integer> endHourComboBox;
    private ComboBox<Integer> endMinuteComboBox;

    private Integer selectedRoomNumber;
    private LocalDateTime eventStartDateTime;
    private LocalDateTime eventEndDateTime;
    private String eventTitle;
    private String eventDescription;

    public ScheduleScreen(Main main, User user) {

        this.main = main;
        this.user = user;
        this.currentDisplayedWeek = LocalDateTime.now()
                .with(WeekFields.of(Locale.getDefault()).dayOfWeek(), DayOfWeek.MONDAY.getValue());
    }

    public Pane getView() {

        agenda = new Agenda();
        agenda.setAllowDragging(false);
        agenda.setAllowResize(false);
        updateCalendar();

        previousWeekButton = new Button("<");
        previousWeekButton.setOnAction(e -> navigateToPreviousWeek());

        buttonAdd = new Button("Add event");
        buttonAdd.setOnAction(e -> chooseEventDate(eventDate));

        nextWeekButton = new Button(">");
        nextWeekButton.setOnAction(e -> navigateToNextWeek());

        navigationBox = new HBox(10, previousWeekButton);

        if (user instanceof Coach || user instanceof Employee) {
            navigationBox.getChildren().add(buttonAdd);
        }

        navigationBox.getChildren().add(nextWeekButton);
        navigationBox.setAlignment(Pos.CENTER);

        centerPane = new VBox(10, navigationBox, agenda);
        centerPane.setPadding(new Insets(10));

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.setTop(new TopPane(main, user));
        borderPane.setCenter(centerPane);
        borderPane.setLeft(new LeftPane(main, user));

        loadEvents();

        return borderPane;
    }

    private void updateCalendar() {

        startOfWeek = currentDisplayedWeek;
        agenda.setDisplayedLocalDateTime(startOfWeek);
    }

    private void chooseEventDate(DatePicker eventDate) {

        StringConverter<Integer> converter = new StringConverter<Integer>() {
            @Override
            public String toString(Integer n) {
                if (n == null) return "";
                if (n < 10) return "0" + n;
                else return n.toString();
            }

            @Override
            public Integer fromString(String s) {
                return Integer.parseInt(s);
            }
        };

        if (eventDate == null) {

            this.eventDate = new DatePicker();
            startHourComboBox = new ComboBox<>();
            startHourComboBox.setConverter(converter);
            startMinuteComboBox = new ComboBox<>();
            startMinuteComboBox.setConverter(converter);
            endHourComboBox = new ComboBox<>();
            endHourComboBox.setConverter(converter);
            endMinuteComboBox = new ComboBox<>();
            endMinuteComboBox.setConverter(converter);
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Choose event date");
        alert.setHeaderText("Please select a date:");

        if (eventDate == null)
            this.eventDate.setValue(LocalDate.now());
        else
            this.eventDate.setValue(this.eventDate.getValue());
        this.eventDate.setDayCellFactory(picker -> new DateCell() {

            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #c03d3d;");
                }
            }
        });

        IntStream.rangeClosed(9, 19).forEach(i -> startHourComboBox.getItems().add(i));

        if (eventDate == null)
            startHourComboBox.getSelectionModel().select(0);
        else
            startHourComboBox.getSelectionModel().select(startHourComboBox.getValue());

        IntStream.iterate(0, i -> i + 5).limit(12).forEach(i -> startMinuteComboBox.getItems().add(i));

        if (eventDate == null)
            startMinuteComboBox.getSelectionModel().select(0);
        else
            startMinuteComboBox.getSelectionModel().select(startMinuteComboBox.getValue());

        IntStream.rangeClosed(startHourComboBox.getValue(), 19).forEach(i -> endHourComboBox.getItems().add(i));

        if (eventDate == null)
            endHourComboBox.getSelectionModel().select(0);
        else
            endHourComboBox.getSelectionModel().select(endHourComboBox.getValue());

        IntStream.iterate(startMinuteComboBox.getValue(), i -> i + 5)
                .limit(12 - startMinuteComboBox.getValue() / 5).forEach(i -> endMinuteComboBox.getItems().add(i));

        if (eventDate == null)
            endMinuteComboBox.getSelectionModel().select(0);
        else
            endMinuteComboBox.getSelectionModel().select(endMinuteComboBox.getValue());

        startHourComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            endHourComboBox.getItems().clear();
            IntStream.rangeClosed(newValue, 19).forEach(i -> endHourComboBox.getItems().add(i));
            endHourComboBox.getSelectionModel().select(0);
        });

        startMinuteComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            updateEndMinutes(newValue);
        });

        endHourComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (endHourComboBox.getValue() != null) {
                updateEndMinutes(startMinuteComboBox.getValue());
            }
        });


        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));

        gridPane.add(new Label("Date:"), 0, 0);
        gridPane.add(this.eventDate, 1, 0);

        gridPane.add(new Label("Start hour:"), 0, 1);
        gridPane.add(startHourComboBox, 1, 1);

        gridPane.add(new Label("Start minute:"), 0, 2);
        gridPane.add(startMinuteComboBox, 1, 2);

        gridPane.add(new Label("End hour:"), 0, 3);
        gridPane.add(endHourComboBox, 1, 3);

        gridPane.add(new Label("End minute:"), 0, 4);
        gridPane.add(endMinuteComboBox, 1, 4);

        alert.getDialogPane().setContent(gridPane);

        alert.showAndWait().ifPresent(buttonType -> {

            if (buttonType == ButtonType.OK) {

                LocalDate selectedDate = this.eventDate.getValue();
                int selectedStartHour = startHourComboBox.getValue();
                int selectedStartMinute = startMinuteComboBox.getValue();
                eventStartDateTime = LocalDateTime.of(selectedDate, LocalTime.of(selectedStartHour, selectedStartMinute));
                int selectedEndHour = endHourComboBox.getValue();
                int selectedEndMinute = endMinuteComboBox.getValue();
                eventEndDateTime = LocalDateTime.of(selectedDate, LocalTime.of(selectedEndHour, selectedEndMinute));

                chooseEventRoom();
            }
        });
    }

    private void updateEndMinutes(int newStartMinute) {

        endMinuteComboBox.getItems().clear();

        int startHour = startHourComboBox.getValue();
        int endHour = endHourComboBox.getValue();

        if (endHour == startHour) {
            IntStream.iterate(newStartMinute, i -> i + 5)
                    .limit((60 - newStartMinute) / 5)
                    .forEach(endMinuteComboBox.getItems()::add);
        } else {
            IntStream.iterate(0, i -> i + 5)
                    .limit(12)
                    .forEach(endMinuteComboBox.getItems()::add);
        }

        endMinuteComboBox.getSelectionModel().select(0);
    }

    private void chooseEventRoom() {

        LocalDate eventDate = eventStartDateTime.toLocalDate();
        LocalTime eventStartTime = eventStartDateTime.toLocalTime();
        LocalTime eventEndTime = eventEndDateTime.toLocalTime();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType backButton = new ButtonType("Back", ButtonBar.ButtonData.BACK_PREVIOUS);
        alert.setTitle("Choose event room");
        alert.getButtonTypes().add(backButton);

        ToggleGroup toggleGroup = new ToggleGroup();

        VBox vBox = new VBox();

        List<Room> availableRooms = Room.getRooms().stream()
                .filter(room -> {
                    return Event.getEvents().stream()
                            .filter(event -> event instanceof Classes)
                            .noneMatch(classes -> {
                                Classes classesEvent = (Classes) classes;
                                LocalDateTime classesStart = classesEvent.getStartDateTime();
                                LocalDateTime classesEnd = classesEvent.getEndDateTime();

                                boolean sameDate = eventDate.isEqual(classesStart.toLocalDate());
                                boolean noOverlap = !(eventEndDateTime.isBefore(classesStart) || eventStartDateTime.isAfter(classesEnd));

                                return sameDate && noOverlap;
                            });
                }).toList();

        AtomicBoolean firstRadioButton = new AtomicBoolean(true);

        availableRooms.forEach(room -> {

            RadioButton radioButton = new RadioButton("Room " + room.getNumber());
            radioButton.setToggleGroup(toggleGroup);
            vBox.getChildren().add(radioButton);

            if (firstRadioButton.get()) {
                radioButton.setSelected(true);
                firstRadioButton.set(false);
            }
        });

        alert.setHeaderText(
                "Please select room available at " +
                eventDate + " between " + eventStartTime +
                " and " + eventEndTime + " (" +
                availableRooms.size() + (availableRooms.size() == 1 ? " room available)" : " rooms available)"));
        alert.getDialogPane().setContent(vBox);

        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(availableRooms.isEmpty());

        alert.showAndWait().ifPresent(buttonType -> {

            if (buttonType == ButtonType.OK) {
                selectedRoomNumber = Integer.valueOf(((RadioButton) (toggleGroup.getSelectedToggle())).getText().split(" ")[1]);
                setEventTitleAndDescription();
            } else if (buttonType == backButton) {
                chooseEventDate(this.eventDate);
            }
        });
    }

    public void setEventTitleAndDescription() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType backButton = new ButtonType("Back", ButtonBar.ButtonData.BACK_PREVIOUS);
        alert.setTitle("Set event title and description");
        alert.setHeaderText("Please enter title and description of event");
        alert.getButtonTypes().add(backButton);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));

        TextField titleTextField = new TextField();
        gridPane.add(new Label("Title:"), 0, 0);
        gridPane.add(titleTextField, 1, 0);

        TextArea descriptionTextArea = new TextArea();
        gridPane.add(new Label("Description:"), 0, 1);
        gridPane.add(descriptionTextArea, 1, 1);

        alert.getDialogPane().setContent(gridPane);

        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);
        okButton.setText("Add");

        ChangeListener<String> textChangeListener = (observable, oldValue, newValue) -> {

            boolean disableButton = titleTextField.getText().isBlank() || descriptionTextArea.getText().isBlank();
            okButton.setDisable(disableButton);
        };

        titleTextField.textProperty().addListener(textChangeListener);
        descriptionTextArea.textProperty().addListener(textChangeListener);

        alert.showAndWait().ifPresent(buttonType -> {

            if (buttonType == ButtonType.OK) {
                eventTitle = titleTextField.getText();
                eventDescription = descriptionTextArea.getText();
                addEvent();
            } else if (buttonType == backButton) {
                chooseEventRoom();
            }
        });
    }

    public void addEvent() {

        if (user instanceof Coach specificUser) {
            Classes classes = new Classes(eventTitle, eventDescription, eventStartDateTime, eventEndDateTime, specificUser, Room.findByRoomNumber(selectedRoomNumber));
            classes.setCoach(specificUser);
            Classes.addClasses(classes);
        } else if (user instanceof Employee specificUser) {
            Classes classes = new Classes(eventTitle, eventDescription, eventStartDateTime, eventEndDateTime, specificUser, Room.findByRoomNumber(selectedRoomNumber));
            Classes.addClasses(classes);
        } else {
            System.out.println("Unsupported user type.");
            return;
        }

        Event.serializeEvents();
        Room.serializeRooms();
    }

    private void navigateToPreviousWeek() {

        currentDisplayedWeek = currentDisplayedWeek.minusWeeks(1);
        updateCalendar();
    }

    private void navigateToNextWeek() {

        currentDisplayedWeek = currentDisplayedWeek.plusWeeks(1);
        updateCalendar();
    }

    private void loadEvents() {
        List<Event> loadedEvents = Event.getEvents();

        loadedEvents.forEach(event -> {
            CustomAppointment customAppointment = new CustomAppointment(event);
            agenda.appointments().add(customAppointment);
        });

        agenda.selectedAppointments().addListener((ListChangeListener<Agenda.Appointment>)(change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    List<Agenda.Appointment> selectedAppointments = agenda.selectedAppointments();

                    if (!selectedAppointments.isEmpty()) {
                        Agenda.Appointment focusedAppointment = selectedAppointments.getFirst();
                        if (focusedAppointment instanceof CustomAppointment customAppointment &&
                                customAppointment.getEvent() instanceof Classes classes) {

                            String coachFullName = "";

                            if (classes.getCoach() == null) {
                                coachFullName = "-";
                            } else {
                                String coachFirstName = classes.getCoach().getFirstName();
                                String coachLastName = classes.getCoach().getLastName();
                                coachFullName = (coachFirstName != null ? coachFirstName : "") + " " +
                                        (coachLastName != null ? coachLastName : "");
                            }

                            LocalDateTime classesStartDateTime = classes.getStartDateTime();
                            LocalDateTime classesEndDateTime = classes.getEndDateTime();
                            Integer roomNumber = classes.getRoom().getNumber();
                            Integer roomCapacity = classes.getRoom().getCapacity();
                            String classesName = classes.getName();
                            String classesDescription = classes.getDescription();
                            List<ClubMember> participants = classes.getParticipants();
                            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle(classesName + " details");
                            alert.setHeaderText(classesDescription);

                            GridPane gridPane = new GridPane();
                            gridPane.setHgap(10);
                            gridPane.setVgap(10);

                            Label trainerLabel = new Label("Coach:");
                            Label trainerValue = new Label(coachFullName);
                            gridPane.addRow(0, trainerLabel, trainerValue);

                            Label startLabel = new Label("Start Date/Time:");
                            Label startValue = new Label(classesStartDateTime.format(dateTimeFormatter));
                            gridPane.addRow(1, startLabel, startValue);

                            Label endLabel = new Label("End Date/Time:");
                            Label endValue = new Label(classesEndDateTime.format(dateTimeFormatter));
                            gridPane.addRow(2, endLabel, endValue);

                            Label roomLabel = new Label("Room:");
                            Label roomValue = new Label(roomNumber.toString());
                            gridPane.addRow(3, roomLabel, roomValue);

                            Label freePlacesLabel = new Label("Free places:");
                            Label freePlacesValue = new Label(Integer.toString(roomCapacity - participants.size()));
                            gridPane.addRow(4, freePlacesLabel, freePlacesValue);

                            ButtonType signUpButton = new ButtonType("Sign Up", ButtonBar.ButtonData.APPLY);
                            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                            alert.getButtonTypes().setAll(signUpButton, okButton);

                            alert.getDialogPane().setContent(gridPane);

                            alert.showAndWait().ifPresent(buttonType -> {

                                if (buttonType.getButtonData() == ButtonBar.ButtonData.APPLY) {
                                    classes.addParticipant((ClubMember) user);
                                    alert.setResult(ButtonType.OK);
                                } else if (buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                                    alert.setResult(ButtonType.OK);
                                }
                            });
                        } else {
                            System.out.println("Focused appointment is not an instance of CustomAppointment or CustomAppointment does not contain Classes.");
                        }
                    } else {
                        System.out.println("No appointment is currently focused.");
                    }
                } else if (change.wasRemoved()) {
                    System.out.println("Selected Appointments changed: " + agenda.selectedAppointments());
                }
            }
        });
    }
}
