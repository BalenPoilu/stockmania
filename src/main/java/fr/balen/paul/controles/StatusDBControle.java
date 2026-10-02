package fr.balen.paul.controles;

import fr.balen.paul.models.StatusDBModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class StatusDBControle implements IControle {

    private StatusDBModel model;

    public StatusDBControle(StatusDBModel model) {
        this.model = model;
    }

    public StatusDBModel getModel() {
        return model;
    }

    public void setModel(StatusDBModel model) {
        this.model = model;
    }

    @Override
    public void verifierConnexion() {
        if (model == null) {
            return;
        }

        String dbUrl = "jdbc:sqlite:stockmania.db";
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
            if (conn != null) {
                model.setConnected(true);
                model.setStatusMessage("connécté (" + dbUrl + ")");
            }
        } catch (SQLException e) {
            model.setConnected(false);
            model.setStatusMessage("Erreur : " + e.getMessage());
        }
    }
}