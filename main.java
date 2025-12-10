package tdfinal;

import java.util.Scanner;

import tdfinal.base.Livre;

/*import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;*/

public class Main{
        public static void main(String[] args) {

        BibliothequeService service = new BibliothequeService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== MENU BIBLIOTHEQUE ===");
            System.out.println("1. Ajouter un livre");
            System.out.println("2. Lister les livres");
            System.out.println("3. Emprunter un livre");
            System.out.println("4. Rendre un livre");
            System.out.println("5. Quitter");
            System.out.print("Votre choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine(); // vider buffer

            switch (choix) {
                case 1:
                    System.out.print("Titre : ");
                    String titre = scanner.nextLine();

                    System.out.print("Auteur : ");
                    String auteur = scanner.nextLine();

                    System.out.print("Stock : ");
                    int stock = scanner.nextInt();

                    Livre livre = new Livre(titre, auteur, stock);
                    service.ajouterLivre(livre);
                    break;

                case 2:
                    service.listerLivres();
                    break;

                case 3:
                    System.out.print("ID du livre à emprunter : ");
                    int idEmprunt = scanner.nextInt();
                    service.emprunterLivre(idEmprunt);
                    break;

                case 4:
                    System.out.print("ID du livre à rendre : ");
                    int idRetour = scanner.nextInt();
                    service.rendreLivre(idRetour);
                    break;

                case 5:
                    System.out.println("Au revoir !");
                    return;

                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

        /*public static void main(String[] args) {
                launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
                // Charge le fichier FXML situé dans le même package (TDfinal/bibliotheque.fxml)
                Parent root = FXMLLoader.load(getClass().getResource("bibliotheque.fxml"));
                Scene scene = new Scene(root);
                primaryStage.setTitle("Bibliothèque");
                primaryStage.setScene(scene);
                primaryStage.show();
        }
}
*/
}