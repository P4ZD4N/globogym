package com.p4zd4n.globogym.forms;

public class AddOtherEventForm extends Form {

    public AddOtherEventForm() {

        super();

        add(eventNameLabel, 0, 0);
        add(eventNameTextField, 1, 0);
        add(eventDescriptionLabel, 0, 1);
        add(eventDescriptionTextField, 1, 1);
        add(eventStartDateLabel, 0, 2);
        add(eventStartDateField, 1, 2);
        add(eventEndDateLabel, 0, 3);
        add(eventEndDateField, 1, 3);
        add(eventStartTimeLabel, 0, 4);
        add(eventStartTimePicker, 1, 4);
        add(eventEndTimeLabel, 0, 5);
        add(eventEndTimePicker, 1, 5);
    }
}
