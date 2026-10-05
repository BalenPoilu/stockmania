package fr.balen.paul.models;

public class ItemStock {

    private int id;
    private Item item;
    private String nom;
    private String categorie;
    private double prix;
    private int quantite;

    public ItemStock() {
    }

    public ItemStock(int id, Item item, String nom, String categorie, double prix, int quantite) {
        this.id = id;
        this.item = item;
        this.nom = nom;
        this.categorie = categorie;
        this.prix = prix;
        this.quantite = quantite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return String.format("ItemStock[id=%d, code=%s, nom='%s', cat='%s', prix=%.2f€, qte=%d]",
                id, item != null ? item.name() : "INCONNU", nom, categorie, prix, quantite);
    }
}
