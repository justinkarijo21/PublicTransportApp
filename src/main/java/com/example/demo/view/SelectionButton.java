package com.example.demo.view;

import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;

public class SelectionButton extends ToggleButton {

    public SelectionButton(String label, ToggleGroup group) {
        super(label);
        configure(group);
    }

    public SelectionButton(String label, ToggleGroup group, Node icon) {
        super(label, icon);
        configure(group);

        this.setContentDisplay(ContentDisplay.TOP); //icoonjte onder de tekst
        this.setAlignment(Pos.TOP_CENTER);
        this.setGraphicTextGap(0.25);
    }

    private void configure(ToggleGroup group) {
        this.setToggleGroup(group);

        this.setMinWidth(0);
        this.setMinHeight(0);

        this.setPrefHeight(150);
        this.setPrefWidth(120);

        this.getStyleClass().add("Selectie-knop");
    }
}