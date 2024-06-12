package com.p4zd4n.globogym.panes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class CenterPane extends VBox {

    public CenterPane() {

        setAlignment(Pos.CENTER);
        setPadding(new Insets(10, 10, 10, 10));
        setSpacing(10);
    }
}
