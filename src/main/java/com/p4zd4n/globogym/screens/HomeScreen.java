package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entities.User;
import com.p4zd4n.globogym.enums.OpeningHours;
import com.p4zd4n.globogym.panes.LeftPane;
import com.p4zd4n.globogym.panes.TopPane;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;


public class HomeScreen {

    private Main main;

    private User user;

    private BorderPane borderPane;
    private VBox centerPane;

    private Label welcomeLabel;
    private Label isOpenLabel;

    private TableView<OpeningHours> tableView;

    public HomeScreen(Main main, User user) {

        this.main = main;
        this.user = user;
    }

    public Pane getView() {

        centerPane = new VBox();
        centerPane.setAlignment(Pos.CENTER);
        centerPane.setPadding(new Insets(10, 10, 10, 10));
        centerPane.setSpacing(20);

        welcomeLabel = new Label("Welcome, " + user.getFirstName() + "!");

        isOpenLabel = new Label(OpeningHours.isOpenNow(LocalDate.now().getDayOfWeek()) ? "Open now!" : "Closed now!");
        if (isOpenLabel.getText().equals("Open now!")) {
            isOpenLabel.getStyleClass().add("open");
        } else {
            isOpenLabel.getStyleClass().add("closed");
        }

        centerPane.getChildren().addAll(welcomeLabel, isOpenLabel);

        ObservableList<OpeningHours> openingHoursObservableList = FXCollections.observableArrayList();
        initTable(openingHoursObservableList);

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.setTop(new TopPane(main, user));
        borderPane.setCenter(centerPane);
        borderPane.setLeft(new LeftPane(main, user));

        return borderPane;
    }

    private void initTable(ObservableList<OpeningHours> openingHoursObservableList) {

        tableView = new TableView<>();

        TableColumn<OpeningHours, String> dayColumn = new TableColumn<>("Day");
        dayColumn.setCellValueFactory(cellData -> {
            OpeningHours day = cellData.getValue();
            return new SimpleStringProperty(day.toString());
        });

        TableColumn<OpeningHours, String> hoursColumn = new TableColumn<>("Hours");
        hoursColumn.setCellValueFactory(cellData -> {
            OpeningHours hours = cellData.getValue();
            if (hours.getOpeningTime() == null || hours.getClosingTime() == null) {
                return new SimpleStringProperty("Closed");
            } else {
                return new SimpleStringProperty(hours.getOpeningTime() + " - " + hours.getClosingTime());
            }
        });

        dayColumn.setPrefWidth(250);
        hoursColumn.setPrefWidth(250);

        tableView.getColumns().addAll(dayColumn, hoursColumn);

        tableView.setMaxWidth(500);
        tableView.setMaxHeight(230);

        Map<DayOfWeek, String> openingHoursMap = OpeningHours.getAllOpeningHours();
        for (Map.Entry<DayOfWeek, String> entry : openingHoursMap.entrySet()) {
            DayOfWeek day = entry.getKey();
            OpeningHours hours = OpeningHours.getByDayOfWeek(day);
            openingHoursObservableList.add(hours);
        }

        tableView.setItems(openingHoursObservableList);

        tableView.setRowFactory(tv -> new TableRow<OpeningHours>() {
            @Override
            protected void updateItem(OpeningHours item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else {
                    if (item.name().equalsIgnoreCase(LocalDate.now().getDayOfWeek().name())) {
                        setStyle("-fx-background-color: #3ece70;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });

        centerPane.getChildren().add(tableView);
    }
}
