package com.p4zd4n.globogym.forms.find;

import com.p4zd4n.globogym.forms.Form;

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
        add(classesTypeLabel, 0, 4);
        add(classesTypeComboBox, 1, 4);
        add(coachLabel, 0, 5);
        add(coachComboBox, 1, 5);
        add(roomNumberLabel, 0, 6);
        add(roomComboBox, 1, 6);
        add(eventCheckbox, 0, 7);
        add(classesCheckbox, 1, 7);
    }
}
