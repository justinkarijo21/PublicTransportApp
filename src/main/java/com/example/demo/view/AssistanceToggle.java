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

        Label title = new Label("Assistentie");

        toggle = new ToggleButton("UIT");
        statusLabel = new Label("Assistentie staat UIT");

        //Als gebruiker klikt word het geupdate

        toggle.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                toggle.setText("AAN");
                statusLabel.setText("Assistentie staat AAN");

            } else {
                toggle.setText("UIT");
                statusLabel.setText("Assistentie staat UIT");
            }
        });
        getChildren().addAll(title, toggle, statusLabel);
    }
    public boolean isAssistanceEnabled() {
        return toggle.isSelected();
    }
}





