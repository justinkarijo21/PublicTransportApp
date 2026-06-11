package com.example.demo.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import com.example.demo.model.UserSession;

public class LoginView {

    private final BorderPane mainLayout;
    private Runnable onLoginSuccess;
    private Runnable onShowLoginForm;

    public LoginView() {
        this.mainLayout = new BorderPane();
        this.mainLayout.setPadding(new Insets(32));
        this.mainLayout.getStyleClass().add("app-root");

        // Center layout
        VBox centerBox = new VBox(30);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(40));

        // Title
        Label titleLabel = new Label("Reis Planner");
        titleLabel.getStyleClass().add("app-title");

        // Subtitle
        Label subtitleLabel = new Label("Inloggen");
        subtitleLabel.getStyleClass().add("section-title");

        // Login buttons container
        VBox buttonsContainer = new VBox(15);
        buttonsContainer.setAlignment(Pos.CENTER);
        buttonsContainer.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12; -fx-border-color: #dce7dd; -fx-border-radius: 12; -fx-padding: 40; -fx-effect: dropshadow(gaussian, rgba(39, 67, 48, 0.12), 18, 0.16, 0, 5);");
        buttonsContainer.setPrefWidth(350);

        // User login button
        Button userButton = new Button("Inloggen als Gebruiker");
        userButton.getStyleClass().add("primary-button");
        userButton.setMaxWidth(Double.MAX_VALUE);
        userButton.setPrefHeight(46);
        userButton.setStyle("-fx-font-size: 14px;");
        userButton.setOnAction(e -> {
            if (onShowLoginForm != null) {
                onShowLoginForm.run();
            }
        });

        // Guest login button
        Button guestButton = new Button("Doorgaan als Gast");
        guestButton.getStyleClass().add("primary-button");
        guestButton.setMaxWidth(Double.MAX_VALUE);
        guestButton.setPrefHeight(46);
        guestButton.setStyle("-fx-font-size: 14px;");
        guestButton.setOnAction(e -> handleGuestLogin());

        buttonsContainer.getChildren().addAll(userButton, guestButton);

        centerBox.getChildren().addAll(titleLabel, subtitleLabel, buttonsContainer);
        mainLayout.setCenter(centerBox);
    }

    private void handleGuestLogin() {
        UserSession.getInstance().login(UserSession.UserType.GUEST);
        if (onLoginSuccess != null) {
            onLoginSuccess.run();
        }
    }

    public void setOnLoginSuccess(Runnable callback) {
        this.onLoginSuccess = callback;
    }

    public void setOnShowLoginForm(Runnable callback) {
        this.onShowLoginForm = callback;
    }

    public Parent getRoot() {
        return mainLayout;
    }
}
