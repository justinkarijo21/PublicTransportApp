package views;

import models.Trajectory;
import com.example.demo.AppFactory;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import models.Routes;


public class TrajectorySelectionView {

    public VBox getView() {
        Label titelLabel = new Label("Kies een traject");
        Label startLocationLabel = new Label("Beginstation:");
        ComboBox<String> startLocationChooser = new ComboBox<>(
                FXCollections.observableArrayList(Routes.getStartLocations())
        );
        startLocationChooser.setPromptText("Selecteer een beginstation");

        Label selectedStartLocationLabel = new Label("Nog geen beginstation gekozen.");

        Label endLocationLabel = new Label("Eindstation:");
        ComboBox<String> endLocationChooser = new ComboBox<>(
                FXCollections.observableArrayList(Routes.getEndLocations())
        );
        endLocationChooser.setPromptText("Selecteer een eindstation");

        Label selectedEndLocationLabel = new Label("Nog geen eindstation gekozen.");



        Label tijdenLabel = new Label("Beschikbare tijden:");

        ComboBox<Trajectory> trajectoryChooser = new ComboBox<>(
                FXCollections.observableArrayList(AppFactory.getTrajectory())
        );
        trajectoryChooser.setPromptText("Selecteer een traject");

        ListView<String> timeList = new ListView<>();
        timeList.setPrefHeight(200);

        startLocationChooser.setOnAction(e -> {
            String selectedStartLocation = startLocationChooser.getValue();

            if (selectedStartLocation != null) {
                selectedStartLocationLabel.setText("Gekozen beginstation: " + selectedStartLocation);
            }
        });

        endLocationChooser.setOnAction(e -> {
            String selectedEndLocation = endLocationChooser.getValue();

            if (selectedEndLocation != null) {
                selectedEndLocationLabel.setText("Gekozen eindstation: " + selectedEndLocation);
            }
        });

        trajectoryChooser.setOnAction(e -> {
            Trajectory chosenTrajectory = trajectoryChooser.getValue();
            if (chosenTrajectory != null) {
                timeList.setItems(FXCollections.observableArrayList(chosenTrajectory.getTijden()));
            }
        });

        VBox trajectoryLayout = new VBox(
                20,
                titelLabel,
                startLocationLabel,
                startLocationChooser,
                selectedStartLocationLabel,
                endLocationLabel,
                endLocationChooser,
                selectedEndLocationLabel,
                trajectoryChooser,
                tijdenLabel,
                timeList
        );
        trajectoryLayout.setPadding(new Insets(10));
        return trajectoryLayout;
    }
}