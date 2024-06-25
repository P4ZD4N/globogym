package com.p4zd4n.globogym;

import com.p4zd4n.globogym.entity.*;
import com.p4zd4n.globogym.enums.MembershipCardStatus;
import com.p4zd4n.globogym.screens.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class Main extends Application {

    private Stage primaryStage;
    private Scene mainScene;
    private Scene loginScene;
    private Scene registrationScene;
    private Scene homeScene;
    private Scene membersManagementScene;
    private Scene userAccountScene;
    private Scene scheduleScene;
    private Scene paymentsScene;
    private Scene membershipCardScene;
    private Scene findUserScene;
    private Scene addUserScene;
    private Scene updateUserScene;
    private Scene roomsManagementScene;
    private Scene findRoomScene;
    private Scene addRoomScene;
    private Scene updateRoomScene;

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

    public void showHomeScreen(User user) {

        HomeScreen homeScreen = new HomeScreen(this, user);

        homeScene = new Scene(homeScreen.getView(), 800, 800);
        homeScene.getStylesheets().add(getClass().getResource("/css/screen-home.css").toExternalForm());

        primaryStage.setScene(homeScene);
    }

    public void showMembersManagementScreen(Employee employee, List<User> clubMembers) {

        MembersManagementScreen membersManagementScreen = new MembersManagementScreen(this, employee, clubMembers);

        membersManagementScene = new Scene(membersManagementScreen.getView(), 800, 800);
        membersManagementScene.getStylesheets().add(getClass().getResource("/css/screen-members-management.css").toExternalForm());

        primaryStage.setScene(membersManagementScene);
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

    public void showFindUserScreen(Employee employee) {

        FindUserScreen findUserScreen = new FindUserScreen(this, employee);

        findUserScene = new Scene(findUserScreen.getView(), 800, 800);
        findUserScene.getStylesheets().add(getClass().getResource("/css/screen-find-user.css").toExternalForm());

        primaryStage.setScene(findUserScene);
    }

    public void showAddUserScreen(Employee employee) {

        AddUserScreen addUserScreen = new AddUserScreen(this, employee);

        addUserScene = new Scene(addUserScreen.getView(), 800, 800);
        addUserScene.getStylesheets().add(getClass().getResource("/css/screen-add-user.css").toExternalForm());

        primaryStage.setScene(addUserScene);
    }

    public void showUpdateUserScreen(Employee employee, User user) {

        UpdateUserScreen updateUserScreen = new UpdateUserScreen(this, employee, user);

        updateUserScene = new Scene(updateUserScreen.getView(), 800, 800);
        updateUserScene.getStylesheets().add(getClass().getResource("/css/screen-update-user.css").toExternalForm());

        primaryStage.setScene(updateUserScene);
    }

    public void showRoomsManagementScreen(Manager manager, List<Room> rooms) {

        RoomsManagementScreen roomsManagementScreen = new RoomsManagementScreen(this, manager, rooms);

        roomsManagementScene = new Scene(roomsManagementScreen.getView(), 800, 800);
        roomsManagementScene.getStylesheets().add(getClass().getResource("/css/screen-rooms-management.css").toExternalForm());

        primaryStage.setScene(roomsManagementScene);
    }

    public void showFindRoomScreen(Manager manager) {

        FindRoomScreen findRoomsScreen = new FindRoomScreen(this, manager);

        findRoomScene = new Scene(findRoomsScreen.getView(), 800, 800);
        findRoomScene.getStylesheets().add(getClass().getResource("/css/screen-find-room.css").toExternalForm());

        primaryStage.setScene(findRoomScene);
    }

    public void showAddRoomScreen(Manager manager) {

        AddRoomScreen addRoomScreen = new AddRoomScreen(this, manager);

        addRoomScene = new Scene(addRoomScreen.getView(), 800, 800);
        addRoomScene.getStylesheets().add(getClass().getResource("/css/screen-add-room.css").toExternalForm());

        primaryStage.setScene(addRoomScene);
    }

    public void showUpdateRoomScreen(Manager manager, Room room) {

        UpdateRoomScreen updateRoomScreen = new UpdateRoomScreen(this, manager, room);

        updateRoomScene = new Scene(updateRoomScreen.getView(), 800, 800);
        updateRoomScene.getStylesheets().add(getClass().getResource("/css/screen-update-room.css").toExternalForm());

        primaryStage.setScene(updateRoomScene);
    }

    public void exit() {

        System.exit(0);
    }
}