package com.p4zd4n.globogym.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Employee extends User {

    private Double salary;

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
}
