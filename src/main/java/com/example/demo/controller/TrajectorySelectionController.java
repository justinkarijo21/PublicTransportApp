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

    // knop naast Swap om de eerste gevonden route te kiezen
    @FXML
    private Button searchButton;

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

    // bepaalt welke route gebruikt moet worden voor Swap
    private Trajectory getSelectedRouteForSwap() {
        Trajectory selected = routeBox.getValue();

        if (selected != null) {
            return selected;
        }

        if (!routeBox.getItems().isEmpty()) {
            return routeBox.getItems().get(0);
        }

        return null;
    }

    @FXML
    private void onSwap() {
        // werkt ook als gebruiker alleen heeft getypt maar nog niet op Zoeken heeft gedrukt
        Trajectory selected = getSelectedRouteForSwap();

        if (selected == null) {
            infoLabel.setText("Kies eerst een route.");
            return;
        }

        // zoek de omgekeerde route in alle routes, niet alleen in de gefilterde lijst
        Trajectory reverse = allRoutes.stream()
                .filter(route ->
                        route.getDeparture().equals(selected.getArrival())
                                && route.getArrival().equals(selected.getDeparture())
                )
                .findFirst()
                .orElse(null);

        if (reverse == null) {
            infoLabel.setText("Geen omgekeerde route gevonden.");
            return;
        }

        // voorkom dat de zoek-listener opnieuw onbedoeld gaat filteren
        updatingRouteBox = true;

        try {
            routeBox.setItems(FXCollections.observableArrayList(allRoutes));
            routeBox.getSelectionModel().select(reverse);
            routeBox.setValue(reverse);
            routeBox.getEditor().setText(reverse.toString());
            routeBox.getEditor().positionCaret(reverse.toString().length());
            routeBox.hide();

            infoLabel.setText("");
            refreshTimes();
        } finally {
            updatingRouteBox = false;
        }
    }
    // kiest de eerste route uit de gefilterde lijst wanneer de gebruiker op Zoeken klikt
    @FXML
    private void onSearch() {
        if (routeBox.getItems().isEmpty()) {
            timesList.setItems(FXCollections.observableArrayList());
            infoLabel.setText("Geen route gevonden.");
            return;
        }

        Trajectory firstRoute = routeBox.getItems().get(0);
        routeBox.getSelectionModel().select(firstRoute);
        routeBox.setValue(firstRoute);
        routeBox.getEditor().setText(firstRoute.toString());
        routeBox.hide();

        infoLabel.setText("");
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
                return;
            }

            routeBox.show();

                infoLabel.setText("");

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
