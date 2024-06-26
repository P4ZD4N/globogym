package com.p4zd4n.globogym.panes;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.ClubMember;
import com.p4zd4n.globogym.entity.Employee;
import com.p4zd4n.globogym.entity.Manager;
import com.p4zd4n.globogym.entity.User;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class LeftPane extends VBox {

    private Button homeButton;
    private Button membersManagementButton;
    private Button eventsManagementButton;
    private Button roomsManagementButton;
    private Button paymentsButton;
    private Button membershipCardButton;
    private Button scheduleButton;
    private Button statisticsButton;

    public LeftPane(Main main, User user) {

        setAlignment(Pos.CENTER);
        setSpacing(10);

        homeButton = new Button("Home");
        homeButton.setOnAction(e -> main.showHomeScreen(user));

        scheduleButton = new Button("Schedule");
        scheduleButton.setOnAction(e -> main.showScheduleScreen(user));

        statisticsButton = new Button("Statistics");

        getChildren().add(homeButton);

        if (user instanceof Employee employee) {

            membersManagementButton = new Button("Members Management");
            membersManagementButton.setOnAction(e -> main.showMembersManagementScreen(employee, null));

            eventsManagementButton = new Button("Events Management");
            eventsManagementButton.setOnAction(e -> main.showEventsManagementScreen(employee, null));

            getChildren().addAll(membersManagementButton, eventsManagementButton);
        }

        if (user instanceof Manager manager) {

            roomsManagementButton = new Button("Rooms Management");
            roomsManagementButton.setOnAction(e -> main.showRoomsManagementScreen(manager, null));

            getChildren().add(roomsManagementButton);
        }

        if (user instanceof ClubMember clubMember) {

            paymentsButton = new Button("Payments");
            paymentsButton.setOnAction(e -> main.showPaymentsScreen(clubMember));

            membershipCardButton = new Button("Membership Card");
            membershipCardButton.setOnAction(e -> main.showMembershipCardScreen(clubMember));
            getChildren().addAll(paymentsButton, membershipCardButton);
        }

        getChildren().addAll(scheduleButton, statisticsButton);
    }
}
