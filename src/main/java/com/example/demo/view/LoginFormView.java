package com.example.demo.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import com.example.demo.model.UserSession;

public class LoginFormView {

    private final BorderPane mainLayout;
    private Runnable onLoginSuccess;
    private Runnable onBackClick;

    public LoginFormView() {
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
        Label subtitleLabel = new Label("Gebruiker Inloggen");
        subtitleLabel.getStyleClass().add("section-title");

        // Form container
        VBox formContainer = new VBox(15);
        formContainer.setAlignment(Pos.TOP_CENTER);
        formContainer.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12; -fx-border-color: #dce7dd; -fx-border-radius: 12; -fx-padding: 40; -fx-effect: dropshadow(gaussian, rgba(39, 67, 48, 0.12), 18, 0.16, 0, 5);");
        formContainer.setPrefWidth(400);

        // Username label
        Label usernameLabel = new Label("Gebruikersnaam:");
        usernameLabel.getStyleClass().add("field-label");

        // Username field
        TextField usernameField = new TextField();
        usernameField.setPromptText("Voer gebruikersnaam in");
        usernameField.getStyleClass().add("search-field");
        usernameField.setPrefHeight(46);

        // Password label
        Label passwordLabel = new Label("Wachtwoord:");
        passwordLabel.getStyleClass().add("field-label");

        // Password field
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Voer wachtwoord in");
        passwordField.getStyleClass().add("search-field");
        passwordField.setPrefHeight(46);

        // Buttons container
        VBox buttonsBox = new VBox(10);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setPadding(new Insets(20, 0, 0, 0));

        // Login button
        Button loginButton = new Button("Inloggen");
        loginButton.getStyleClass().add("primary-button");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.setPrefHeight(46);
        loginButton.setStyle("-fx-font-size: 14px;");
        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            // Alles is goed - geen verificatie nodig
            if (!username.isEmpty() || !password.isEmpty()) {
                handleLogin();
            }
        });

        // Back button
        Button backButton = new Button("Terug");
        backButton.getStyleClass().add("primary-button");
        backButton.setMaxWidth(Double.MAX_VALUE);
        backButton.setPrefHeight(46);
        backButton.setStyle("-fx-font-size: 14px; -fx-background-color: #6b8a7e;");
        backButton.setOnAction(e -> {
            if (onBackClick != null) {
                onBackClick.run();
            }
        });

        buttonsBox.getChildren().addAll(loginButton, backButton);

        // Add all to form
        formContainer.getChildren().addAll(
                usernameLabel, usernameField,
                passwordLabel, passwordField,
                buttonsBox
        );

        centerBox.getChildren().addAll(titleLabel, subtitleLabel, formContainer);
        mainLayout.setCenter(centerBox);
    }

    private void handleLogin() {
        UserSession.getInstance().login(UserSession.UserType.USER);
        if (onLoginSuccess != null) {
            onLoginSuccess.run();
        }
    }

    public void setOnLoginSuccess(Runnable callback) {
        this.onLoginSuccess = callback;
    }

    public void setOnBackClick(Runnable callback) {
        this.onBackClick = callback;
    }

    public Parent getRoot() {
        return mainLayout;
    }
}

