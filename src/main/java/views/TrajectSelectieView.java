package views;



import com.example.demo.AppFactory;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Trajectory;

public class TrajectSelectieView {

    public void start(Stage stage) {

        Label title = new Label("Kies een traject");
        Label tijdenLabel = new Label("Beschikbare tijden:");

        ComboBox<Trajectory> trajectBox =
                new ComboBox<>(FXCollections.observableArrayList(AppFactory.getTrajecten()));

        trajectBox.setPromptText("Selecteer een traject");

        ListView<String> tijdenList = new ListView<>();
        tijdenList.setPrefHeight(200);

        trajectBox.setOnAction(e -> {
            Trajectory gekozen = trajectBox.getValue();
            if (gekozen != null) {
                tijdenList.setItems(FXCollections.observableArrayList(gekozen.getTijden()));
            }
        });

        VBox root = new VBox(20, title, trajectBox, tijdenLabel, tijdenList);
        root.setPadding(new Insets(200,200,200,200));

        stage.setScene(new Scene(root, 1000, 600));
        stage.setTitle("Traject kiezen");
        stage.show();
    }
}