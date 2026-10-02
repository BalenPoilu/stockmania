package fr.balen.paul.controles;

import fr.balen.paul.models.IModels;
import fr.balen.paul.vues.IVues;

import java.util.ArrayList;

public class StatusDBControle implements IControles{

    ArrayList<IVues> vues;
    StatusDBControle model;

    @Override
    public void addIvues(IVues vue) {
        vues.add(vue);
    }

    @Override
    public void removeIvues(IVues vue) {
        vues.remove(vue);
    }

    @Override
    public void notifyVues(IModels model) {
        for (IVues vue : vues){
            vue.update(model);
        }
    }
}
