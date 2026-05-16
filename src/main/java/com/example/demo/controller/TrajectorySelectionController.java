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
import java.util.List;

public class TrajectorySelectionController {
    @FXML
    private ComboBox<Trajectory> routeBox;

    @FXML
    private Button swapButton;

    @FXML
    private Label infoLabel;

    @FXML
    private ListView<String> timesList;

    private List<Trajectory> allRoutes;
    private boolean updatingRouteBox;

    @FXML
    private void initialize() {
        allRoutes = AppFactory.getTrajectory();

        routeBox.setItems(FXCollections.observableArrayList(allRoutes));
        routeBox.setEditable(true);
        routeBox.getSelectionModel().selectFirst();

        routeBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                refreshTimes();
            }
        });
        routeBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> filterRoutes(newValue));

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

    // filtert routes op vertrek, aankomst of volledige routenaam
    private void filterRoutes(String searchText) {
        if (updatingRouteBox) {
            return;
        }

        updatingRouteBox = true;
        try {
            String typedText = searchText == null ? "" : searchText;
            String search = typedText.toLowerCase().trim();

            var filteredRoutes = allRoutes.stream()
                    .filter(route ->
                            route.getDeparture().toLowerCase().contains(search)
                                    || route.getArrival().toLowerCase().contains(search)
                                    || route.toString().toLowerCase().contains(search)
                    )
                    .toList();

            routeBox.setItems(FXCollections.observableArrayList(filteredRoutes));
            routeBox.getEditor().setText(typedText);
            routeBox.getEditor().positionCaret(typedText.length());

            if (filteredRoutes.isEmpty()) {
                timesList.setItems(FXCollections.observableArrayList());
                infoLabel.setText("Geen route gevonden.");
            } else {
                infoLabel.setText("");
            }
        } finally {
            updatingRouteBox = false;
        }
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
