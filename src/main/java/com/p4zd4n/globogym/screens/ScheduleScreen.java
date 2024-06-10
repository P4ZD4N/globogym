package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.User;
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

    public ScheduleScreen(Main main, User user) {

        this.main = main;
        this.user = user;
    }

    public Pane getView() {

        DatePicker datePicker = new DatePicker();

        VBox box = new VBox(20);
        box.setPadding(new Insets(10));
        box.getChildren().add(datePicker);

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.setTop(new TopPane(main, user));
        borderPane.setCenter(box);
        borderPane.setLeft(new LeftPane(main, user));

        return borderPane;
    }
}
