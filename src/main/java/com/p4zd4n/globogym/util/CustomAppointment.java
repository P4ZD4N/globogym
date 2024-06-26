package com.p4zd4n.globogym.util;

import com.p4zd4n.globogym.entity.Classes;
import com.p4zd4n.globogym.entity.Event;
import com.p4zd4n.globogym.enums.ClassesType;
import jfxtras.scene.control.agenda.Agenda;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomAppointment extends Agenda.AppointmentImplLocal {

    private Event event;

    public CustomAppointment(Event event) {
        super();

        setStartLocalDateTime(event.getStartDateTime());
        setEndLocalDateTime(event.getEndDateTime());
        setSummary(event.getName());
        setDescription(event.getName());

        this.event = event;
    }

    public void setColor(Event event) {
        Agenda.AppointmentGroup group = new Agenda.AppointmentGroupImpl();

        group.setDescription(event.getDescription());

        if (!(event instanceof Classes classes)) {
            group.setStyleClass("event");
        } else {
            switch (classes.getClassesType()) {
                case CARDIO -> group.setStyleClass("cardio");
                case STRENGTH -> group.setStyleClass("strength");
                case ENDURANCE -> group.setStyleClass("endurance");
                case WEIGHT_LOSS -> group.setStyleClass("weight-loss");
                case REHABILITATION -> group.setStyleClass("rehabilitation");
                case WOMEN_TRAINING -> group.setStyleClass("women-training");
            }
        }

        setAppointmentGroup(group);
    }
}
