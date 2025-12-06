package TDfinal.base;


public class Livre {
    private String titre;
    private String auteur;
    private int stock;
public void reduireStock(int quantité) {
        this.stock -= quantité;
    }

public Livre(String titre, String auteur, int stock){
    this.titre=titre;
    this.auteur=auteur;
    this.stock=stock;
}
public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public int getStock() {
        return stock;
    }
}

    