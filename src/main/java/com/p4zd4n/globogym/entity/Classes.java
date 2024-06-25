package com.p4zd4n.globogym.entity;

import com.p4zd4n.globogym.enums.MembershipCardStatus;
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

    private User user;
    private Coach coach;
    private Room room;
    private List<ClubMember> participants = new ArrayList<>();

    public Classes(String name, String description, LocalDateTime startDateTime, LocalDateTime endDateTime, User user, Room room) {
        super(name, description, startDateTime, endDateTime);

        if (user instanceof Coach || user instanceof Employee) {

            this.user = user;
            this.room = room;

            allClasses.add(this);
        }
    }

    public void addParticipant(ClubMember clubMember) {

        if (!participants.contains(clubMember) &&
            clubMember.getMembershipCard() != null &&
            clubMember.getMembershipCard().getMembershipCardStatus().equals(MembershipCardStatus.ACTIVE) &&
            participants.size() < room.getCapacity()
        ) {
            participants.add(clubMember);
            Event.serializeEvents();
        } else {
            System.out.println("Can't add this participant to classes");
        }
    }

    public void removeParticipant(ClubMember clubMember) {

        if (participants.contains(clubMember)) {
            participants.remove(clubMember);
            Event.serializeEvents();
        } else {
            System.out.println("Can't remove this participant from classes");
        }
    }

    public static void addClasses(Classes classes) {

        allClasses.add(classes);

        Event.serializeEvents();
    }

    public static List<Classes> getAllClasses() {
        return allClasses;
    }

    public static void setAllClasses(List<Classes> allClasses) {
        Classes.allClasses = allClasses;
    }
}
