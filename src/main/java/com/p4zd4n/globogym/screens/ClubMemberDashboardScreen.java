package com.p4zd4n.globogym.screens;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entity.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ClubMemberDashboardScreen {

    private Main main;

    private BorderPane borderPane;
    private HBox topContainer;

    private Image logo;
    private ImageView logoView;

    public ClubMemberDashboardScreen(Main main) {

        this.main = main;
    }

    public Pane getView() {

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10, 10, 10, 10));

        logo = new Image(getClass().getResource("/img/logo-no-background.png").toString());
        logoView = new ImageView(logo);
        logoView.setFitHeight(96);
        logoView.setFitWidth(300);

        topContainer = new HBox();
        topContainer.getChildren().add(logoView);
        topContainer.setAlignment(Pos.CENTER);

        borderPane.setTop(topContainer);

        return borderPane;
    }
}
