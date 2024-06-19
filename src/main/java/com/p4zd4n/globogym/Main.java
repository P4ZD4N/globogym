package com.p4zd4n.globogym;

import com.p4zd4n.globogym.entity.*;
import com.p4zd4n.globogym.enums.MembershipCardStatus;
import com.p4zd4n.globogym.screens.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Main extends Application {

    private Stage primaryStage;
    private Scene mainScene;
    private Scene loginScene;
    private Scene registrationScene;
    private Scene clubMemberDashboardScene;
    private Scene userAccountScene;
    private Scene scheduleScene;
    private Scene paymentsScene;
    private Scene membershipCardScene;

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;

        MainScreen mainScreen = new MainScreen(this);
        LoginScreen loginScreen = new LoginScreen(this);
        RegistrationScreen registrationScreen = new RegistrationScreen(this);

        mainScene = new Scene(mainScreen.getView(), 800, 800);
        mainScene.getStylesheets().add(getClass().getResource("/css/screen-main.css").toExternalForm());

        loginScene = new Scene(loginScreen.getView(), 800, 800);
        loginScene.getStylesheets().add(getClass().getResource("/css/screen-login.css").toExternalForm());

        registrationScene = new Scene(registrationScreen.getView(), 800, 800);
        registrationScene.getStylesheets().add(getClass().getResource("/css/screen-register.css").toExternalForm());

        primaryStage.setScene(mainScene);
        primaryStage.setTitle("GloboGym");
        primaryStage.show();
    }

    public static void main(String[] args) {

        Room room1 = new Room(1, 10);
        Room.serializeRooms();

        ClubMember testClubMember = new ClubMember(
                "clubm",
                "clubm@gmail.com",
                "!Test123",
                "Wiktor",
                "Chudy",
                LocalDate.of(2003, 8, 1));
        System.out.println("testClubMember registered successfully!");

        Coach testCoach = new Coach(
                "coach",
                "coach@gmail.com",
                "!Test123",
                "Wiktor",
                "Chudy",
                LocalDate.of(2003, 8, 1));
        testCoach.setActive(true);
        System.out.println("testCoach registered successfully!");

        Employee testEmployee = new Employee(
                "emp",
                "emp@gmail.com",
                "!Test123",
                "Wiktor",
                "Chudy",
                LocalDate.of(2003, 8, 1),
                4000.0);
        System.out.println("testEmployee registered successfully!");

        Manager manager = new Manager(
                "man",
                "man@gmail.com",
                "!Test123",
                "Wiktor",
                "Chudy",
                LocalDate.of(2003, 8, 1),
                4000.0);
        System.out.println("manager registered successfully!");

        User.serializeUsers();

        Event.deserializeEvents();
        Room.deserializeRooms();
        User.deserializeUsers();
        MembershipCard.deserializeMembershipCards();
        MembershipCard.getMembershipCards()
                    .stream()
                    .filter(membershipCard -> membershipCard.getExpirationDate().isBefore(LocalDate.now()))
                    .forEach(membershipCard -> membershipCard.setMembershipCardStatus(MembershipCardStatus.EXPIRED));
        launch();
    }

    public void showMainScreen() {

        primaryStage.setScene(mainScene);
    }

    public void showLoginScreen() {

        primaryStage.setScene(loginScene);
    }

    public void showRegistrationScreen() {

        primaryStage.setScene(registrationScene);
    }

    public void showClubMemberDashboardScreen(User user) {

        ClubMemberDashboardScreen clubMemberDashboardScreen = new ClubMemberDashboardScreen(this, user);

        clubMemberDashboardScene = new Scene(clubMemberDashboardScreen.getView(), 800, 800);
        clubMemberDashboardScene.getStylesheets().add(getClass().getResource("/css/screen-club-member-dashboard.css").toExternalForm());

        primaryStage.setScene(clubMemberDashboardScene);
    }

    public void showUserAccountScreen(User user) {

        UserAccountScreen userAccountScreen = new UserAccountScreen(this, user);

        userAccountScene = new Scene(userAccountScreen.getView(), 800, 800);
        userAccountScene.getStylesheets().add(getClass().getResource("/css/screen-user-account.css").toExternalForm());

        primaryStage.setScene(userAccountScene);
    }

    public void showScheduleScreen(User user) {

        ScheduleScreen scheduleScreen = new ScheduleScreen(this, user);

        scheduleScene = new Scene(scheduleScreen.getView(), 800, 800);
        scheduleScene.getStylesheets().add(getClass().getResource("/css/screen-schedule.css").toExternalForm());

        primaryStage.setScene(scheduleScene);
    }

    public void showPaymentsScreen(ClubMember clubMember) {

        PaymentsScreen paymentsScreen = new PaymentsScreen(this, clubMember);

        paymentsScene = new Scene(paymentsScreen.getView(), 800, 800);
        paymentsScene.getStylesheets().add(getClass().getResource("/css/screen-payments.css").toExternalForm());

        primaryStage.setScene(paymentsScene);
    }

    public void showMembershipCardScreen(ClubMember clubMember) {

        MembershipCardScreen membershipCardScreen = new MembershipCardScreen(this, clubMember);

        membershipCardScene = new Scene(membershipCardScreen.getView(), 800, 800);
        membershipCardScene.getStylesheets().add(getClass().getResource("/css/screen-membership-card.css").toExternalForm());

        primaryStage.setScene(membershipCardScene);
    }

    public void exit() {

        System.exit(0);
    }
}