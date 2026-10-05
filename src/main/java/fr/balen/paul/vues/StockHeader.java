package fr.balen.paul.vues;

import fr.balen.paul.models.StatusDBModel;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class StockHeader extends VBox {

    private final Label lblStatus;
    private final Label lblDetails;

    public StockHeader(Runnable onTesterConnexion) {
        super(8);
        setPadding(new Insets(15, 20, 15, 20));
        setStyle("-fx-background-color: #ffffff; -fx-border-color: #e2e8f0; -fx-border-width: 0 0 1 0;");

        HBox topRow = new HBox(15);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label lblTitle = new Label("StockMania");
        lblTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        lblStatus = new Label("● En attente");
        lblStatus.setStyle("-fx-background-color: #e2e8f0; -fx-text-fill: #475569; -fx-padding: 6 12; -fx-background-radius: 12; -fx-font-weight: bold;");

        Button btnTest = new Button("Tester DB");
        btnTest.setStyle("-fx-background-color: #3b82f6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6 14; -fx-background-radius: 6;");
        btnTest.setOnAction(e -> onTesterConnexion.run());

        topRow.getChildren().addAll(lblTitle, spacer, lblStatus, btnTest);

        lblDetails = new Label("");
        lblDetails.setStyle("-fx-font-size: 11px; -fx-text-fill: #64748b;");

        getChildren().addAll(topRow, lblDetails);
    }

    public void updateStatus(StatusDBModel model) {
        Platform.runLater(() -> {
            if (model.isConnected()) {
                lblStatus.setText("● Connecté");
                lblStatus.setStyle("-fx-background-color: #dcfce7; -fx-text-fill: #15803d; -fx-padding: 6 12; -fx-background-radius: 12; -fx-font-weight: bold;");
            } else {
                lblStatus.setText("● Déconnecté");
                lblStatus.setStyle("-fx-background-color: #fee2e2; -fx-text-fill: #b91c1c; -fx-padding: 6 12; -fx-background-radius: 12; -fx-font-weight: bold;");
            }
            lblDetails.setText(model.getStatusMessage());
        });
    }
}
