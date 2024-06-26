package com.p4zd4n.globogym.entities;

import java.time.LocalDate;

public class Manager extends Employee {

    public Manager(String username, String email, String password, String firstName, String lastName, LocalDate birthDate, Double salary) {
        super(username, email, password, firstName, lastName, birthDate, salary);
    }
}
