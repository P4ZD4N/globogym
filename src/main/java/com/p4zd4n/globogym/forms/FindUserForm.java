package com.p4zd4n.globogym.forms;

import javafx.scene.control.CheckBox;
import lombok.Getter;

@Getter
public class FindUserForm extends Form {

    private CheckBox clubMemberCheckBox;
    private CheckBox coachCheckbox;

    public FindUserForm() {

        super();

        clubMemberCheckBox = new CheckBox("Club member");
        clubMemberCheckBox.getStyleClass().add("checkbox");

        coachCheckbox = new CheckBox("Coach");
        coachCheckbox.getStyleClass().add("checkbox");

        add(clubMemberCheckBox, 0, 0);
        add(coachCheckbox, 1, 0);
    }
}
