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

import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class TrajectorySelectionController {
    @FXML
    private ComboBox<String> startStationBox;

    @FXML
    private ComboBox<String> endStationBox;

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
    private List<String> allStartStations = List.of();
    private List<String> currentEndStations = List.of();
    private Trajectory selectedTrajectory;
    private String selectedDepartureTime;
    private boolean updatingComboBoxItems;

    @FXML
    private void initialize() {
        allRoutes = AppFactory.getTrajectory();

        startStationBox.setEditable(true);
        endStationBox.setEditable(true);

        transportTypeBox.setOnTransportTypeChanged(transportType -> fillStationBoxes());
        startStationBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            updateEndStationsForSelectedStart();
            refreshTimes();
        });
        startStationBox.getEditor().textProperty().addListener((obs, oldText, newText) -> {
            if (updatingComboBoxItems) {
                return;
            }
            filterComboBoxItems(startStationBox, allStartStations, newText);
            updateEndStationsForSelectedStart();
            refreshTimes();
        });
        endStationBox.valueProperty().addListener((obs, oldValue, newValue) -> refreshTimes());
        endStationBox.getEditor().textProperty().addListener((obs, oldText, newText) -> {
            if (updatingComboBoxItems) {
                return;
            }
            filterComboBoxItems(endStationBox, currentEndStations, newText);
            refreshTimes();
        });
        fillStationBoxes();

        infoLabel.setText("");

        timesList.setOnMouseClicked(event -> {
            if (timesList.getSelectionModel().getSelectedItem() != null) {
                onTravelSelected();
            }
        });

        timesList.setCellFactory(listView -> new ListCell<>() {
            private final Image wheelchairBlueIcon = new Image(getClass().getResource("/icons/wheelchair blue.png").toExternalForm());
            private final Image wheelchairRedIcon = new Image(getClass().getResource("/icons/wheelchair red.png").toExternalForm());
            private final ImageView wheelchairIcon = new ImageView();

            {
                wheelchairIcon.setFitWidth(46);
                wheelchairIcon.setFitHeight(46);
                wheelchairIcon.setPreserveRatio(true);
                wheelchairIcon.setSmooth(false);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }

                Trajectory selected = findSelectedTrajectory();

                setText(item);

                if (selected != null && selected.isWheelchairCompatibility()) {
                    wheelchairIcon.setImage(wheelchairBlueIcon);
                } else {
                    wheelchairIcon.setImage(wheelchairRedIcon);
                }

                setGraphic(wheelchairIcon);
            }
        });

        backButton.setOnAction(event -> onBack());

        showSearchPane();
    }

    private void fillStationBoxes() {
        fillStartStations();
        updateEndStationsForSelectedStart();
        refreshTimes();
    }

    private void fillStartStations() {
        allStartStations = getStartStationsForSelectedType();

        startStationBox.setItems(FXCollections.observableArrayList(allStartStations));

        if (allStartStations.isEmpty()) {
            startStationBox.setValue(null);
            startStationBox.getEditor().clear();
            return;
        }

        startStationBox.getSelectionModel().selectFirst();
    }

    private void updateEndStationsForSelectedStart() {
        String start = getComboBoxText(startStationBox);

        if (start.isEmpty()) {
            currentEndStations = List.of();
            endStationBox.setItems(FXCollections.observableArrayList());
            endStationBox.setValue(null);
            endStationBox.getEditor().clear();
            return;
        }

        String oldEnd = getComboBoxText(endStationBox);
        currentEndStations = getEndStationsForStart(start);

        endStationBox.setItems(FXCollections.observableArrayList(currentEndStations));

        if (currentEndStations.isEmpty()) {
            endStationBox.setValue(null);
            endStationBox.getEditor().clear();
            return;
        }

        if (currentEndStations.contains(oldEnd)) {
            endStationBox.setValue(oldEnd);
            endStationBox.getEditor().setText(oldEnd);
        } else {
            endStationBox.getSelectionModel().selectFirst();
        }
    }

    private List<String> getStartStationsForSelectedType() {
        TransportType selectedType = transportTypeBox.getSelectedType();
        TransportType typeToFilter = (selectedType != null) ? selectedType : TransportType.BUS;

        return allRoutes.stream()
                .filter(route -> route.getTransportType() == typeToFilter)
                .map(route -> route.getDeparture().getName())
                .distinct()
                .sorted()
                .toList();
    }

    private List<String> getEndStationsForStart(String start) {
        TransportType selectedType = transportTypeBox.getSelectedType();
        TransportType typeToFilter = (selectedType != null) ? selectedType : TransportType.BUS;

        return allRoutes.stream()
                .filter(route -> route.getTransportType() == typeToFilter)
                .filter(route -> route.getDeparture().getName().equals(start))
                .map(route -> route.getArrival().getName())
                .distinct()
                .sorted()
                .toList();
    }

    private void filterComboBoxItems(ComboBox<String> comboBox, List<String> sourceItems, String typedText) {
        String search = typedText == null ? "" : typedText.toLowerCase().trim();

        var filteredItems = sourceItems.stream()
                .filter(item -> item.toLowerCase().contains(search))
                .toList();

        updatingComboBoxItems = true;
        try {
            comboBox.setItems(FXCollections.observableArrayList(filteredItems));
            comboBox.getEditor().setText(typedText);
            comboBox.getEditor().positionCaret(typedText == null ? 0 : typedText.length());
        } finally {
            updatingComboBoxItems = false;
        }

        if (comboBox.isFocused()
                && typedText != null
                && !typedText.isBlank()
                && !filteredItems.isEmpty()) {
            comboBox.show();
        } else {
            comboBox.hide();
        }
    }

    private String getComboBoxText(ComboBox<String> comboBox) {
        String text = comboBox.getEditor().getText();
        return text == null ? "" : text.trim();
    }

    private Trajectory findSelectedTrajectory() {
        String start = getComboBoxText(startStationBox);
        String end = getComboBoxText(endStationBox);

        if (start.isEmpty() || end.isEmpty() || start.equals(end)) {
            return null;
        }

        TransportType selectedType = transportTypeBox.getSelectedType();
        TransportType typeToFilter = (selectedType != null) ? selectedType : TransportType.BUS;

        return allRoutes.stream()
                .filter(route -> route.getTransportType() == typeToFilter)
                .filter(route -> route.getDeparture().getName().equals(start))
                .filter(route -> route.getArrival().getName().equals(end))
                .findFirst()
                .orElse(null);
    }

    @FXML
    private void onSwap() {
        String start = getComboBoxText(startStationBox);
        String end = getComboBoxText(endStationBox);

        if (start.isEmpty() || end.isEmpty()) {
            infoLabel.setText("Kies eerst een begin- en eindstation.");
            return;
        }

        startStationBox.getEditor().setText(end);
        endStationBox.getEditor().setText(start);
        updateEndStationsForSelectedStart();
        refreshTimes();
    }

    private void refreshTimes() {
        Trajectory selected = findSelectedTrajectory();
        if (selected == null) {
            timesList.setItems(FXCollections.observableArrayList());
            if (getComboBoxText(startStationBox).isEmpty() || getComboBoxText(endStationBox).isEmpty()) {
                infoLabel.setText("Kies een begin- en eindstation.");
            } else {
                infoLabel.setText("Geen route gevonden.");
            }
            return;
        }

        infoLabel.setText("");
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

        selectedTrajectory = findSelectedTrajectory();
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

        detailsDeparture.setText(selectedTrajectory.getDeparture().getName());
        detailsArrival.setText(selectedTrajectory.getArrival().getName());
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
