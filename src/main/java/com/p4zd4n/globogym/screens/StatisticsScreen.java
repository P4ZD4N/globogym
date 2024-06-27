package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entities.*;
import com.p4zd4n.globogym.enums.MembershipCardStatus;
import com.p4zd4n.globogym.panes.LeftPane;
import com.p4zd4n.globogym.panes.TopPane;
import com.p4zd4n.globogym.utils.BoldDescriptorLabel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.time.LocalDate;
import java.util.List;

public class StatisticsScreen {

    private Main main;
    private User user;
    private BorderPane borderPane;
    private VBox centerPane;

    public StatisticsScreen(Main main, User user) {

        this.main = main;
        this.user = user;
    }

    public Pane getView() {

        centerPane = new VBox();
        centerPane.setAlignment(Pos.CENTER);
        centerPane.setPadding(new Insets(10, 10, 10, 10));
        centerPane.setSpacing(20);

        if (user instanceof ClubMember clubMember && !(user instanceof Coach)) {
            initStatisticsForClubMember(clubMember);
        } else if (user instanceof Coach coach) {
            initStatisticsForCoach(coach);
        } else if (user instanceof Employee employee && !(user instanceof Manager)) {
            initStatisticsForEmployee(employee);
        } else if (user instanceof Manager manager) {
            initStatisticsForManager(manager);
        }

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.setTop(new TopPane(main, user));
        borderPane.setCenter(centerPane);
        borderPane.setLeft(new LeftPane(main, user));

        return borderPane;
    }

    private void initStatisticsForClubMember(ClubMember clubMember) {

        Label numberOfClassesParticipatedIn = new BoldDescriptorLabel(
                "Number of classes participated in: ", String.valueOf(clubMember.getClassesParticipatedIn().size())
        );

        centerPane.getChildren().addAll(numberOfClassesParticipatedIn);
    }

    private void initStatisticsForCoach(Coach coach) {

        Label numberOfClassesCreated = new BoldDescriptorLabel(
                "Number of classes created: ", String.valueOf(coach.getClassesCreatedByCoach().size())
        );

        Label numberOfClassesParticipatedIn = new BoldDescriptorLabel(
                "Number of classes participated in: ", String.valueOf(coach.getClassesParticipatedIn().size())
        );

        centerPane.getChildren().addAll(numberOfClassesCreated, numberOfClassesParticipatedIn);
    }

    private void initStatisticsForEmployee(Employee employee) {

        Label numberOfEventsCreated = new BoldDescriptorLabel(
                "Number of events created: ", String.valueOf(employee.getEventsCreatedByEmployee().size())
        );

        centerPane.getChildren().addAll(numberOfEventsCreated);
    }

    private void initStatisticsForManager(Manager manager) {

        List<Classes> classesToday = Event.getEvents()
                .stream()
                .filter(event -> event instanceof Classes classes && classes.getStartDateTime().toLocalDate().isEqual(LocalDate.now()))
                .map(event -> (Classes) event)
                .toList();

        int participantsToday = classesToday.stream()
                .mapToInt(classes -> classes.getParticipants().size())
                .sum();

        int allPlacesToday = classesToday.stream()
                .mapToInt(classes -> classes.getRoom().getCapacity())
                .sum();


        Label numberOfEventsCreated = new BoldDescriptorLabel(
                "Number of events created: ", String.valueOf(manager.getEventsCreatedByEmployee().size())
        );

        Label participantsStatsToday = new BoldDescriptorLabel(
                "Participants today (Signed Up / All places): ", participantsToday + " / " + allPlacesToday
        );

        int allClubMembers = (int) User.getUsers()
                .stream()
                .filter(user -> user instanceof ClubMember)
                .count();

        int clubMembersWithoutCard = (int) User.getUsers()
                .stream()
                .filter(user -> user instanceof ClubMember clubMember && clubMember.getMembershipCard() == null)
                .count();

        int clubMembersWithActiveCard = (int) User.getUsers()
                .stream()
                .filter(user -> user instanceof ClubMember clubMember &&
                        clubMember.getMembershipCard() != null &&
                        clubMember.getMembershipCard().getMembershipCardStatus().equals(MembershipCardStatus.ACTIVE)
                )
                .count();

        int clubMembersWithExpiredCard = (int) User.getUsers()
                .stream()
                .filter(user -> user instanceof ClubMember clubMember &&
                        clubMember.getMembershipCard() != null &&
                        clubMember.getMembershipCard().getMembershipCardStatus().equals(MembershipCardStatus.EXPIRED)
                )
                .count();

        Label membershipCards = new BoldDescriptorLabel(
                "All club members / No card / Active / Expired: ",
                allClubMembers + " / " + clubMembersWithoutCard + " / " + clubMembersWithActiveCard + " / " + clubMembersWithExpiredCard
        );

        centerPane.getChildren().addAll(numberOfEventsCreated, participantsStatsToday, membershipCards);
    }
}
