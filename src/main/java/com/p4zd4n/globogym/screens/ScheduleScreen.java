package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.enums.ClassesType;
import com.p4zd4n.globogym.enums.MembershipCardStatus;
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
    private ClassesType classesType;
    private Coach coach;

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
        buttonAdd.setOnAction(e -> chooseEventCoachAndType());

        nextWeekButton = new Button(">");
        nextWeekButton.setOnAction(e -> navigateToNextWeek());

        navigationBox = new HBox(10, previousWeekButton);

        if (user instanceof Employee) {
            navigationBox.getChildren().add(buttonAdd);
        } else if (
                user instanceof Coach coach &&
                coach.getMembershipCard() != null &&
                coach.getMembershipCard().getMembershipCardStatus().equals(MembershipCardStatus.ACTIVE)
        ) {
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

    private void chooseEventCoachAndType() {

        if (user instanceof Coach coach) {
            showAlertForCoach(coach);
        } else if (user instanceof Employee) {
            showAlertForEmployee();
        }


    }

    private void showAlertForCoach(Coach coach) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Choose event coach and type");
        alert.setHeaderText("Please select a coach and event type:");

        Label coachLabel = new Label("ID: " + coach.getId() + ", " + coach.getFirstName() + " " + coach.getLastName());
        ComboBox<String> claseesTypeComboBox = new ComboBox<>();

        claseesTypeComboBox.getItems().addAll(
                coach.getSpecializations()
                        .stream()
                        .map(ClassesType::getType)
                        .toList()
        );

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));

        gridPane.add(new Label("Coach:"), 0, 0);
        gridPane.add(coachLabel, 1, 0);

        gridPane.add(new Label("Classes Type:"), 0, 1);
        gridPane.add(claseesTypeComboBox, 1, 1);

        alert.getDialogPane().setContent(gridPane);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(okButton, cancelButton);

        alert.getDialogPane().lookupButton(okButton).setDisable(true);

        ChangeListener<String> changeListener = (observable, oldValue, newValue) -> {

            boolean disableButton = claseesTypeComboBox.getValue() == null;
            alert.getDialogPane().lookupButton(okButton).setDisable(disableButton);
        };

        claseesTypeComboBox.valueProperty().addListener(changeListener);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == okButton) {

                this.coach = coach;
                classesType = ClassesType.getByString(claseesTypeComboBox.getValue());

                chooseEventDate(eventDate);
            }
        });
    }

    private void showAlertForEmployee() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Choose event coach and type");
        alert.setHeaderText("Please select a coach and event type:");

        ComboBox<String> coachComboBox = new ComboBox<>();
        ComboBox<String> classesTypeComboBox = new ComboBox<>();

        coachComboBox.getItems().addAll(User.getUsers().stream()
                .filter(u -> u instanceof Coach)
                .map(user -> "ID: " +
                        user.getId() + ", " +
                        user.getFirstName() + " " +
                        user.getLastName()
                )
                .toList()
        );

        coachComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            classesTypeComboBox.getItems().clear();

            String[] stringElements = newValue.split(" ");
            Long coachId = Long.parseLong(stringElements[1].substring(0, stringElements[1].length() - 1));
            Coach foundCoach = (Coach) User.findById(coachId);

            classesTypeComboBox.getItems().addAll(
                    foundCoach.getSpecializations()
                            .stream()
                            .map(ClassesType::getType)
                            .toList()
            );
        });

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));

        gridPane.add(new Label("Coach:"), 0, 0);
        gridPane.add(coachComboBox, 1, 0);

        gridPane.add(new Label("Classes Type:"), 0, 1);
        gridPane.add(classesTypeComboBox, 1, 1);

        alert.getDialogPane().setContent(gridPane);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(okButton, cancelButton);

        alert.getDialogPane().lookupButton(okButton).setDisable(true);

        ChangeListener<String> changeListener = (observable, oldValue, newValue) -> {

            boolean disableButton = coachComboBox.getValue() == null || classesTypeComboBox.getValue() == null;
            alert.getDialogPane().lookupButton(okButton).setDisable(disableButton);
        };

        coachComboBox.valueProperty().addListener(changeListener);
        classesTypeComboBox.valueProperty().addListener(changeListener);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == okButton) {

                String selectedCoach = coachComboBox.getValue();
                String[] stringElements = selectedCoach.split(" ");
                Long coachId = Long.parseLong(stringElements[1].substring(0, stringElements[1].length() - 1));
                coach = (Coach) User.findById(coachId);

                classesType = ClassesType.getByString(classesTypeComboBox.getValue());

                chooseEventDate(eventDate);
            }
        });
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
        ButtonType backButton = new ButtonType("Back", ButtonBar.ButtonData.BACK_PREVIOUS);
        alert.getButtonTypes().add(backButton);

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
            } else if (buttonType == backButton) {
                chooseEventCoachAndType();
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

                                return room.getNumber().equals(classesEvent.getRoom().getNumber()) && sameDate && noOverlap;
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

        Classes classes = new Classes(classesType, eventTitle, eventDescription, eventStartDateTime, eventEndDateTime, coach, Room.findByRoomNumber(selectedRoomNumber));
        Classes.addClasses(classes);

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

                            String classesType = classes.getClassesType().getType();
                            String coachFullName = classes.getCoach().getFirstName() + " " + classes.getCoach().getLastName();
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

                            Label classesTypeLabel = new Label("Classes type: ");
                            Label classesTypeValue = new Label(classesType);
                            gridPane.addRow(0, classesTypeLabel, classesTypeValue);

                            Label coachLabel = new Label("Coach:");
                            if (!(user instanceof Employee) || LocalDateTime.now().isAfter(classesStartDateTime)) {
                                Label coachValue = new Label(coachFullName);
                                gridPane.addRow(0, coachLabel, coachValue);
                            } else {
                                ComboBox<String> coachValue = new ComboBox<>();

                                coachValue.getItems().addAll(User.getUsers()
                                        .stream()
                                        .filter(u -> u instanceof Coach)
                                        .filter(c -> ((Coach) c).getSpecializations().contains(classes.getClassesType()))
                                        .map(user -> "ID: " +
                                            user.getId() + ", " +
                                            user.getFirstName() + " " +
                                            user.getLastName()
                                        )
                                        .toList()
                                );


                                coachValue.setValue(coachFullName);
                                coachValue.setOnAction(event -> {
                                    String selectedCoach = coachValue.getValue();
                                    String[] stringElements = selectedCoach.split(" ");
                                    Long coachId = Long.parseLong(stringElements[1].substring(0, stringElements[1].length() - 1));
                                    classes.setCoach((Coach) User.findById(coachId));
                                    Event.serializeEvents();
                                });
                                gridPane.addRow(1, coachLabel, coachValue);
                            }

                            Label startLabel = new Label("Start Date/Time:");
                            Label startValue = new Label(classesStartDateTime.format(dateTimeFormatter));
                            gridPane.addRow(2, startLabel, startValue);

                            Label endLabel = new Label("End Date/Time:");
                            Label endValue = new Label(classesEndDateTime.format(dateTimeFormatter));
                            gridPane.addRow(3, endLabel, endValue);

                            Label roomLabel = new Label("Room:");
                            Label roomValue = new Label(roomNumber.toString());
                            gridPane.addRow(4, roomLabel, roomValue);

                            Label freePlacesLabel = new Label("Free places:");
                            Label freePlacesValue = new Label(Integer.toString(roomCapacity - participants.size()));
                            gridPane.addRow(5, freePlacesLabel, freePlacesValue);

                            ButtonType signUpButton = new ButtonType("Sign Up", ButtonBar.ButtonData.APPLY);
                            ButtonType signOutButton = new ButtonType("Sign Out", ButtonBar.ButtonData.BACK_PREVIOUS);
                            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                            alert.getButtonTypes().setAll(signUpButton, signOutButton, okButton);

                            alert.getDialogPane().setContent(gridPane);

                            alert.getDialogPane().lookupButton(signUpButton).setDisable(
                                    !(user instanceof ClubMember) ||
                                    freePlacesValue.getText().equals("0") ||
                                    LocalDateTime.now().isAfter(classesStartDateTime) ||
                                    user instanceof ClubMember clubMember &&
                                    clubMember.getMembershipCard() == null ||
                                    user instanceof ClubMember clubMember1 &&
                                    clubMember1.getMembershipCard().getMembershipCardStatus().equals(MembershipCardStatus.EXPIRED) ||
                                    classes.getParticipants().contains(user)
                            );

                            alert.getDialogPane().lookupButton(signOutButton).setDisable(
                                    !(user instanceof ClubMember) ||
                                    LocalDateTime.now().isAfter(classesStartDateTime) ||
                                    !classes.getParticipants().contains(user)
                            );

                            alert.showAndWait().ifPresent(buttonType -> {

                                if (buttonType.getButtonData() == ButtonBar.ButtonData.APPLY) {
                                    classes.addParticipant((ClubMember) user);
                                    alert.setResult(ButtonType.OK);
                                } else if (buttonType.getButtonData() == ButtonBar.ButtonData.BACK_PREVIOUS) {
                                    classes.removeParticipant((ClubMember) user);
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
