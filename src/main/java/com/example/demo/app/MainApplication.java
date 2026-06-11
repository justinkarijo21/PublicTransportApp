package com.example.demo.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.demo.view.LoginView;
import com.example.demo.view.LoginFormView;
import com.example.demo.view.DashboardView;

import java.util.Objects;

public class MainApplication extends Application {

    private Stage stage;
    private Scene loginScene;
    private Scene loginFormScene;
    private Scene dashboardScene;

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        // Create login view (inlogopties scherm)
        createLoginScene();

        stage.setTitle("Reis Planner");
        stage.setScene(loginScene);
        stage.show();
    }

    private void createLoginScene() {
        LoginView loginView = new LoginView();
        loginScene = new Scene(loginView.getRoot(), 1024, 760);
        loginScene.getStylesheets().add(Objects.requireNonNull(
                MainApplication.class.getResource("/com/example/demo/view/app.css")
        ).toExternalForm());

        // Show login form when user clicks "Inloggen als Gebruiker"
        loginView.setOnShowLoginForm(() -> showLoginForm());

        // Go to dashboard after successful guest login
        loginView.setOnLoginSuccess(() -> showDashboard());
    }

    private void showLoginForm() {
        // Create login form view
        LoginFormView loginFormView = new LoginFormView();
        loginFormScene = new Scene(loginFormView.getRoot(), 1024, 760);
        loginFormScene.getStylesheets().add(Objects.requireNonNull(
                MainApplication.class.getResource("/com/example/demo/view/app.css")
        ).toExternalForm());

        // Go to dashboard after login
        loginFormView.setOnLoginSuccess(() -> showDashboard());

        // Go back to login view when back button is clicked
        loginFormView.setOnBackClick(() -> {
            createLoginScene();
            stage.setScene(loginScene);
        });

        stage.setScene(loginFormScene);
    }

    private void showDashboard() {
        // Create dashboard after login
        DashboardView dashboardView = new DashboardView();
        dashboardScene = new Scene(dashboardView.getRoot(), 1024, 760);
        dashboardScene.getStylesheets().add(Objects.requireNonNull(
                MainApplication.class.getResource("/com/example/demo/view/app.css")
        ).toExternalForm());

        // Logout button handler - go back to login
        dashboardView.setOnLogout(() -> {
            createLoginScene();
            stage.setScene(loginScene);
        });

        stage.setScene(dashboardScene);
    }
}
