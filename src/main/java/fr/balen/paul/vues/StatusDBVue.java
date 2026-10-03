package fr.balen.paul.vues;

import fr.balen.paul.models.IModel;
import fr.balen.paul.models.StatusDBModel;

public class StatusDBVue implements IVue {

    @Override
    public void update(IModel model) {
        if (model instanceof StatusDBModel dbModel) {
            System.out.println("[StatusDB] : "
                    + (dbModel.isConnected() ? "CONNECTÉ (OK)" : "DÉCONNECTÉ (KO)")
                    + " | Message : " + dbModel.getStatusMessage());
        }
    }
}
