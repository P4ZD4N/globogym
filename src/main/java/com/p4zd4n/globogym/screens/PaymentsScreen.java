package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.ClubMember;
import com.p4zd4n.globogym.entity.User;
import com.p4zd4n.globogym.panes.CenterPane;
import com.p4zd4n.globogym.panes.LeftPane;
import com.p4zd4n.globogym.panes.TopPane;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class PaymentsScreen {

    private Main main;

    private ClubMember clubMember;

    private BorderPane borderPane;
    private CenterPane centerPane;

    private Label balanceLabel;

    public PaymentsScreen(Main main, ClubMember clubMember) {

        this.main = main;
        this.clubMember = clubMember;
    }

    public Pane getView() {

        centerPane = new CenterPane();

        balanceLabel = new Label(clubMember.getBalance() + " zł");

        centerPane.getChildren().add(balanceLabel);

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.setTop(new TopPane(main, clubMember));
        borderPane.setCenter(centerPane);
        borderPane.setLeft(new LeftPane(main, clubMember));

        return borderPane;
    }
}
