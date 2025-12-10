package tdfinal;
import tdfinal.Dao.LivreDAO;
import tdfinal.base.Livre;

public class BibliothequeService {

    private LivreDAO livreDAO = new LivreDAO();

    public void ajouterLivre(Livre livre) {
        livreDAO.add(livre);
        System.out.println("Livre ajouté !");
    }

    public void listerLivres() {
        for (Livre l : livreDAO.getAll()) {
            System.out.println(l);
        }
    }

    // Renvoie la liste des livres (pour l'UI)
    public java.util.List<Livre> getAllLivres() {
        return livreDAO.getAll();
    }

    public void emprunterLivre(int idLivre) {
        Livre livre = livreDAO.getById(idLivre);

        if (livre == null) {
            System.out.println("Livre introuvable.");
            return;
        }

        if (livre.getStock() <= 0) {
            System.out.println("Livre non disponible.");
            return;
        }

        livre.reduireStock(livre.getStock() - 1);
        livreDAO.update(livre);

        System.out.println("Livre emprunté !");
    }

    public void rendreLivre(int idLivre) {
        Livre livre = livreDAO.getById(idLivre);

        if (livre == null) {
            System.out.println("Livre introuvable.");
            return;
        }

        livre.reduireStock(livre.getStock() + 1);
        livreDAO.update(livre);

        System.out.println("Livre rendu !");
    }
}

