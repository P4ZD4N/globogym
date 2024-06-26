package com.p4zd4n.globogym.entity;

import com.p4zd4n.globogym.enums.ClassesType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Event implements Serializable {

    @Serial
    private static final long serialVersionUID = -6112017548826377065L;
    private static List<Event> events = new ArrayList<>();
    private static Long counter = 1L;

    private Long id;

    private String name;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public Event(String name, String description, LocalDateTime startDateTime, LocalDateTime endDateTime) {

        this.id = counter++;
        this.name = name;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;

        events.add(this);
    }

    public static void serializeEvents() {

        try (FileOutputStream fileOut = new FileOutputStream("events.bin");
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            objectOut.writeObject(events);
            objectOut.writeObject(counter);

            System.out.println("Events successfully saved to events.bin");
        } catch (IOException e) {
            System.err.println("Error with writing events to events.bin");
            e.printStackTrace();
        }
    }

    public static void deserializeEvents() {

        try (FileInputStream fileIn = new FileInputStream("events.bin");
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

            List<Event> events = (List<Event>) objectIn.readObject();
            Long counter = (Long) objectIn.readObject();

            Event.setEvents(events);
            Event.setCounter(counter);

            System.out.println("Events successfully read from events.bin");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error with reading events from events.bin");
            e.printStackTrace();
        }
    }

    public static List<Event> getEvents() {
        return events;
    }

    public static void setEvents(List<Event> events) {
        Event.events = events;
    }

    public static Long getCounter() {
        return counter;
    }

    public static void setCounter(Long counter) {
        Event.counter = counter;
    }
}
