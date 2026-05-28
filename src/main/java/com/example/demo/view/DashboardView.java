package com.example.demo.view;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DashboardView {
    private final BorderPane mainLayout;

    public DashboardView() {
        this.mainLayout = new BorderPane();
        this.mainLayout.setPadding(new Insets(32));
        this.mainLayout.getStyleClass().add("app-root");

        VBox centerContent = new VBox();
        centerContent.setSpacing(20);
        centerContent.getStyleClass().add("planner-shell");

        centerContent.getChildren().add(loadTrajectorySelection());

        mainLayout.setCenter(centerContent);
    }

    private Parent loadTrajectorySelection() {
        try {
            return FXMLLoader.load(DashboardView.class.getResource("/com/example/demo/view/trajectory-selection-view.fxml"));
        } catch (IOException e) {
            throw new IllegalStateException("Het laden van de trajectselectieweergave is mislukt.", e);
        }
    }

    public BorderPane getRoot() {
        return mainLayout;
    }
}
