package com.example.demo.view;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import com.example.demo.model.UserSession;

import java.io.IOException;

public class DashboardView {

    private final BorderPane mainLayout;
    private boolean menuOpen = false;
    private Runnable onLogout;

    public DashboardView() {
        this.mainLayout = new BorderPane();
        this.mainLayout.setPadding(new Insets(32));
        this.mainLayout.getStyleClass().add("app-root");

        // ---------------------------
        // 1. Bovenbalk met hamburger (ALLEEN voor Gebruikers) en logout icoontje
        // ---------------------------
        HBox bovenbalk = new HBox();
        bovenbalk.setPadding(new Insets(10));
        bovenbalk.setStyle("-fx-background-color: #25845e5f;");
        bovenbalk.setAlignment(Pos.CENTER_LEFT);

        // Hamburger ALLEEN toevoegen als gebruiker (niet gast)
        if (UserSession.getInstance().isUser()) {
            Node hamburger = maakHamburgerIcoon();

            // ---------------------------
            // 2. Zijmenu (rechts, GROEN)
            // ---------------------------
            VBox zijMenu = maakZijMenu();

            hamburger.setOnMouseClicked(e -> {
                if (menuOpen) {
                    mainLayout.setLeft(null);
                } else {
                    mainLayout.setLeft(zijMenu);
                }
                menuOpen = !menuOpen;
            });

            bovenbalk.getChildren().add(hamburger);
        }

        // Logout icoontje (zichtbaar voor iedereen)
        Node logoutIcon = maakLogoutIcoon();

        // Spacer om logout rechts te plaatsen
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        bovenbalk.getChildren().addAll(spacer, logoutIcon);

        // Logout button handler
        logoutIcon.setOnMouseClicked(e -> {
            UserSession.getInstance().logout();
            if (onLogout != null) {
                onLogout.run();
            }
        });

        mainLayout.setTop(bovenbalk);

        // ---------------------------
        // 3. Center content (jouw FXML)
        // ---------------------------
        VBox centerContent = new VBox();
        centerContent.setSpacing(20);
        centerContent.getStyleClass().add("planner-shell");

        centerContent.getChildren().add(loadTrajectorySelection());

        mainLayout.setCenter(centerContent);
    }

    // ---------------------------
    // Hamburger icoon (drie streepjes LINKS)
    // ---------------------------
    private Node maakHamburgerIcoon() {
        Rectangle streep1 = new Rectangle(25, 3);
        Rectangle streep2 = new Rectangle(25, 3);
        Rectangle streep3 = new Rectangle(25, 3);

        streep1.setArcHeight(2);
        streep2.setArcHeight(2);
        streep3.setArcHeight(2);

        VBox icoon = new VBox(5, streep1, streep2, streep3);
        icoon.setPadding(new Insets(10));
        icoon.setCursor(Cursor.HAND);

        return icoon;
    }

    // ---------------------------
    // Logout icoontje (cirkel met pijltje RECHTS)
    // ---------------------------
    private Node maakLogoutIcoon() {
        // Text/icoontje (exit symbool)
        Text logoutText = new Text("⤴");
        logoutText.setStyle("-fx-font-size: 20px; -fx-fill: #405348; -fx-font-weight: bold;");

        // Container voor logout icoontje
        VBox logoutIcon = new VBox();
        logoutIcon.setAlignment(Pos.CENTER);
        logoutIcon.getChildren().add(logoutText);
        logoutIcon.setPrefWidth(36);
        logoutIcon.setPrefHeight(36);
        logoutIcon.setStyle("-fx-background-color: transparent;");
        logoutIcon.setPadding(new Insets(10));
        logoutIcon.setCursor(Cursor.HAND);

        return logoutIcon;
    }

    // ---------------------------
    // Zijmenu (groen + Home + Favorieten)
    // ---------------------------
    private VBox maakZijMenu() {
        VBox menu = new VBox(15);
        menu.setPadding(new Insets(20));
        menu.setStyle("-fx-background-color: #25845e5f;");
        menu.setPrefWidth(180);

        Button knopHome = new Button("Home");
        Button knopFavorieten = new Button("Favorieten");

        menu.getChildren().addAll(knopHome, knopFavorieten);

        return menu;
    }

    // ---------------------------
    // FXML loader
    // ---------------------------
    private Parent loadTrajectorySelection() {
        try {
            return FXMLLoader.load(DashboardView.class.getResource(
                    "/com/example/demo/view/trajectory-selection-view.fxml"));
        } catch (IOException e) {
            throw new IllegalStateException("Het laden van de trajectselectieweergave is mislukt.", e);
        }
    }

    public void setOnLogout(Runnable callback) {
        this.onLogout = callback;
    }

    public BorderPane getRoot() {
        return mainLayout;
    }
}
