package fr.balen.paul.models;

public enum Item {

    BOIS("Bois", "Matière première", 5.0),
    PIERRE("Pierre", "Matière première", 3.0),
    FER("Minerai de fer", "Matière première", 15.0),
    CHARBON("Charbon", "Combustible", 8.0),
    OR("Minerai d'or", "Matière première", 50.0),
    CUIVRE("Minerai de cuivre", "Matière première", 10.0),
    SABLE("Sable", "Matière première", 2.0),
    ARGILE("Argile", "Matière première", 4.0),

    PLANCHE("Planche de bois", "Matériau transformé", 8.0),
    LINGOT_FER("Lingot de fer", "Matériau transformé", 25.0),
    LINGOT_OR("Lingot d'or", "Matériau transformé", 80.0),
    LINGOT_CUIVRE("Lingot de cuivre", "Matériau transformé", 18.0),
    VERRE("Verre", "Matériau transformé", 12.0),
    BRIQUE("Brique", "Matériau transformé", 9.0),

    HACHE("Hache", "Outil", 40.0),
    PIOCHE("Pioche", "Outil", 45.0),
    PELLE("Pelle", "Outil", 30.0),
    EPEE("Épée", "Équipement", 60.0),

    BLE("Blé", "Agriculture", 3.0),
    PAIN("Pain", "Nourriture", 10.0),
    POMME("Pomme", "Nourriture", 5.0),
    VIANDE("Viande", "Nourriture", 15.0);

    private final String nom;
    private final String categorie;
    private final double prixBase;

    Item(String nom, String categorie, double prixBase) {
        this.nom = nom;
        this.categorie = categorie;
        this.prixBase = prixBase;
    }

    public String getNom() {
        return nom;
    }

    public String getCategorie() {
        return categorie;
    }

    public double getPrixBase() {
        return prixBase;
    }

    @Override
    public String toString() {
        return nom + " (" + categorie + " - " + prixBase + " €)";
    }
}
