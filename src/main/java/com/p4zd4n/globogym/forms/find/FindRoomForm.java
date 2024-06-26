package com.p4zd4n.globogym.forms.find;

import com.p4zd4n.globogym.forms.Form;
import lombok.Getter;

@Getter
public class FindRoomForm extends Form {

    public FindRoomForm() {

        super();

        add(roomNumberLabel, 0, 0);
        add(roomNumberField, 1, 0);
        add(minRoomCapacityLabel, 0, 1);
        add(minRoomCapacityField, 1, 1);
        add(maxRoomCapacityLabel, 0, 2);
        add(maxRoomCapacityField, 1, 2);
    }
}
