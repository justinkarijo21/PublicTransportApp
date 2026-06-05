package com.example.demo.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.demo.view.DashboardView;

import java.util.Objects;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) {
        DashboardView startScreen = new DashboardView();

        Scene scene = new Scene(startScreen.getRoot(), 1024, 760);
        scene.getStylesheets().add(Objects.requireNonNull(
                MainApplication.class.getResource("/com/example/demo/view/app.css")

        ).toExternalForm());
        stage.setTitle("Reis Planner");
        stage.setScene(scene);
        stage.show();
    }
}
