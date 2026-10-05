package fr.balen.paul;

import fr.balen.paul.controles.IControle;
import fr.balen.paul.controles.StatusDBControle;
import fr.balen.paul.dao.ItemDAO;
import fr.balen.paul.models.Item;
import fr.balen.paul.models.ItemStock;
import fr.balen.paul.models.StatusDBModel;
import fr.balen.paul.vues.IVue;
import fr.balen.paul.vues.StatusDBVue;

import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== StockMania - Test MVC & Queries DB ===\n");

        StatusDBModel model = new StatusDBModel();
        IVue vueConsole = new StatusDBVue();
        model.addVue(vueConsole);

        IControle controle = new StatusDBControle(model);
        System.out.println("--- 1. Vérification connexion DB ---");
        controle.verifierConnexion();

        System.out.println("\n--- 2. Initialisation des données items ---");
        ItemDAO.initialiserDonneesSiVide();

        System.out.println("\n--- 3. Query : Récupérer tous les items ---");
        List<ItemStock> tous = ItemDAO.recupererTous();
        System.out.println("Nombre total d'items trouvés : " + tous.size());
        tous.stream().limit(5).forEach(i -> System.out.println(" -> " + i));
        if (tous.size() > 5) {
            System.out.println(" -> ... et " + (tous.size() - 5) + " autres items.");
        }

        System.out.println("\n--- 4. Query par catégorie ('Outil') ---");
        List<ItemStock> outils = ItemDAO.recupererParCategorie("Outil");
        outils.forEach(o -> System.out.println(" -> " + o));

        System.out.println("\n--- 5. Query pour Item.BOIS ---");
        Optional<ItemStock> boisOpt = ItemDAO.recupererParItem(Item.BOIS);
        boisOpt.ifPresent(bois -> System.out.println(" -> Trouvé : " + bois));

        System.out.println("\n--- 6. Query générique avec clause (prix <= 10€ et en stock) ---");
        List<ItemStock> economiques = ItemDAO.recupererAvecClause("prix <= ? AND quantite > ?", 10.0, 0);
        economiques.forEach(e -> System.out.println(" -> " + e));
    }
}
