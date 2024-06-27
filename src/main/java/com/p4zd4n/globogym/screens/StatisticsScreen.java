package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entities.*;
import com.p4zd4n.globogym.panes.LeftPane;
import com.p4zd4n.globogym.panes.TopPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;



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

        Label numberOfClassesParticipatedIn = createLabelWithBoldDescriptor(
                "Number of classes participated in: ", String.valueOf(clubMember.getClassesParticipatedIn().size())
        );

        centerPane.getChildren().addAll(numberOfClassesParticipatedIn);
    }

    private void initStatisticsForCoach(Coach coach) {

        Label numberOfClassesCreated = createLabelWithBoldDescriptor(
                "Number of classes created: ", String.valueOf(coach.getClassesCreatedByCoach().size())
        );

        Label numberOfClassesParticipatedIn = createLabelWithBoldDescriptor(
                "Number of classes participated in: ", String.valueOf(coach.getClassesParticipatedIn().size())
        );

        centerPane.getChildren().addAll(numberOfClassesCreated, numberOfClassesParticipatedIn);
    }

    private void initStatisticsForEmployee(Employee employee) {

        Label numberOfEventsCreated = createLabelWithBoldDescriptor(
                "Number of events created: ", String.valueOf(employee.getEventsCreatedByEmployee().size())
        );

        centerPane.getChildren().addAll(numberOfEventsCreated);
    }

    private void initStatisticsForManager(Manager manager) {

        Label numberOfEventsCreated = createLabelWithBoldDescriptor(
                "Number of events created: ", String.valueOf(manager.getEventsCreatedByEmployee().size())
        );

        centerPane.getChildren().addAll(numberOfEventsCreated);
    }

    private Label createLabelWithBoldDescriptor(String label, String value) {

        Text boldText = new Text(label);
        boldText.setStyle("-fx-font-weight: bold");

        Text normalText = new Text(value);
        TextFlow textFlow = new TextFlow(boldText, normalText);

        return new Label("", textFlow);
    }
}
