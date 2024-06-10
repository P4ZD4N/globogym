package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.User;
import com.p4zd4n.globogym.panes.TopBorderPane;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class ClubMemberDashboardScreen {

    private Main main;

    private User user;

    private BorderPane borderPane;

    public ClubMemberDashboardScreen(Main main, User user) {

        this.main = main;
        this.user = user;
    }

    public Pane getView() {

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10, 10, 10, 10));
        borderPane.setTop(new TopBorderPane(main, user));

        return borderPane;
    }
}
