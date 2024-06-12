package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.ClubMember;
import com.p4zd4n.globogym.panes.CenterPane;
import com.p4zd4n.globogym.panes.LeftPane;
import com.p4zd4n.globogym.panes.TopPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

public class PaymentsScreen {

    private Main main;

    private ClubMember clubMember;

    private BorderPane borderPane;
    private CenterPane centerPane;

    private Label balanceLabel;

    private Button depositButton;

    public PaymentsScreen(Main main, ClubMember clubMember) {

        this.main = main;
        this.clubMember = clubMember;
    }

    public Pane getView() {

        centerPane = new CenterPane();

        balanceLabel = new Label("Balance: " + clubMember.getBalance() + " PLN");

        depositButton = new Button("Deposit");
        depositButton.setOnAction(e -> showDepositOptionsWindow());

        ObservableList<String> items = FXCollections.observableArrayList();
        clubMember.getPaymentsHistory().reversed().forEach(payment -> items.add(payment.toString()));
        ListView<String> listView = new ListView<>(items);
        listView.setPrefHeight(150);

        ScrollPane scrollPane = new ScrollPane(listView);
        scrollPane.setFitToWidth(true);

        centerPane.getChildren().addAll(balanceLabel, depositButton, scrollPane);

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.setTop(new TopPane(main, clubMember));
        borderPane.setCenter(centerPane);
        borderPane.setLeft(new LeftPane(main, clubMember));

        return borderPane;
    }

    private void showDepositOptionsWindow() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Deposit amount");
        alert.setHeaderText("Select appropriate deposit amount");

        ButtonType button10 = new ButtonType("10");
        ButtonType button20 = new ButtonType("20");
        ButtonType button50 = new ButtonType("50");
        ButtonType button100 = new ButtonType("100");
        ButtonType button200 = new ButtonType("200");

        alert.getButtonTypes().setAll(button10, button20, button50, button100, button200);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            switch (result.get().getText()) {
                case "10" -> clubMember.increaseBalance(10.0);
                case "20" -> clubMember.increaseBalance(20.0);
                case "50" -> clubMember.increaseBalance(50.0);
                case "100" -> clubMember.increaseBalance(100.0);
                case "200" -> clubMember.increaseBalance(200.0);
            }
        }
    }
}
