package com.example.demo.view;

import com.example.demo.view.TransportTypeBox;
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
        this.mainLayout.setPadding(new Insets(20));


        // Create modules
        TransportTypeBox transportSection = new TransportTypeBox();

        // Place modules on the dashboard
        mainLayout.setTop(transportSection);
        //mainLayout.setCenter(loadTrajectorySelection());
        VBox centerContent = new VBox();
        centerContent.setSpacing(20);

        centerContent.getChildren().addAll(
                loadTrajectorySelection(),
                new AssistanceToggle()
        );

        mainLayout.setCenter(centerContent);

        // Add spacing between modules
        BorderPane.setMargin(transportSection, new Insets(0, 0, 20, 0));
    }


        mainLayout.setCenter(loadTrajectorySelection());
    } // <-- Added closing brace for the constructor


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