package tdfinal.base;

public class Utilisateur {
    private String pseudo;
    private String mdp;
    private int id;

public Utilisateur(String pseudo, String mdp, int id){
    this.pseudo=pseudo;
    this.mdp=mdp;
    this.id=id;
}
public String getPseudo() {
        return pseudo;
    }

    public String getMdp() {
        return mdp;
    }
     public int getId() {
        return id;
    }

}
