package com.example.demo.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.demo.view.DashboardView;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) {
        DashboardView startScreen = new DashboardView();

        Scene scene = new Scene(startScreen.getRoot(), 800, 600);
        stage.setTitle("Reis Planner");
        stage.setScene(scene);
        stage.show();
    }
}
