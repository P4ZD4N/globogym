package com.p4zd4n.globogym.forms;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import lombok.Getter;

@Getter
public class RegistrationForm extends Form {

    private ToggleGroup group;

    private RadioButton clubMemberRadioButton;
    private RadioButton coachRadioButton;

    public RegistrationForm() {

        super();

        clubMemberRadioButton = new RadioButton("Club member");
        clubMemberRadioButton.getStyleClass().add("radio-button");
        coachRadioButton = new RadioButton("Coach (approval required!)");
        coachRadioButton.getStyleClass().add("radio-button");

        group = new ToggleGroup();

        clubMemberRadioButton.setToggleGroup(group);
        coachRadioButton.setToggleGroup(group);

        clubMemberRadioButton.setSelected(true);

        add(clubMemberRadioButton, 0, 0);
        add(coachRadioButton, 1, 0);
    }
}
