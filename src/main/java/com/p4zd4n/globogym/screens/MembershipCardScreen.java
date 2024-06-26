package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.ClubMember;
import com.p4zd4n.globogym.entity.Coach;
import com.p4zd4n.globogym.entity.MembershipCard;
import com.p4zd4n.globogym.enums.MembershipCardStatus;
import com.p4zd4n.globogym.panes.CenterPane;
import com.p4zd4n.globogym.panes.LeftPane;
import com.p4zd4n.globogym.panes.TopPane;
import com.p4zd4n.globogym.util.EmptySpace;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MembershipCardScreen {

    private Main main;

    private Double membershipCardPrice;

    private ClubMember clubMember;

    private MembershipCard membershipCard;

    private BorderPane borderPane;
    private CenterPane centerPane;

    private Label infoLabel;
    private Label errorLabel;
    private Label cardStatus;
    private Label purchaseDate;
    private Label expirationDate;

    private Button buyRenewCardButton;

    public MembershipCardScreen(Main main, ClubMember clubMember) {

        this.main = main;
        this.clubMember = clubMember;

        if (clubMember instanceof Coach) {
            membershipCardPrice = MembershipCard.getCoachPrice();
        } else {
            membershipCardPrice = MembershipCard.getClubMemberPrice();
        }

        membershipCard = MembershipCard.findByUsername(clubMember.getUsername());
    }

    public Pane getView() {

        centerPane = new CenterPane();

        infoLabel = new Label();
        infoLabel.getStyleClass().add("info");

        errorLabel = new Label();
        errorLabel.getStyleClass().add("error");

        cardStatus = new Label();
        purchaseDate = new Label();
        expirationDate = new Label();

        buyRenewCardButton = new Button();
        buyRenewCardButton.setOnAction(e -> buyOrRenewMembership());

        if (membershipCard == null) {
            buyRenewCardButton.setText("Buy membership! (" + membershipCardPrice + " PLN = +1 month)");
            cardStatus.setText("Not purchased");
            cardStatus.getStyleClass().add("not-purchased");
        } else if (membershipCard.getMembershipCardStatus().equals(MembershipCardStatus.ACTIVE)) {
            buyRenewCardButton.setText("Extend membership! (" + membershipCardPrice + " PLN = +1 month)");
            cardStatus.setText("Active");
            cardStatus.getStyleClass().add("active");
            purchaseDate.setText("Purchased on " + membershipCard.getPurchaseDate());
            long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), membershipCard.getExpirationDate());
            expirationDate.setText("Expires on " + membershipCard.getExpirationDate() + ", " + daysLeft + " days left");
        } else if (membershipCard.getMembershipCardStatus().equals(MembershipCardStatus.EXPIRED)) {
            buyRenewCardButton.setText("Renew membership! (" + membershipCardPrice + " PLN = +1 month)");
            cardStatus.setText("Expired");
            cardStatus.getStyleClass().add("expired");
            purchaseDate.setText("Purchased on " + membershipCard.getPurchaseDate());
            long daysFromExpiration = ChronoUnit.DAYS.between(LocalDate.now(), membershipCard.getExpirationDate());
            expirationDate.setText("Expired on " + membershipCard.getExpirationDate() + ", " + daysFromExpiration + " days from expiration");
        }

        centerPane.getChildren().add(infoLabel);
        centerPane.getChildren().add(errorLabel);
        centerPane.getChildren().add(cardStatus);
        centerPane.getChildren().add(purchaseDate);
        centerPane.getChildren().add(expirationDate);
        centerPane.getChildren().add(new EmptySpace(40));
        createAndAddCardViewToCenterPane();
        centerPane.getChildren().add(new EmptySpace(40));
        centerPane.getChildren().add(buyRenewCardButton);

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.setTop(new TopPane(main, clubMember));
        borderPane.setCenter(centerPane);
        borderPane.setLeft(new LeftPane(main, clubMember));

        return borderPane;
    }

    private void buyOrRenewMembership() {

        if (membershipCard == null && clubMember.getBalance() < membershipCardPrice) {
            errorLabel.setText("Not enough money! Your current balance: " + clubMember.getBalance());
            return;
        }

        if (membershipCard == null) {
            infoLabel.setText("Successfully bought membership!");
            clubMember.setMembershipCard(new MembershipCard(clubMember));
            clubMember.reduceBalance(membershipCardPrice);
            MembershipCard.serializeMembershipCards();
            return;
        }

        if (clubMember.getBalance() >= membershipCardPrice) {
            infoLabel.setText("Successfully renewed membership!");
            membershipCard.renew();
            clubMember.reduceBalance(membershipCardPrice);
            MembershipCard.serializeMembershipCards();
        } else {
            errorLabel.setText("Not enough money! Your current balance: " + clubMember.getBalance());
        }
    }

    private void createAndAddCardViewToCenterPane() {

        Rectangle card = new Rectangle();
        card.setWidth(400);
        card.setHeight(200);
        card.setArcWidth(20);
        card.setArcHeight(20);

        if (membershipCard != null) {
            if (membershipCard.getMembershipCardStatus().equals(MembershipCardStatus.EXPIRED)) {
                card.setFill(Color.rgb(140, 140, 140));
            } else {
                card.setFill(Color.rgb(101, 201, 255));
            }
        } else {
            card.setFill(Color.rgb(140, 140, 140));
        }

        Image logo = new Image(getClass().getResource("/img/logo-no-background.png").toString());
        ImageView logoView = new ImageView(logo);
        logoView.setFitHeight(72);
        logoView.setFitWidth(225);

        ColorAdjust whiteAdjust = new ColorAdjust();
        whiteAdjust.setBrightness(1.0);
        logoView.setEffect(whiteAdjust);

        Text coachText = new Text("Coach");
        coachText.getStyleClass().add("coach-text");

        Text ownerName = new Text(clubMember.getFirstName() + " " + clubMember.getLastName());
        ownerName.getStyleClass().add("owner-name");

        Text ownerId = new Text("UID " + clubMember.getId());
        ownerId.getStyleClass().add("owner-id");

        logoView.setX(10);
        logoView.setY(10);

        coachText.setX(card.getWidth() - coachText.getLayoutBounds().getWidth() - 45);
        coachText.setY(card.getHeight() - 10);

        ownerName.setX(10);
        ownerName.setY(card.getHeight() - 10);

        ownerId.setX(10);
        ownerId.setY(card.getHeight() - 30);

        Group cardGroup;
        if (clubMember instanceof Coach) {
            cardGroup = new Group(card, logoView, coachText, ownerName, ownerId);
        } else {
            cardGroup = new Group(card, logoView, ownerName, ownerId);
        }

        centerPane.getChildren().add(cardGroup);
    }
}
