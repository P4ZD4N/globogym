package com.p4zd4n.globogym.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Classes extends Event {

    @Serial
    private static final long serialVersionUID = -5047333787692779181L;
    private static List<Classes> allClasses = new ArrayList<>();

    private Coach coach;
    private Room room;
    private List<ClubMember> participants = new ArrayList<>();

    public Classes(String name, String description, LocalDateTime startDateTime, LocalDateTime endDateTime, Coach coach, Room room) {
        super(name, description, startDateTime, endDateTime);

        this.coach = coach;
        this.room = room;

        allClasses.add(this);
    }

    public void addParticipant(ClubMember clubMember) {

        participants.add(clubMember);
    }

    public static void addClasses(Classes classes) {

        allClasses.add(classes);
    }

    public static List<Classes> getAllClasses() {
        return allClasses;
    }

    public static void setAllClasses(List<Classes> allClasses) {
        Classes.allClasses = allClasses;
    }
}
