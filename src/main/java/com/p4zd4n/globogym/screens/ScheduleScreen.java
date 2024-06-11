package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.User;
import com.p4zd4n.globogym.panes.CenterPane;
import com.p4zd4n.globogym.panes.LeftPane;
import com.p4zd4n.globogym.panes.TopPane;
import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ScheduleScreen {

    private Main main;

    private User user;

    private BorderPane borderPane;
    private CenterPane centerPane;

    public ScheduleScreen(Main main, User user) {

        this.main = main;
        this.user = user;
    }

    public Pane getView() {

        DatePicker datePicker = new DatePicker();

        centerPane = new CenterPane();
        centerPane.getChildren().add(datePicker);

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.setTop(new TopPane(main, user));
        borderPane.setCenter(centerPane);
        borderPane.setLeft(new LeftPane(main, user));

        return borderPane;
    }
}
