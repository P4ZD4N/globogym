package com.p4zd4n.globogym;

import com.p4zd4n.globogym.entity.User;
import com.p4zd4n.globogym.screens.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;
    private Scene mainScene;
    private Scene loginScene;
    private Scene registrationScene;
    private Scene clubMemberDashboardScene;
    private Scene userAccountScene;
    private Scene scheduleScene;
    private Scene paymentsScene;

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

        User.deserializeUsers();
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

    public void showPaymentsScreen(User user) {

        PaymentsScreen paymentsScreen = new PaymentsScreen(this, user);

        paymentsScene = new Scene(paymentsScreen.getView(), 800, 800);
        paymentsScene.getStylesheets().add(getClass().getResource("/css/screen-payments.css").toExternalForm());

        primaryStage.setScene(paymentsScene);
    }

    public void exit() {

        System.exit(0);
    }
}