package TDfinal.base;

import java.time.LocalDate;

//import TDfinal.StockInsuffisantException;

public class Emprunt{
        private Livre livre;
    public Livre getLivre() {
    return livre;
}         
    private Utilisateur utilisateur;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;
     public Emprunt(Livre livre,Utilisateur utilisateur, LocalDate dateEmprunt) {
    //this.livre = livre;
    this.utilisateur = utilisateur;
    this.dateEmprunt = dateEmprunt;
    this.dateRetour = null;
}

/*public Livre getLivre() { return livre; }
public void setLivre(Livre livre) { this.livre = livre; }*/

public Utilisateur getUtilisateur() { return utilisateur; }
public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }

public LocalDate getDateEmprunt() { return dateEmprunt; }
public void setDateEmprunt(LocalDate dateEmprunt) { this.dateEmprunt = dateEmprunt; }

public LocalDate getDateRetour() { return dateRetour; }
public void setDateRetour(LocalDate dateRetour) { this.dateRetour = dateRetour; }
}

