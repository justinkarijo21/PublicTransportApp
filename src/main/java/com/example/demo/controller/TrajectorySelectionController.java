package com.example.demo.controller;

import com.example.demo.app.AppFactory;
import com.example.demo.model.Trajectory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TrajectorySelectionController {
    @FXML
    private ComboBox<Trajectory> routeBox;

    @FXML
    private ListView<String> timesList;

    @FXML
    private void initialize() {
        routeBox.setItems(FXCollections.observableArrayList(AppFactory.getTrajectory()));
        routeBox.getSelectionModel().selectFirst();
        routeBox.setOnAction(e -> refreshTimes());
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
                    return timeFormat.format(departureTime) + " \u2192 " + timeFormat.format(arrivalTime);
                })
                .toList();

        timesList.setItems(FXCollections.observableArrayList(items));
    }
}
