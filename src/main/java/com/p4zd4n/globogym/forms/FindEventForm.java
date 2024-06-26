package com.p4zd4n.globogym.forms;

public class FindEventForm extends Form {

    public FindEventForm() {

        super();

        add(idLabel, 0, 0);
        add(idTextField, 1, 0);
        add(eventNameLabel, 0, 1);
        add(eventNameTextField, 1, 1);
        minDateLabel.setText("Min. start date:");
        add(minDateLabel, 0, 2);
        add(minDateField, 1, 2);
        maxDateLabel.setText("Max start date:");
        add(maxDateLabel, 0, 3);
        add(maxDateField, 1, 3);
        add(coachLabel, 0, 4);
        add(coachComboBox, 1, 4);
        add(roomNumberLabel, 0, 5);
        add(roomComboBox, 1, 5);
        add(eventCheckbox, 0, 6);
        add(classesCheckbox, 1, 6);
    }
}
