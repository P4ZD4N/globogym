package com.p4zd4n.globogym.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = -5881023603198851913L;
    private static List<User> users = new ArrayList<>();
    private static Long counter = 1L;

    private Long id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    public User(String username, String email, String password, String firstName, String lastName, LocalDate birthDate) {

        this.id = counter;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;

        counter++;
    }

    public static List<User> getUsers() {
        return users;
    }

    public static void setUsers(List<User> users) {
        User.users = users;
    }

    public static void serializeUsers() {

        try (FileOutputStream fileOut = new FileOutputStream("users.bin");
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            objectOut.writeObject(users);

            System.out.println("Users successfully saved to users.bin");
        } catch (IOException e) {
            System.err.println("Error with writing users to users.bin");
            e.printStackTrace();
        }
    }

    public static void deserializeUsers() {

        try (FileInputStream fileIn = new FileInputStream("users.bin");
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

            List<User> users = (List<User>) objectIn.readObject();

            User.setUsers(users);

            System.out.println("Users successfully read from users.bin");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error with reading users from users.bin");
            e.printStackTrace();
        }
    }

    public static User findByUsername(String username) {

        Optional<User> foundUser = users.stream()
                                        .filter(user -> user.getUsername().equals(username))
                                        .findFirst();

        return foundUser.orElse(null);
    }

    public static User findByEmail(String email) {

        Optional<User> foundUser = users.stream()
                                        .filter(user -> user.getEmail().equals(email))
                                        .findFirst();

        return foundUser.orElse(null);
    }
}
