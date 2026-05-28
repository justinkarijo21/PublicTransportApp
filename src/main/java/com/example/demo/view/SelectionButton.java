package com.example.demo.view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class SelectionButton extends ToggleButton {

    public SelectionButton(String label, ToggleGroup group) {
        super(label);
        configure(group);
    }

    public SelectionButton(String label, ToggleGroup group, Node icon) {
        super(label, icon);
        configure(group);
        this.setContentDisplay(ContentDisplay.TOP);
        this.setGraphicTextGap(6);
        this.setAlignment(Pos.CENTER);
    }

    private void configure(ToggleGroup group) {
        this.setToggleGroup(group);

        this.setMinWidth(130);
        this.setMinHeight(84);
        this.setPrefHeight(84);
        this.setPrefWidth(140);
        this.setAlignment(Pos.CENTER);

        this.getStyleClass().add("transport-toggle");
    }
}
