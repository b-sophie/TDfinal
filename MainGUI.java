package tdfinal;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import tdfinal.base.Livre;

public class MainGUI {
    private BibliothequeService service;
    private JTextArea textArea;

    public MainGUI() {
        service = new BibliothequeService();
        
        JFrame frame = new JFrame("Bibliothèque");
        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panneau des boutons (haut)
        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new GridLayout(5, 1, 10, 10));
        panelButtons.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnAdd = new JButton("Ajouter un livre");
        JButton btnList = new JButton("Lister les livres");
        JButton btnBorrow = new JButton("Emprunter un livre");
        JButton btnReturn = new JButton("Rendre un livre");
        JButton btnQuit = new JButton("Quitter");

        // Action listeners
        btnAdd.addActionListener(e -> onAjouter());
        btnList.addActionListener(e -> onLister());
        btnBorrow.addActionListener(e -> onEmprunter());
        btnReturn.addActionListener(e -> onRendre());
        btnQuit.addActionListener(e -> System.exit(0));

        panelButtons.add(btnAdd);
        panelButtons.add(btnList);
        panelButtons.add(btnBorrow);
        panelButtons.add(btnReturn);
        panelButtons.add(btnQuit);

        // Panneau de texte pour afficher résultats (bas)
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Layout principal
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelButtons, scrollPane);
        splitPane.setDividerLocation(200);

        frame.add(splitPane);
        frame.setVisible(true);
    }

    private void onAjouter() {
        JTextField tfTitre = new JTextField();
        JTextField tfAuteur = new JTextField();
        JTextField tfStock = new JTextField();

        Object[] message = {
            "Titre:", tfTitre,
            "Auteur:", tfAuteur,
            "Stock:", tfStock
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Ajouter un livre", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String titre = tfTitre.getText();
                String auteur = tfAuteur.getText();
                int stock = Integer.parseInt(tfStock.getText());

                if (titre.isEmpty() || auteur.isEmpty()) {
                    textArea.append("Titre et auteur ne peuvent pas être vides.\n");
                    return;
                }

                Livre livre = new Livre(titre, auteur, stock);
                service.ajouterLivre(livre);
                textArea.append("✓ Livre ajouté : " + titre + " par " + auteur + "\n");
            } catch (NumberFormatException ex) {
                textArea.append("Stock invalide (doit être un nombre).\n");
            }
        }
    }

    private void onLister() {
        textArea.append("\n=== Liste des livres ===\n");
        try {
            java.util.List<Livre> livres = service.getAllLivres();
            if (livres == null) {
                textArea.append("Erreur : connexion à la base de données impossible (connection null).\n");
                return;
            }
            if (livres.isEmpty()) {
                textArea.append("(Aucun livre trouvé)\n");
                return;
            }
            int i = 1;
            for (Livre l : livres) {
                textArea.append(i + ". " + l.getTitre() + " — " + l.getAuteur() + " (stock: " + l.getStock() + ")\n");
                i++;
            }
            textArea.append("\n");
        } catch (Throwable ex) {
            textArea.append("Erreur lors de la récupération : " + ex.toString() + "\n");
            for (StackTraceElement ste : ex.getStackTrace()) {
                textArea.append("  at " + ste.toString() + "\n");
            }
        }
    }

    private void onEmprunter() {
        String idStr = JOptionPane.showInputDialog("ID du livre à emprunter :");
        if (idStr != null) {
            try {
                int idLivre = Integer.parseInt(idStr);
                service.emprunterLivre(idLivre);
                textArea.append("✓ Livre emprunté (ID: " + idLivre + ")\n");
            } catch (NumberFormatException ex) {
                textArea.append("ID invalide.\n");
            }
        }
    }

    private void onRendre() {
        String idStr = JOptionPane.showInputDialog("ID du livre à rendre :");
        if (idStr != null) {
            try {
                int idLivre = Integer.parseInt(idStr);
                service.rendreLivre(idLivre);
                textArea.append("✓ Livre rendu (ID: " + idLivre + ")\n");
            } catch (NumberFormatException ex) {
                textArea.append("ID invalide.\n");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI());
    }
}
