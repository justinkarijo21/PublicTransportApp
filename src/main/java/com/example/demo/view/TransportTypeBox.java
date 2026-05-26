package com.example.demo.view;

import com.example.demo.model.TransportType;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class TransportTypeBox extends HBox {
    private final ToggleGroup transportTypeGroup;

    public TransportTypeBox() {
        this.transportTypeGroup = new ToggleGroup();

        SelectionButton busButton = new SelectionButton(
                TransportType.BUS.getLabel(),
                transportTypeGroup,
                createIcon("/icons/bus icon green.png")
        );
        busButton.setUserData(TransportType.BUS);

        SelectionButton trainButton = new SelectionButton(
                TransportType.TREIN.getLabel(),
                transportTypeGroup,
                createIcon("/icons/train icon yellow.png")
        );
        trainButton.setUserData(TransportType.TREIN);

        busButton.setSelected(true);

        this.getChildren().addAll(busButton, trainButton);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(0);
    }

    private ImageView createIcon(String path) {
        Image image = new Image(
                Objects.requireNonNull(getClass().getResource(path)).toExternalForm()
        );

        ImageView icon = new ImageView(image);
        icon.setFitWidth(105);
        icon.setFitHeight(105);
        icon.setPreserveRatio(true);

        return icon;
    }

    public TransportType getSelectedType() {
        SelectionButton chosenButton = (SelectionButton) transportTypeGroup.getSelectedToggle();
        return (chosenButton != null) ? (TransportType) chosenButton.getUserData() : null;
    }

    public void setOnTransportTypeChanged(java.util.function.Consumer<TransportType> callback) {
        transportTypeGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                callback.accept(getSelectedType());
            }
        });
    }
}

