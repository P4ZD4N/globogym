package com.p4zd4n.globogym.entity;

import java.time.LocalDate;

public class ClubMember extends User {

    public ClubMember(String username, String email, String password, String firstName, String lastName, LocalDate birthDate) {
        super(username, email, password, firstName, lastName, birthDate);
    }
}
