package fr.balen.paul;

import fr.balen.paul.controles.IControle;
import fr.balen.paul.controles.StatusDBControle;
import fr.balen.paul.models.StatusDBModel;
import fr.balen.paul.vues.IVue;
import fr.balen.paul.vues.StatusDBVue;

public class Main {
    public static void main(String[] args) {
        System.out.println("test stockmania\n");

        StatusDBModel model = new StatusDBModel();

        IVue vueConsole = new StatusDBVue();


        IVue vueJournal = m -> {
            if (m instanceof StatusDBModel dbModel) {
                System.out.println("[Log] Changement d'état -> message: " + dbModel.getStatusMessage());
            }
        };

        model.addVue(vueConsole);
        model.addVue(vueJournal);

        IControle controle = new StatusDBControle(model);

        System.out.println("connexion :");
        controle.verifierConnexion();

        System.out.println("\ndéconnexion manuelle");
        model.setStatusMessage("Arrêt");
        model.setConnected(false);
    }
}

