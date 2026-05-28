package com.example.demo.view;

import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class AssistanceToggle extends VBox {

    private final ToggleButton yesButton;
    private final ToggleButton noButton;
    private final Label statusLabel;


    public AssistanceToggle() {
        setSpacing(10);
        setPadding(new Insets(12, 0, 0, 0));
        getStyleClass().add("assistance-section");

        Label title = new Label("Wilt u assistentie tijdens uw reis?");
        title.getStyleClass().add("field-label");

        ToggleGroup group = new ToggleGroup();
        yesButton = new ToggleButton("Ja");
        noButton = new ToggleButton("Nee");
        yesButton.setToggleGroup(group);
        noButton.setToggleGroup(group);
        noButton.setSelected(true);

        yesButton.getStyleClass().add("choice-toggle");
        noButton.getStyleClass().add("choice-toggle");

        statusLabel = new Label("U reist zelfstandig.");
        statusLabel.getStyleClass().add("message-label");

        group.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == yesButton) {
                statusLabel.setText("U reist met assistentie.");
            } else {
                noButton.setSelected(true);
                statusLabel.setText("U reist zelfstandig.");
            }
        });

        HBox choices = new HBox(8, yesButton, noButton, statusLabel);
        choices.getStyleClass().add("assistance-choices");

        getChildren().addAll(title, choices);
    }

    public boolean isAssistanceEnabled() {
        return yesButton.isSelected();
    }
}

