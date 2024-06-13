package com.p4zd4n.globogym.entity;

import com.p4zd4n.globogym.enums.CoachSpecialization;

import java.io.Serial;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Coach extends ClubMember {

    @Serial
    private static final long serialVersionUID = -5076345663056590411L;

    private List<CoachSpecialization> specializations = new ArrayList<>();
    private Double balance = 0D;

    public Coach(
            String username,
            String email,
            String password,
            String firstName,
            String lastName,
            LocalDate birthDate
    ) {

        super(username, email, password, firstName, lastName, birthDate);
    }
}
