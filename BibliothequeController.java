package tdfinal;

/*import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tdfinal.base.Livre;

public class BibliothequeController {

    @FXML
    private TableView<Livre> tableLivres;

    @FXML
    private TableColumn<Livre, String> colTitre;

    @FXML
    private TableColumn<Livre, String> colAuteur;

    @FXML
    private TableColumn<Livre, Integer> colStock;

    @FXML
    private TextField tfTitre;

    @FXML
    private TextField tfAuteur;

    @FXML
    private TextField tfStock;

    @FXML
    private Label statusLabel;

    private ObservableList<Livre> livres = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // configure columns (PropertyValueFactory uses getters: getTitre, getAuteur, getStock)
        colTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        colAuteur.setCellValueFactory(new PropertyValueFactory<>("auteur"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        // Example data (replace with data from LivreDAO when ready)
        livres.add(new Livre("Le Petit Prince", "Antoine de Saint-Exupéry", 3));
        livres.add(new Livre("1984", "George Orwell", 5));
        livres.add(new Livre("Les Misérables", "Victor Hugo", 2));

        tableLivres.setItems(livres);
    }

    @FXML
    private void onAjouter() {
        String titre = tfTitre.getText();
        String auteur = tfAuteur.getText();
        int stock = 0;
        try {
            stock = Integer.parseInt(tfStock.getText());
        } catch (NumberFormatException e) {
            statusLabel.setText("Stock invalide");
            return;
        }

        Livre l = new Livre(titre, auteur, stock);
        livres.add(l);
        // TODO: appeler LivreDAO.add(l) pour persister
        statusLabel.setText("Livre ajouté");
        tfTitre.clear(); tfAuteur.clear(); tfStock.clear();
    }

    @FXML
    private void onEmprunter() {
        Livre selected = tableLivres.getSelectionModel().getSelectedItem();
        if (selected == null) {
            statusLabel.setText("Sélectionnez un livre");
            return;
        }
        if (selected.getStock() <= 0) {
            statusLabel.setText("Stock insuffisant");
            return;
        }
        // réduire le stock localement (la classe Livre n'a pas de propriété observable)
        selected.reduireStock(1);
        tableLivres.refresh();
        statusLabel.setText("Livre emprunté");
        // TODO: persister le changement de stock via DAO
    }

    @FXML
    private void onRetourner() {
        Livre selected = tableLivres.getSelectionModel().getSelectedItem();
        if (selected == null) {
            statusLabel.setText("Sélectionnez un livre");
            return;
        }
        // Pas de méthode d'augmentation du stock; on manipule directement via constructeur-style workaround
        // Simplest approach: replace with a new Livre instance with increased stock
        int idx = livres.indexOf(selected);
        Livre updated = new Livre(selected.getTitre(), selected.getAuteur(), selected.getStock() + 1);
        livres.set(idx, updated);
        tableLivres.refresh();
        statusLabel.setText("Livre retourné");
        // TODO: persister le changement via DAO
    }

    @FXML
    private void onRefresh() {
        tableLivres.refresh();
        statusLabel.setText("Actualisé");
    }*/

