package com.p4zd4n.globogym.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@ToString
public class Room implements Serializable {

    @Serial
    private static final long serialVersionUID = -565156663281647612L;
    private static List<Room> rooms = new ArrayList<>();

    private Integer number;
    private Integer capacity;

    public Room(Integer number, Integer capacity) {

        if (!rooms.stream().filter(room -> room.getNumber().equals(number)).findFirst().isEmpty()) {
            System.out.println("You can't create two rooms with the same number!");
        }

        this.number = number;
        this.capacity = capacity;

        rooms.add(this);
    }

    public static Room findByRoomNumber(Integer roomNumber) {

        Optional<Room> foundRoom = rooms.stream()
                .filter(room -> room.getNumber().equals(roomNumber))
                .findFirst();

        return foundRoom.orElse(null);
    }

    public static void serializeRooms() {

        try (FileOutputStream fileOut = new FileOutputStream("rooms.bin");
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            objectOut.writeObject(rooms);

            System.out.println("Rooms successfully saved to rooms.bin");
        } catch (IOException e) {
            System.err.println("Error with writing rooms to rooms.bin");
        }
    }

    public static void deserializeRooms() {

        try (FileInputStream fileIn = new FileInputStream("rooms.bin");
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

            List<Room> rooms = (List<Room>) objectIn.readObject();

            Room.setRooms(rooms);

            System.out.println("Rooms successfully read from rooms.bin");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error with reading rooms from rooms.bin");
        }
    }

    public static List<Room> getRooms() {
        return rooms;
    }

    public static void setRooms(List<Room> rooms) {
        Room.rooms = rooms;
    }
}
