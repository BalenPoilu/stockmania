package fr.balen.paul.models;

import fr.balen.paul.vues.IVue;

import java.util.ArrayList;
import java.util.List;

public class StatusDBModel implements IModel {

    private final List<IVue> vues = new ArrayList<>();
    private boolean isConnected;
    private String statusMessage = "Déconnecté";

    public StatusDBModel() {
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean isConnected) {
        this.isConnected = isConnected;
        notifyVues();
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
        notifyVues();
    }

    @Override
    public void addVue(IVue vue) {
        if (vue != null && !vues.contains(vue)) {
            vues.add(vue);
        }
    }

    @Override
    public void removeVue(IVue vue) {
        vues.remove(vue);
    }

    @Override
    public void notifyVues() {
        for (IVue vue : vues) {
            vue.update(this);
        }
    }
}