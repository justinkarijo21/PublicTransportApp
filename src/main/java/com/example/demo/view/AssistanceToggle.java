package com.example.demo.view;

import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;


public class AssistanceToggle extends VBox {

    private final ToggleButton toggle;
    private final Label statusLabel;


    public AssistanceToggle() {

        //Ruimte tussen onderdelen
        setSpacing(10);
        //Padding rondom
        setPadding(new Insets(10));

        Label title = new Label("Wilt u assistentie tijdens uw Reis");

        toggle = new ToggleButton("NEE");
        statusLabel = new Label("U reist zelfstandig");

        //Als gebruiker klikt word het geupdate

        toggle.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                toggle.setText("JA");
                statusLabel.setText("U krijgt Assistentie tijdens uw reis");

            } else {
                toggle.setText("UIT");
                statusLabel.setText("U reist zelfstandig");
            }
        });
        getChildren().addAll(title, toggle, statusLabel);
    }
    public boolean isAssistanceEnabled() {
        return toggle.isSelected();
    }
}





