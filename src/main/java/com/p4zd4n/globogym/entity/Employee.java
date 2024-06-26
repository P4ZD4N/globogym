package com.p4zd4n.globogym.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Employee extends User {

    private Double salary;
    private List<Event> eventsCreatedByEmployee = new ArrayList<>();

    public Employee(
            String username,
            String email,
            String password,
            String firstName,
            String lastName,
            LocalDate birthDate,
            Double salary
    ) {

        super(username, email, password, firstName, lastName, birthDate);
        this.salary = salary;
    }

    public void addEventCreatedByEmployee(Event event) {

        eventsCreatedByEmployee.add(event);
    }
}
