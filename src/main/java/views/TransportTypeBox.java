package views;

import javafx.geometry.Pos;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class TransportTypeBox extends HBox {
    private final ToggleGroup transportTypeGroup;

    public TransportTypeBox() {
        this.transportTypeGroup = new ToggleGroup();

        SelectionButton busButton = new SelectionButton("Bus", transportTypeGroup);
        SelectionButton trainButton = new SelectionButton("Train", transportTypeGroup);

        busButton.setSelected(true);

        this.getChildren().addAll(busButton, trainButton);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(0);
    }

    public String getSelectedType() {
        SelectionButton chosenButton = (SelectionButton) transportTypeGroup.getSelectedToggle();
        return (chosenButton != null) ? chosenButton.getText() : "";
    }
}