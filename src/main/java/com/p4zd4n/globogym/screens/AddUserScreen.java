package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.Employee;
import com.p4zd4n.globogym.forms.AddUserForm;
import com.p4zd4n.globogym.panes.LeftPane;
import com.p4zd4n.globogym.panes.TopPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class AddUserScreen {

    private Main main;

    private Employee employee;

    private BorderPane borderPane;
    private BorderPane centerContainer;
    private HBox centerTopContainer;
    private AddUserForm form;
    private HBox bottomContainer;

    private Label errorLabel;

    private Button addButton;

    public AddUserScreen(Main main, Employee employee) {

        this.main = main;
        this.employee = employee;
    }

    public Pane getView() {

        errorLabel = new Label();
        errorLabel.getStyleClass().add("label");
        errorLabel.getStyleClass().add("error");

        centerTopContainer = new HBox();
        centerTopContainer.setAlignment(Pos.CENTER);
        centerTopContainer.getChildren().add(errorLabel);

        form = new AddUserForm();
        form.getStyleClass().add("container");
        form.setVgap(10);
        form.setHgap(10);

        centerContainer = new BorderPane();
        centerContainer.setTop(centerTopContainer);
        centerContainer.setCenter(form);

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.setTop(new TopPane(main, employee));
        borderPane.setCenter(centerContainer);
        borderPane.setLeft(new LeftPane(main, employee));

        addButton = new Button("Add");
        addButton.getStyleClass().add("button");
        addButton.setOnAction(e -> addUser());

        bottomContainer = new HBox(10);
        bottomContainer.setAlignment(Pos.CENTER);
        bottomContainer.getChildren().add(addButton);

        borderPane.setBottom(bottomContainer);

        return borderPane;
    }

    public void addUser() {

    }
}
