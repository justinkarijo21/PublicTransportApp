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
import javafx.scene.layout.VBox;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TrajectorySelectionController {
    @FXML
    private ComboBox<Trajectory> routeBox;

    @FXML
    private ScrollPane detailsScrollPane;

    @FXML
    private Button swapButton;

    @FXML
    private Button searchButton;

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

    private List<Trajectory> allRoutes;
    private boolean updatingRouteBox;
    private Trajectory selectedTrajectory;
    private String selectedDepartureTime;

    @FXML
    private void initialize() {
        allRoutes = AppFactory.getTrajectory();

        transportTypeBox.setOnTransportTypeChanged(transportType -> filterByTransportType(transportType));
        filterByTransportType(TransportType.BUS);

        routeBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                refreshTimes();
            }
        });

        routeBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (!updatingRouteBox) {
                filterRoutes(newValue);
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

    private void filterByTransportType(TransportType transportType) {
        updatingRouteBox = true;
        try {
            var filteredRoutes = allRoutes.stream()
                    .filter(route -> route.getTransportType() == transportType)
                    .toList();

            routeBox.setItems(FXCollections.observableArrayList(filteredRoutes));
            routeBox.getEditor().clear();
            routeBox.getSelectionModel().selectFirst();
        } finally {
            updatingRouteBox = false;
        }
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

        updatingRouteBox = true;

        try {
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

    @FXML
    private void onSearch() {
        if (routeBox.getItems().isEmpty()) {
            timesList.setItems(FXCollections.observableArrayList());
            infoLabel.setText("Geen route gevonden.");
            return;
        }

        updatingRouteBox = true;
        try {
            Trajectory firstRoute = routeBox.getItems().get(0);
            routeBox.getSelectionModel().select(firstRoute);
            routeBox.setValue(firstRoute);
            routeBox.getEditor().setText(firstRoute.toString());
            routeBox.hide();

            infoLabel.setText("");
            refreshTimes();
        } finally {
            updatingRouteBox = false;
        }
    }

    private void filterRoutes(String searchText) {
        if (updatingRouteBox) {
            return;
        }

        updatingRouteBox = true;
        try {
            String typedText = searchText == null ? "" : searchText;
            String search = typedText.toLowerCase().trim();
            TransportType selectedType = transportTypeBox.getSelectedType();
            TransportType typeToFilter = (selectedType != null) ? selectedType : TransportType.BUS;

            var filteredRoutes = allRoutes.stream()
                    .filter(route -> route.getTransportType() == typeToFilter)
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
                    return timeFormat.format(departureTime) + " → " + timeFormat.format(arrivalTime) + "\t(" + selected.getDurationString() + ")";
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
