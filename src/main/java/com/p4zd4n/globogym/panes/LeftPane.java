package com.p4zd4n.globogym.panes;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.User;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class LeftPane extends VBox {

    private Button homeButton;
    private Button paymentsButton;
    private Button scheduleButton;
    private Button statisticsButton;

    public LeftPane(Main main, User user) {

        setAlignment(Pos.CENTER);
        setSpacing(10);

        homeButton = new Button("Home");
        homeButton.setOnAction(e -> main.showClubMemberDashboardScreen(user));

        paymentsButton = new Button("Payments");

        scheduleButton = new Button("Schedule");
        scheduleButton.setOnAction(e -> main.showScheduleScreen(user));

        statisticsButton = new Button("Statistics");

        getChildren().addAll(homeButton, paymentsButton, scheduleButton, statisticsButton);
    }
}
