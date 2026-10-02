package fr.balen.paul.models;

import fr.balen.paul.vues.IVue;

public interface IModel {
    void addVue(IVue vue);
    void removeVue(IVue vue);
    void notifyVues();
}