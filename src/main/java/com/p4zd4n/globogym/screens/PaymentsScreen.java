package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.ClubMember;
import com.p4zd4n.globogym.panes.CenterPane;
import com.p4zd4n.globogym.panes.LeftPane;
import com.p4zd4n.globogym.panes.TopPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Deposit Amount");

        VBox dialogVbox = new VBox(20);
        dialogVbox.setPadding(new Insets(10));
        dialogVbox.getChildren().add(new Label("Select appropriate deposit amount"));
        dialogVbox.setAlignment(Pos.CENTER);

        Button button10 = createDepositButton("10", 10.0);
        Button button20 = createDepositButton("20", 20.0);
        Button button50 = createDepositButton("50", 50.0);
        Button button100 = createDepositButton("100", 100.0);
        Button button200 = createDepositButton("200", 200.0);

        dialogVbox.getChildren().addAll(button10, button20, button50, button100, button200);

        Scene dialogScene = new Scene(dialogVbox, 300, 300);

        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }

    private Button createDepositButton(String text, double amount) {

        Button button = new Button(text);

        button.setMinWidth(100);
        button.setOnAction(e -> {
            clubMember.increaseBalance(amount);
            ((Stage) button.getScene().getWindow()).close();
            main.showPaymentsScreen(clubMember);
        });

        return button;
    }
}
