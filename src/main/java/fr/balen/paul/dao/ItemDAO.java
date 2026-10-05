package fr.balen.paul.dao;

import fr.balen.paul.database.DatabaseHelper;
import fr.balen.paul.database.RowMapper;
import fr.balen.paul.models.Item;
import fr.balen.paul.models.ItemStock;

import java.util.List;
import java.util.Optional;

public class ItemDAO {

    public static final RowMapper<ItemStock> ITEM_ROW_MAPPER = rs -> {
        int id = rs.getInt("id");
        String code = rs.getString("code");
        String nom = rs.getString("nom");
        String categorie = rs.getString("categorie");
        double prix = rs.getDouble("prix");
        int quantite = rs.getInt("quantite");

        Item itemEnum = null;
        try {
            itemEnum = Item.valueOf(code);
        } catch (IllegalArgumentException ignored) {
        }

        return new ItemStock(id, itemEnum, nom, categorie, prix, quantite);
    };

    public static void initialiserTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS items (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                code TEXT NOT NULL UNIQUE,
                nom TEXT NOT NULL,
                categorie TEXT NOT NULL,
                prix REAL NOT NULL,
                quantite INTEGER NOT NULL DEFAULT 0
            );
            """;
        DatabaseHelper.executeUpdate(sql);
    }

    public static void initialiserDonneesSiVide() {
        initialiserTable();
        List<ItemStock> existants = recupererTous();
        if (existants.isEmpty()) {
            String insertSql = "INSERT INTO items (code, nom, categorie, prix, quantite) VALUES (?, ?, ?, ?, ?)";
            for (Item item : Item.values()) {
                DatabaseHelper.executeUpdate(insertSql,
                        item.name(),
                        item.getNom(),
                        item.getCategorie(),
                        item.getPrixBase(),
                        10
                );
            }
            System.out.println("[ItemDAO] Base initialisée avec " + Item.values().length + " items par défaut.");
        }
    }

    public static List<ItemStock> recupererTous() {
        String sql = "SELECT * FROM items ORDER BY id ASC";
        return DatabaseHelper.executeQuery(sql, ITEM_ROW_MAPPER);
    }

    public static Optional<ItemStock> recupererParId(int id) {
        String sql = "SELECT * FROM items WHERE id = ?";
        return DatabaseHelper.executeQuerySingle(sql, ITEM_ROW_MAPPER, id);
    }

    public static Optional<ItemStock> recupererParItem(Item item) {
        if (item == null) return Optional.empty();
        return recupererParCode(item.name());
    }

    public static Optional<ItemStock> recupererParCode(String code) {
        String sql = "SELECT * FROM items WHERE code = ?";
        return DatabaseHelper.executeQuerySingle(sql, ITEM_ROW_MAPPER, code);
    }

    public static List<ItemStock> recupererParCategorie(String categorie) {
        String sql = "SELECT * FROM items WHERE categorie = ? ORDER BY nom ASC";
        return DatabaseHelper.executeQuery(sql, ITEM_ROW_MAPPER, categorie);
    }

    public static List<ItemStock> recupererEnStock() {
        String sql = "SELECT * FROM items WHERE quantite > 0 ORDER BY nom ASC";
        return DatabaseHelper.executeQuery(sql, ITEM_ROW_MAPPER);
    }

    public static List<ItemStock> recupererParCritere(String colonne, Object valeur) {
        if (!colonne.matches("^[a-zA-Z_]+$")) {
            throw new IllegalArgumentException("Nom de colonne invalide : " + colonne);
        }
        String sql = "SELECT * FROM items WHERE " + colonne + " = ?";
        return DatabaseHelper.executeQuery(sql, ITEM_ROW_MAPPER, valeur);
    }

    public static List<ItemStock> recupererAvecClause(String whereClause, Object... params) {
        String sql = "SELECT * FROM items WHERE " + whereClause;
        return DatabaseHelper.executeQuery(sql, ITEM_ROW_MAPPER, params);
    }

    public static boolean mettreAJourQuantite(Item item, int nouvelleQuantite) {
        if (item == null) return false;
        String sql = "UPDATE items SET quantite = ? WHERE code = ?";
        return DatabaseHelper.executeUpdate(sql, nouvelleQuantite, item.name()) > 0;
    }

    public static boolean modifierStock(Item item, int delta) {
        if (item == null) return false;
        String sql = "UPDATE items SET quantite = quantite + ? WHERE code = ?";
        return DatabaseHelper.executeUpdate(sql, delta, item.name()) > 0;
    }
}
