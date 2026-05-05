package com.example.demo;

import javafx.application.Application;
import javafx.stage.Stage;
import views.TrajectSelectieView;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) {
        new TrajectSelectieView().start(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}