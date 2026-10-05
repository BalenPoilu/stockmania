package fr.balen.paul.vues;

import fr.balen.paul.dao.ItemDAO;
import fr.balen.paul.models.ItemStock;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class StockTableView extends TableView<ItemStock> {

    private final ObservableList<ItemStock> masterData = FXCollections.observableArrayList();
    private FilteredList<ItemStock> filteredData;
    private Runnable onDataChanged;

    public StockTableView() {
        setStyle("-fx-background-radius: 8; -fx-border-color: #cbd5e1; -fx-border-radius: 8;");
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        configurerColonnes();
    }

    private void configurerColonnes() {
        TableColumn<ItemStock, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getId()).asObject());
        colId.setPrefWidth(60);

        TableColumn<ItemStock, String> colCode = new TableColumn<>("Code");
        colCode.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getItem() != null ? c.getValue().getItem().name() : ""));
        colCode.setPrefWidth(110);

        TableColumn<ItemStock, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNom()));
        colNom.setPrefWidth(160);

        TableColumn<ItemStock, String> colCat = new TableColumn<>("Catégorie");
        colCat.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCategorie()));
        colCat.setPrefWidth(150);

        TableColumn<ItemStock, Double> colPrix = new TableColumn<>("Prix (€)");
        colPrix.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getPrix()).asObject());
        colPrix.setPrefWidth(90);

        TableColumn<ItemStock, Integer> colQte = new TableColumn<>("Quantité");
        colQte.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getQuantite()).asObject());
        colQte.setPrefWidth(90);

        getColumns().addAll(List.of(colId, colCode, colNom, colCat, colPrix, colQte));
    }

    public void setOnDataChanged(Runnable onDataChanged) {
        this.onDataChanged = onDataChanged;
    }

    public void chargerDonnees() {
        masterData.setAll(ItemDAO.recupererTous());
        if (filteredData == null) {
            filteredData = new FilteredList<>(masterData, p -> true);
            setItems(filteredData);
        }
        notifierChangement();
    }

    public void appliquerFiltre(String motCle, String categorie) {
        if (filteredData == null) return;
        String query = motCle != null ? motCle.trim().toLowerCase() : "";

        filteredData.setPredicate(item -> {
            boolean matchTexte = query.isEmpty()
                    || item.getNom().toLowerCase().contains(query)
                    || (item.getItem() != null && item.getItem().name().toLowerCase().contains(query));

            boolean matchCat = categorie == null
                    || categorie.equals("Toutes les catégories")
                    || item.getCategorie().equalsIgnoreCase(categorie);

            return matchTexte && matchCat;
        });
        notifierChangement();
    }

    public void modifierStockSelectionne(int delta) {
        ItemStock selection = getSelectionModel().getSelectedItem();
        if (selection == null) {
            afficherAlerte("Sélection requise", "Veuillez sélectionner un item dans le tableau.");
            return;
        }

        int nouvelleQte = selection.getQuantite() + delta;
        if (nouvelleQte < 0) {
            afficherAlerte("Stock insuffisant", "La quantité ne peut pas être négative.");
            return;
        }

        if (ItemDAO.mettreAJourQuantite(selection.getItem(), nouvelleQte)) {
            selection.setQuantite(nouvelleQte);
            refresh();
            notifierChangement();
        }
    }

    public int getNombreItemsVisibles() {
        return filteredData != null ? filteredData.size() : 0;
    }

    public double getValeurTotale() {
        if (filteredData == null) return 0.0;
        return filteredData.stream()
                .mapToDouble(i -> i.getPrix() * i.getQuantite())
                .sum();
    }

    private void notifierChangement() {
        if (onDataChanged != null) {
            onDataChanged.run();
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
