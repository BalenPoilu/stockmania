package fr.balen.paul.vues;

import fr.balen.paul.models.Item;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class StockToolbar extends HBox {

    private final TextField txtRecherche;
    private final ComboBox<String> cmbCategorie;
    private final TextField txtValeur;

    public StockToolbar(StockTableView tableView) {
        super(10);
        setAlignment(Pos.CENTER_LEFT);

        txtRecherche = new TextField();
        txtRecherche.setPromptText("Rechercher un item...");
        txtRecherche.setPrefWidth(190);
        txtRecherche.setStyle("-fx-padding: 7 10; -fx-background-radius: 6; -fx-border-color: #cbd5e1; -fx-border-radius: 6;");

        cmbCategorie = new ComboBox<>();
        cmbCategorie.getItems().add("Toutes les catégories");
        for (Item item : Item.values()) {
            if (!cmbCategorie.getItems().contains(item.getCategorie())) {
                cmbCategorie.getItems().add(item.getCategorie());
            }
        }
        cmbCategorie.setValue("Toutes les catégories");
        cmbCategorie.setStyle("-fx-padding: 4 8; -fx-background-radius: 6; -fx-border-color: #cbd5e1; -fx-border-radius: 6;");

        txtRecherche.textProperty().addListener((obs, o, n) -> declencherFiltre(tableView));
        cmbCategorie.setOnAction(e -> declencherFiltre(tableView));

        Button btnRefresh = new Button("Actualiser");
        btnRefresh.setStyle("-fx-background-color: #0ea5e9; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 7 12; -fx-background-radius: 6; -fx-cursor: hand;");
        btnRefresh.setOnAction(e -> tableView.chargerDonnees());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnMoins1 = new Button("-1");
        btnMoins1.setStyle("-fx-background-color: #f87171; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 7 10; -fx-background-radius: 6; -fx-cursor: hand;");
        btnMoins1.setOnAction(e -> tableView.modifierStockSelectionne(-1));

        Button btnPlus1 = new Button("+1");
        btnPlus1.setStyle("-fx-background-color: #34d399; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 7 10; -fx-background-radius: 6; -fx-cursor: hand;");
        btnPlus1.setOnAction(e -> tableView.modifierStockSelectionne(1));

        txtValeur = new TextField();
        txtValeur.setPromptText("Qté");
        txtValeur.setPrefWidth(60);
        txtValeur.setStyle("-fx-padding: 7 8; -fx-background-radius: 6; -fx-border-color: #cbd5e1; -fx-border-radius: 6;");
        txtValeur.textProperty().addListener((obs, oldV, newV) -> {
            if (!newV.matches("\\d*")) {
                txtValeur.setText(newV.replaceAll("[^\\d]", ""));
            }
        });

        Button btnAjouter = new Button("Ajouter");
        btnAjouter.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 7 12; -fx-background-radius: 6; -fx-cursor: hand;");
        btnAjouter.setOnAction(e -> appliquerChiffre(tableView, 1));

        Button btnRetirer = new Button("Retirer");
        btnRetirer.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 7 12; -fx-background-radius: 6; -fx-cursor: hand;");
        btnRetirer.setOnAction(e -> appliquerChiffre(tableView, -1));

        getChildren().addAll(txtRecherche, cmbCategorie, btnRefresh, spacer, btnMoins1, btnPlus1, txtValeur, btnAjouter, btnRetirer);
    }

    private void declencherFiltre(StockTableView tableView) {
        tableView.appliquerFiltre(txtRecherche.getText(), cmbCategorie.getValue());
    }

    private void appliquerChiffre(StockTableView tableView, int signe) {
        String texte = txtValeur.getText().trim();
        if (texte.isEmpty()) {
            afficherAlerte("Valeur manquante", "Veuillez saisir un nombre dans le champ quantité.");
            return;
        }

        try {
            int qte = Integer.parseInt(texte);
            if (qte <= 0) {
                afficherAlerte("Valeur invalide", "Veuillez saisir un nombre strictement positif.");
                return;
            }
            tableView.modifierStockSelectionne(signe * qte);
        } catch (NumberFormatException ex) {
            txtValeur.clear();
        }
    }

    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
