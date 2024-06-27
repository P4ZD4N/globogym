package com.p4zd4n.globogym.utils;

import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class BoldDescriptorLabel extends Label {

    public BoldDescriptorLabel(String label, String value) {

        Text boldText = new Text(label);
        boldText.setStyle("-fx-font-weight: bold");

        Text normalText = new Text(value);
        TextFlow textFlow = new TextFlow(boldText, normalText);

        this.setGraphic(textFlow);
    }
}
