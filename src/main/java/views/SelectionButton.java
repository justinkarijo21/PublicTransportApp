package views;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class SelectionButton extends ToggleButton {

    public SelectionButton(String label, ToggleGroup group) {
        super(label);
        this.setToggleGroup(group);
        this.setMinWidth(120);
        this.setMinHeight(40);
        this.getStyleClass().add("selection-button");
    }
}