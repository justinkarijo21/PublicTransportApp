package com.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.demo.Launcher;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) {
        DashboardView startScreen = new DashboardView();

        Scene scene = new Scene(startScreen.getRoot(), 800, 600);
        stage.setTitle("OV Planner Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}