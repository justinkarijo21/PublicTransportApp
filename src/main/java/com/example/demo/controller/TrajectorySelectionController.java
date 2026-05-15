package com.example.demo.controller;

import com.example.demo.app.AppFactory;
import com.example.demo.model.Trajectory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TrajectorySelectionController {
    @FXML
    private ComboBox<Trajectory> routeBox;

    @FXML
    private Button swapButton;

    @FXML
    private Label infoLabel;

    @FXML
    private ListView<String> timesList;

    @FXML
    private void initialize() {
        routeBox.setItems(FXCollections.observableArrayList(AppFactory.getTrajectory()));
        routeBox.getSelectionModel().selectFirst();
        routeBox.setOnAction(e -> refreshTimes());
        infoLabel.setText("");
        refreshTimes();
    }

    @FXML
    private void onSwap() {
        Trajectory selected = routeBox.getValue();
        if (selected == null) {
            return;
        }

        Trajectory reverse = routeBox.getItems().stream()
                .filter(t -> t.getDeparture().equals(selected.getArrival()) && t.getArrival().equals(selected.getDeparture()))
                .findFirst()
                .orElse(null);

        if (reverse == null) {
            infoLabel.setText("No return trip found.");
            return;
        }

        infoLabel.setText("");
        routeBox.getSelectionModel().select(reverse);
        refreshTimes();
    }

    private void refreshTimes() {
        Trajectory selected = routeBox.getValue();
        if (selected == null) {
            timesList.setItems(FXCollections.observableArrayList());
            return;
        }
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        var items = selected.getDepartureTimes().stream()
                .map(departure -> {
                    LocalTime departureTime = LocalTime.parse(departure);
                    LocalTime arrivalTime = departureTime.plusMinutes(selected.getTravelMinutes());
                    return timeFormat.format(departureTime) + " \u2192 " + timeFormat.format(arrivalTime) + "\t(" + selected.getDurationString() + ")";
                })
                .toList();

        timesList.setItems(FXCollections.observableArrayList(items));
    }
}
