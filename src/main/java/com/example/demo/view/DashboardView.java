package com.example.demo.view;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.geometry.Pos;

import java.io.IOException;

public class DashboardView {

    private final BorderPane mainLayout;
    private boolean menuOpen = false;

    public DashboardView() {
        this.mainLayout = new BorderPane();
        this.mainLayout.setPadding(new Insets(32));
        this.mainLayout.getStyleClass().add("app-root");

        // ---------------------------
        // 1. Bovenbalk met hamburger RECHTS
        // ---------------------------
        Node hamburger = maakHamburgerIcoon();

        HBox bovenbalk = new HBox();
        bovenbalk.setPadding(new Insets(10));
        bovenbalk.setStyle("-fx-background-color: #21f3955a;");
        bovenbalk.setAlignment(Pos.CENTER_LEFT); // <-- streepjes links, rest van de ruimte rechts
        bovenbalk.getChildren().add(hamburger);

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
    // Hamburger icoon (drie streepjes)
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
    // Zijmenu (groen + Home + Favorieten)
    // ---------------------------
    private VBox maakZijMenu() {
        VBox menu = new VBox(15);
        menu.setPadding(new Insets(20));
        menu.setStyle("-fx-background-color: #25845e5f;"); // <-- GROEN menu
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

    public BorderPane getRoot() {
        return mainLayout;
    }
}
