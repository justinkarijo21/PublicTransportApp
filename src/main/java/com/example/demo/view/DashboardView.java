package com.example.demo.view;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import com.example.demo.view.TransportTypeBox;

import java.io.IOException;

public class DashboardView {
    private final BorderPane mainLayout;

    public DashboardView() {
        this.mainLayout = new BorderPane();
        this.mainLayout.setPadding(new Insets(20));

        mainLayout.setCenter(loadTrajectorySelection());
    } // <-- Added closing brace for the constructor

    private Parent loadTrajectorySelection() {
        try {
            return FXMLLoader.load(DashboardView.class.getResource("/com/example/demo/view/trajectory-selection-view.fxml"));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load trajectory selection view", e);
        }
    }

    public BorderPane getRoot() {
        return mainLayout;
    }
}