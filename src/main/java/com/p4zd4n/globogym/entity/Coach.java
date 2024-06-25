package com.p4zd4n.globogym.entity;

import com.p4zd4n.globogym.enums.ClassesType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Coach extends ClubMember {

    @Serial
    private static final long serialVersionUID = -5076345663056590411L;

    private boolean isActive = false;
    private List<ClassesType> specializations = new ArrayList<>();

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

    public void addSpecialization(ClassesType specialization) {

        specializations.add(specialization);
        User.serializeUsers();
    }

    public void removeSpecialization(ClassesType specialization) {

        specializations.remove(specialization);
        User.serializeUsers();
    }
}
