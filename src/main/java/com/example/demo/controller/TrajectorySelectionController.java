package com.example.demo.controller;

import com.example.demo.model.TransportType;
import com.example.demo.model.Trajectory;
import com.example.demo.app.AppFactory;
import com.example.demo.view.TransportTypeBox;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TrajectorySelectionController {
    @FXML
    private ComboBox<Trajectory> routeBox;

    @FXML
    private TextField searchField;

    @FXML
    private ScrollPane detailsScrollPane;

    @FXML
    private Button swapButton;

    @FXML
    private Label infoLabel;

    @FXML
    private ListView<String> timesList;

    @FXML
    private TransportTypeBox transportTypeBox;

    @FXML
    private VBox searchPane;

    @FXML
    private VBox detailsPane;

    @FXML
    private Button backButton;

    @FXML
    private Label detailsDeparture;

    @FXML
    private Label detailsArrival;

    @FXML
    private Label detailsDepartureTime;

    @FXML
    private Label detailsArrivalTime;

    @FXML
    private Label detailsDuration;

    @FXML
    private Label detailsTransportType;

    @FXML
    private Label detailsTimes;

    private List<Trajectory> allRoutes;
    private Trajectory selectedTrajectory;
    private String selectedDepartureTime;

    @FXML
    private void initialize() {
        allRoutes = AppFactory.getTrajectory();

        transportTypeBox.setOnTransportTypeChanged(transportType -> applyFilters());
        searchField.textProperty().addListener((observable, oldValue, newValue) -> applyFilters());
        applyFilters();

        routeBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                refreshTimes();
            }
        });

        infoLabel.setText("");

        timesList.setOnMouseClicked(event -> {
            if (timesList.getSelectionModel().getSelectedItem() != null) {
                onTravelSelected();
            }
        });

        backButton.setOnAction(event -> onBack());

        showSearchPane();
    }

    private void applyFilters() {
        String searchText = searchField.getText() == null ? "" : searchField.getText().toLowerCase().trim();
        TransportType selectedType = transportTypeBox.getSelectedType();
        TransportType typeToFilter = (selectedType != null) ? selectedType : TransportType.BUS;

        var filteredRoutes = allRoutes.stream()
                .filter(route -> route.getTransportType() == typeToFilter)
                .filter(route ->
                        route.getDeparture().toLowerCase().contains(searchText)
                                || route.getArrival().toLowerCase().contains(searchText)
                                || route.toString().toLowerCase().contains(searchText)
                )
                .toList();

        routeBox.setItems(FXCollections.observableArrayList(filteredRoutes));

        if (filteredRoutes.isEmpty()) {
            routeBox.setValue(null);
            timesList.setItems(FXCollections.observableArrayList());
            infoLabel.setText("Geen route gevonden.");
            return;
        }

        routeBox.getSelectionModel().selectFirst();
        infoLabel.setText("");
        refreshTimes();
    }

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
        Trajectory selected = getSelectedRouteForSwap();

        if (selected == null) {
            infoLabel.setText("Kies eerst een route.");
            return;
        }

        Trajectory reverse = allRoutes.stream()
                .filter(route ->
                        route.getDeparture().equals(selected.getArrival())
                                && route.getArrival().equals(selected.getDeparture())
                                && route.getTransportType().equals(selected.getTransportType())
                )
                .findFirst()
                .orElse(null);

        if (reverse == null) {
            infoLabel.setText("Geen omgekeerde route gevonden.");
            return;
        }

        routeBox.getSelectionModel().select(reverse);
        routeBox.setValue(reverse);
        routeBox.hide();

        infoLabel.setText("");
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
                    return "Vertrek " + timeFormat.format(departureTime)
                            + "   Aankomst " + timeFormat.format(arrivalTime)
                            + "   Duur: " + selected.getDurationString();
                })
                .toList();

        timesList.setItems(FXCollections.observableArrayList(items));
    }

    private void onTravelSelected() {
        int selectedIndex = timesList.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            return;
        }

        selectedTrajectory = routeBox.getValue();
        if (selectedTrajectory == null) {
            return;
        }

        selectedDepartureTime = selectedTrajectory.getDepartureTimes().get(selectedIndex);
        showDetailsPane();
    }

    private void showDetailsPane() {
        searchPane.setManaged(false);
        searchPane.setVisible(false);
        detailsScrollPane.setManaged(true);
        detailsScrollPane.setVisible(true);

        updateDetailsDisplay();
    }

    private void updateDetailsDisplay() {
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime departureTime = LocalTime.parse(selectedDepartureTime);
        LocalTime arrivalTime = departureTime.plusMinutes(selectedTrajectory.getTravelMinutes());

        detailsDeparture.setText(selectedTrajectory.getDeparture());
        detailsArrival.setText(selectedTrajectory.getArrival());
        detailsDepartureTime.setText(timeFormat.format(departureTime));
        detailsArrivalTime.setText(timeFormat.format(arrivalTime));
        detailsDuration.setText(selectedTrajectory.getDurationString());
        detailsTransportType.setText(selectedTrajectory.getTransportType().toString());
        detailsTimes.setText(String.join(", ", selectedTrajectory.getDepartureTimes()));
    }

    private void showSearchPane() {
        searchPane.setManaged(true);
        searchPane.setVisible(true);
        detailsScrollPane.setManaged(false);
        detailsScrollPane.setVisible(false);
    }

    @FXML
    private void onBack() {
        showSearchPane();
        timesList.getSelectionModel().clearSelection();
        selectedTrajectory = null;
        selectedDepartureTime = null;
    }
}
