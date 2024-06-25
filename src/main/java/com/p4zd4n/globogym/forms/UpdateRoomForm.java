package com.p4zd4n.globogym.forms;

import com.p4zd4n.globogym.entity.Room;
import lombok.Getter;

@Getter
public class UpdateRoomForm extends Form {

    private Room room;

    public UpdateRoomForm(Room room) {

        super();

        this.room = room;

        add(roomNumberLabel, 0, 0);
        roomNumberField.setText(String.valueOf(room.getNumber()));
        add(roomNumberField, 1, 0);

        add(roomCapacityLabel, 0, 1);
        roomCapacityField.setText(String.valueOf(room.getCapacity()));
        add(roomCapacityField, 1, 1);
    }
}
