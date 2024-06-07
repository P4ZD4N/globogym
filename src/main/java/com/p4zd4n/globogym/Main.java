package com.p4zd4n.globogym;

import com.p4zd4n.globogym.entity.User;
import com.p4zd4n.globogym.screens.ClubMemberDashboardScreen;
import com.p4zd4n.globogym.screens.LoginScreen;
import com.p4zd4n.globogym.screens.MainScreen;
import com.p4zd4n.globogym.screens.RegistrationScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;
    private Scene mainScene;
    private Scene loginScene;
    private Scene registrationScene;
    private Scene clubMemberDashboardScene;

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;

        MainScreen mainScreen = new MainScreen(this);
        LoginScreen loginScreen = new LoginScreen(this);
        RegistrationScreen registrationScreen = new RegistrationScreen(this);
        ClubMemberDashboardScreen clubMemberDashboardScreen = new ClubMemberDashboardScreen(this);

        mainScene = new Scene(mainScreen.getView(), 800, 800);
        mainScene.getStylesheets().add(getClass().getResource("/css/screen-main.css").toExternalForm());

        loginScene = new Scene(loginScreen.getView(), 800, 800);
        loginScene.getStylesheets().add(getClass().getResource("/css/screen-login.css").toExternalForm());

        registrationScene = new Scene(registrationScreen.getView(), 800, 800);
        registrationScene.getStylesheets().add(getClass().getResource("/css/screen-register.css").toExternalForm());

        clubMemberDashboardScene = new Scene(clubMemberDashboardScreen.getView(), 800, 800);

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

    public void showClubMemberDashboardScreen() {

        primaryStage.setScene(clubMemberDashboardScene);
    }

    public void exit() {

        System.exit(0);
    }
}