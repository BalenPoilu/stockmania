package fr.balen.paul.vues;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class StockFooter extends HBox {

    private final Label lblTotalItems;
    private final Label lblValeurTotale;

    public StockFooter() {
        super(20);
        setPadding(new Insets(12, 20, 12, 20));
        setAlignment(Pos.CENTER_LEFT);
        setStyle("-fx-background-color: #ffffff; -fx-border-color: #e2e8f0; -fx-border-width: 1 0 0 0;");

        lblTotalItems = new Label("Total items : 0");
        lblTotalItems.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #334155;");

        lblValeurTotale = new Label("Valeur totale du stock : 0.00 €");
        lblValeurTotale.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #059669;");

        getChildren().addAll(lblTotalItems, lblValeurTotale);
    }

    public void rafraichir(int totalItems, double valeurTotale) {
        lblTotalItems.setText("Total items visibles : " + totalItems);
        lblValeurTotale.setText(String.format("Valeur totale : %.2f €", valeurTotale));
    }
}
