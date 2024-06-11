package com.p4zd4n.globogym.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ClubMember extends User {

    private static final long serialVersionUID = -4495183180571699662L;

    private Double balance = 0D;

    public ClubMember(String username, String email, String password, String firstName, String lastName, LocalDate birthDate) {
        super(username, email, password, firstName, lastName, birthDate);
    }
}
