package com.p4zd4n.globogym;

import com.p4zd4n.globogym.entities.*;
import com.p4zd4n.globogym.enums.ClassesType;
import com.p4zd4n.globogym.enums.MembershipCardStatus;
import com.p4zd4n.globogym.screens.*;
import com.p4zd4n.globogym.screens.add.AddOtherEventScreen;
import com.p4zd4n.globogym.screens.add.AddRoomScreen;
import com.p4zd4n.globogym.screens.add.AddUserScreen;
import com.p4zd4n.globogym.screens.auth.LoginScreen;
import com.p4zd4n.globogym.screens.auth.RegistrationScreen;
import com.p4zd4n.globogym.screens.find.FindEventScreen;
import com.p4zd4n.globogym.screens.find.FindRoomScreen;
import com.p4zd4n.globogym.screens.find.FindUserScreen;
import com.p4zd4n.globogym.screens.management.ClassesParticipantsScreen;
import com.p4zd4n.globogym.screens.management.EventsManagementScreen;
import com.p4zd4n.globogym.screens.management.RoomsManagementScreen;
import com.p4zd4n.globogym.screens.management.UsersManagementScreen;
import com.p4zd4n.globogym.screens.update.UpdateRoomScreen;
import com.p4zd4n.globogym.screens.update.UpdateUserScreen;
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
    private Scene usersManagementScene;
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
    private Scene eventsManagementScene;
    private Scene addOtherEventScene;
    private Scene findEventScene;
    private Scene classesParticipantsScene;
    private Scene statisticsScene;

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;

        MainScreen mainScreen = new MainScreen(this);
        LoginScreen loginScreen = new LoginScreen(this);
        RegistrationScreen registrationScreen = new RegistrationScreen(this);

        mainScene = new Scene(mainScreen.getView(), 1200, 800);
        mainScene.getStylesheets().add(getClass().getResource("/css/screen-main.css").toExternalForm());

        loginScene = new Scene(loginScreen.getView(), 1200, 800);
        loginScene.getStylesheets().add(getClass().getResource("/css/screen-login.css").toExternalForm());

        registrationScene = new Scene(registrationScreen.getView(), 1200, 800);
        registrationScene.getStylesheets().add(getClass().getResource("/css/screen-register.css").toExternalForm());

        primaryStage.setScene(mainScene);
        primaryStage.setTitle("GloboGym");
        primaryStage.show();
    }

    public static void main(String[] args) {

        Event.deserializeEvents();
        Room.deserializeRooms();
        User.deserializeUsers();
        MembershipCard.deserializeMembershipCards();

        createExampleUsers();

        User.serializeUsers();

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

        homeScene = new Scene(homeScreen.getView(), 1200, 800);
        homeScene.getStylesheets().add(getClass().getResource("/css/screen-home.css").toExternalForm());

        primaryStage.setScene(homeScene);
    }

    public void showMembersManagementScreen(Employee employee, List<User> clubMembers) {

        UsersManagementScreen usersManagementScreen = new UsersManagementScreen(this, employee, clubMembers);

        usersManagementScene = new Scene(usersManagementScreen.getView(), 1200, 800);
        usersManagementScene.getStylesheets().add(getClass().getResource("/css/screen-members-management.css").toExternalForm());

        primaryStage.setScene(usersManagementScene);
    }

    public void showUserAccountScreen(User user) {

        UserAccountScreen userAccountScreen = new UserAccountScreen(this, user);

        userAccountScene = new Scene(userAccountScreen.getView(), 1200, 800);
        userAccountScene.getStylesheets().add(getClass().getResource("/css/screen-user-account.css").toExternalForm());

        primaryStage.setScene(userAccountScene);
    }

    public void showScheduleScreen(User user) {

        ScheduleScreen scheduleScreen = new ScheduleScreen(this, user);

        scheduleScene = new Scene(scheduleScreen.getView(), 1200, 800);
        scheduleScene.getStylesheets().add(getClass().getResource("/css/screen-schedule.css").toExternalForm());

        primaryStage.setScene(scheduleScene);
    }

    public void showPaymentsScreen(ClubMember clubMember) {

        PaymentsScreen paymentsScreen = new PaymentsScreen(this, clubMember);

        paymentsScene = new Scene(paymentsScreen.getView(), 1200, 800);
        paymentsScene.getStylesheets().add(getClass().getResource("/css/screen-payments.css").toExternalForm());

        primaryStage.setScene(paymentsScene);
    }

    public void showMembershipCardScreen(ClubMember clubMember) {

        MembershipCardScreen membershipCardScreen = new MembershipCardScreen(this, clubMember);

        membershipCardScene = new Scene(membershipCardScreen.getView(), 1200, 800);
        membershipCardScene.getStylesheets().add(getClass().getResource("/css/screen-membership-card.css").toExternalForm());

        primaryStage.setScene(membershipCardScene);
    }

    public void showFindUserScreen(Employee employee) {

        FindUserScreen findUserScreen = new FindUserScreen(this, employee);

        findUserScene = new Scene(findUserScreen.getView(), 1200, 800);
        findUserScene.getStylesheets().add(getClass().getResource("/css/screen-find-user.css").toExternalForm());

        primaryStage.setScene(findUserScene);
    }

    public void showAddUserScreen(Employee employee) {

        AddUserScreen addUserScreen = new AddUserScreen(this, employee);

        addUserScene = new Scene(addUserScreen.getView(), 1200, 800);
        addUserScene.getStylesheets().add(getClass().getResource("/css/screen-add-user.css").toExternalForm());

        primaryStage.setScene(addUserScene);
    }

    public void showUpdateUserScreen(User updatingUser, User updatedUser) {

        UpdateUserScreen updateUserScreen = new UpdateUserScreen(this, updatingUser, updatedUser);

        updateUserScene = new Scene(updateUserScreen.getView(), 1200, 800);
        updateUserScene.getStylesheets().add(getClass().getResource("/css/screen-update-user.css").toExternalForm());

        primaryStage.setScene(updateUserScene);
    }

    public void showRoomsManagementScreen(Manager manager, List<Room> rooms) {

        RoomsManagementScreen roomsManagementScreen = new RoomsManagementScreen(this, manager, rooms);

        roomsManagementScene = new Scene(roomsManagementScreen.getView(), 1200, 800);
        roomsManagementScene.getStylesheets().add(getClass().getResource("/css/screen-rooms-management.css").toExternalForm());

        primaryStage.setScene(roomsManagementScene);
    }

    public void showFindRoomScreen(Manager manager) {

        FindRoomScreen findRoomsScreen = new FindRoomScreen(this, manager);

        findRoomScene = new Scene(findRoomsScreen.getView(), 1200, 800);
        findRoomScene.getStylesheets().add(getClass().getResource("/css/screen-find-room.css").toExternalForm());

        primaryStage.setScene(findRoomScene);
    }

    public void showAddRoomScreen(Manager manager) {

        AddRoomScreen addRoomScreen = new AddRoomScreen(this, manager);

        addRoomScene = new Scene(addRoomScreen.getView(), 1200, 800);
        addRoomScene.getStylesheets().add(getClass().getResource("/css/screen-add-room.css").toExternalForm());

        primaryStage.setScene(addRoomScene);
    }

    public void showUpdateRoomScreen(Manager manager, Room room) {

        UpdateRoomScreen updateRoomScreen = new UpdateRoomScreen(this, manager, room);

        updateRoomScene = new Scene(updateRoomScreen.getView(), 1200, 800);
        updateRoomScene.getStylesheets().add(getClass().getResource("/css/screen-update-room.css").toExternalForm());

        primaryStage.setScene(updateRoomScene);
    }

    public void showEventsManagementScreen(Employee employee, List<Event> events) {

        EventsManagementScreen eventsManagementScreen = new EventsManagementScreen(this, employee, events);

        eventsManagementScene = new Scene(eventsManagementScreen.getView(), 1200, 800);
        eventsManagementScene.getStylesheets().add(getClass().getResource("/css/screen-events-management.css").toExternalForm());

        primaryStage.setScene(eventsManagementScene);
    }

    public void showAddOtherEventScreen(Employee employee) {

        AddOtherEventScreen addOtherEventScreen = new AddOtherEventScreen(this, employee);

        addOtherEventScene = new Scene(addOtherEventScreen.getView(), 1200, 800);
        addOtherEventScene.getStylesheets().add(getClass().getResource("/css/screen-add-other-event.css").toExternalForm());

        primaryStage.setScene(addOtherEventScene);
    }

    public void showFindEventScreen(Employee employee) {

        FindEventScreen findEventScreen = new FindEventScreen(this, employee);

        findEventScene = new Scene(findEventScreen.getView(), 1200, 800);
        findEventScene.getStylesheets().add(getClass().getResource("/css/screen-find-event.css").toExternalForm());

        primaryStage.setScene(findEventScene);
    }

    public void showClassesParticipantsScreen(Employee employee, Classes classes) {

        ClassesParticipantsScreen classesParticipantsScreen = new ClassesParticipantsScreen(this, employee, classes);

        classesParticipantsScene = new Scene(classesParticipantsScreen.getView(), 1200, 800);
        classesParticipantsScene.getStylesheets().add(getClass().getResource("/css/screen-classes-participants.css").toExternalForm());

        primaryStage.setScene(classesParticipantsScene);
    }

    public void showStatisticsScreen(User user) {

        StatisticsScreen statisticsScreen = new StatisticsScreen(this, user);

        statisticsScene = new Scene(statisticsScreen.getView(), 1200, 800);
        statisticsScene.getStylesheets().add(getClass().getResource("/css/screen-statistics.css").toExternalForm());

        primaryStage.setScene(statisticsScene);
    }

    private static void createExampleUsers() {

        if (Room.findByRoomNumber(1) == null) {
            Room room1 = new Room(1, 10);
            Room.serializeRooms();
        }

        if (User.findByUsername("testClubm") == null) {
            ClubMember testClubMember = new ClubMember(
                    "testClubm",
                    "testClubm@gmail.com",
                    "!Test123",
                    "Wiktor",
                    "Chudy",
                    LocalDate.of(2000, 4, 16));
            System.out.println("testClubMember registered successfully!");
        }

        if (User.findByUsername("testCoach1") == null) {
            Coach testCoach = new Coach(
                    "testCoach1",
                    "testCoach1@gmail.com",
                    "!Test123",
                    "Robert",
                    "Mocny",
                    LocalDate.of(2003, 8, 1));
            testCoach.setActive(true);
            testCoach.setSpecializations(List.of(ClassesType.ENDURANCE, ClassesType.REHABILITATION, ClassesType.WEIGHT_LOSS));
            System.out.println("testCoach registered successfully!");
        }

        if (User.findByUsername("testCoach2") == null) {
            Coach testCoach1 = new Coach(
                    "testCoach2",
                    "testCoach2@gmail.com",
                    "!Test123",
                    "Eryk",
                    "Wilk",
                    LocalDate.of(2004, 10, 11));
            testCoach1.setActive(true);
            testCoach1.setSpecializations(List.of(ClassesType.CARDIO, ClassesType.WOMEN_TRAINING, ClassesType.STRENGTH));
            System.out.println("testCoach registered successfully!");
        }

        if (User.findByUsername("testEmp") == null) {
            Employee testEmployee = new Employee(
                    "testEmp",
                    "testEmp@gmail.com",
                    "!Test123",
                    "Jan",
                    "Kowalski",
                    LocalDate.of(2002, 9, 11),
                    4000.0);
            System.out.println("testEmployee registered successfully!");
        }

        if (User.findByUsername("testMan") == null) {
            Manager manager = new Manager(
                    "testMan",
                    "testMan@gmail.com",
                    "!Test123",
                    "Karol",
                    "Krawczyk",
                    LocalDate.of(1999, 1, 19),
                    4000.0);
            System.out.println("manager registered successfully!");
        }

    }

    public void exit() {

        System.exit(0);
    }
}