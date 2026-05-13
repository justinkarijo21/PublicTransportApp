package views;

import models.Trajectory;
import com.example.demo.AppFactory;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;


public class TrajectorySelectionView {

    public VBox getView() {
        Label titelLabel = new Label("Kies een traject");
        Label tijdenLabel = new Label("Beschikbare tijden:");

        ComboBox<Trajectory> trajectoryChooser = new ComboBox<>(
                FXCollections.observableArrayList(AppFactory.getTrajectory())
        );
        trajectoryChooser.setPromptText("Selecteer een traject");

        ListView<String> timeList = new ListView<>();
        timeList.setPrefHeight(200);

        trajectoryChooser.setOnAction(e -> {
            Trajectory chosenTrajectory = trajectoryChooser.getValue();
            if (chosenTrajectory != null) {
                timeList.setItems(FXCollections.observableArrayList(chosenTrajectory.getTijden()));
            }
        });

        VBox trajectoryLayout = new VBox(20, titelLabel, trajectoryChooser, tijdenLabel, timeList);
        trajectoryLayout.setPadding(new Insets(10));
        return trajectoryLayout;
    }
}