package com.example.demo;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import views.TrajectorySelectionView;
import views.TransportTypeBox;

public class DashboardView {
    private final BorderPane mainLayout;

    public DashboardView() {
        this.mainLayout = new BorderPane();
        this.mainLayout.setPadding(new Insets(20));

        // Modules aanmaken, NIEUWE KNOP HIERBIJ
        TransportTypeBox transportSection = new TransportTypeBox();
        TrajectorySelectionView trajectorySection = new TrajectorySelectionView();

        // Onderdelen in het dashboard plaatsen
        mainLayout.setTop(transportSection);
        mainLayout.setCenter(trajectorySection.getView());

        // Marges instellen voor ruimte tussen de modules
        BorderPane.setMargin(transportSection, new Insets(0, 0, 20, 0));
    }

    public BorderPane getRoot() {
        return mainLayout;
    }
}