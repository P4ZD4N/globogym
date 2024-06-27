package com.p4zd4n.globogym.panes;

import com.p4zd4n.globogym.Main;
import com.p4zd4n.globogym.entities.User;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class TopPane extends BorderPane {

    private VBox topRightContainer;
    private Image logo;
    private ImageView logoView;
    private Button usernameButton;
    private Button logoutButton;

    public TopPane(Main main, User user) {

        logo = new Image(getClass().getResource("/img/logo-no-background.png").toString());
        logoView = new ImageView(logo);
        logoView.setFitHeight(96);
        logoView.setFitWidth(300);

        usernameButton = new Button(user.getUsername());
        usernameButton.setOnAction(e -> main.showUserAccountScreen(user));
        logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> main.showMainScreen());

        topRightContainer = new VBox();
        topRightContainer.setAlignment(Pos.CENTER);
        topRightContainer.setSpacing(10);
        topRightContainer.getChildren().addAll(usernameButton, logoutButton);

        setLeft(logoView);
        setRight(topRightContainer);
    }
}
