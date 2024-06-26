package com.p4zd4n.globogym.forms.add;

import com.p4zd4n.globogym.forms.Form;

public class AddRoomForm extends Form {

    public AddRoomForm() {

        super();

        add(roomNumberLabel, 0, 0);
        add(roomNumberField, 1, 0);
        add(roomCapacityLabel, 0, 1);
        add(roomCapacityField, 1, 1);
    }
}
