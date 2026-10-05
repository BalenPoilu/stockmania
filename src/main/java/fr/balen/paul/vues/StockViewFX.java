package fr.balen.paul.vues;

import fr.balen.paul.controles.IControle;
import fr.balen.paul.controles.StatusDBControle;
import fr.balen.paul.dao.ItemDAO;
import fr.balen.paul.models.IModel;
import fr.balen.paul.models.StatusDBModel;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StockViewFX extends Application implements IVue {

    private StatusDBModel statusModel;
    private IControle controle;
    private StockHeader header;
    private StockTableView tableView;
    private StockFooter footer;

    @Override
    public void start(Stage primaryStage) {
        statusModel = new StatusDBModel();
        statusModel.addVue(this);
        controle = new StatusDBControle(statusModel);

        ItemDAO.initialiserDonneesSiVide();

        header = new StockHeader(controle::verifierConnexion);
        tableView = new StockTableView();
        StockToolbar toolbar = new StockToolbar(tableView);
        footer = new StockFooter();

        tableView.setOnDataChanged(() -> footer.rafraichir(tableView.getNombreItemsVisibles(), tableView.getValeurTotale()));

        VBox centre = new VBox(12, toolbar, tableView);
        centre.setPadding(new Insets(20));
        VBox.setVgrow(tableView, Priority.ALWAYS);

        BorderPane root = new BorderPane();
        root.setStyle("-fx-font-family: 'Segoe UI', sans-serif; -fx-background-color: #f4f6f9;");
        root.setTop(header);
        root.setCenter(centre);
        root.setBottom(footer);

        Scene scene = new Scene(root, 980, 650);
        primaryStage.setTitle("StockMania - Gestion de Stock");
        primaryStage.setScene(scene);
        primaryStage.show();

        controle.verifierConnexion();
        tableView.chargerDonnees();
    }

    @Override
    public void update(IModel model) {
        if (model instanceof StatusDBModel dbModel) {
            header.updateStatus(dbModel);
        }
    }
}
