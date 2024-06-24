package com.p4zd4n.globogym.util;

import com.p4zd4n.globogym.entity.Event;
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
}
