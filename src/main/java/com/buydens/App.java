package com.buydens;

import com.buydens.Library.Book;
import com.buydens.Library.BookDao;
import com.buydens.Library.User;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.List;

public class App extends Application {

    private User currentUser;
    private Label lentCountLabel;

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(20);
        root.setPadding(new javafx.geometry.Insets(20));

        
        Label welcomeLabel = new Label("Bienvenue, " + currentUser.getPseudo());
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button logoutButton = new Button("Se Déconnecter");
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topBar.getChildren().addAll(welcomeLabel, spacer, logoutButton);

        HBox statsBox = new HBox(20);
        statsBox.setAlignment(Pos.CENTER);

        StackPane totalPane = createStatPane("Total Livres", String.valueOf(MainController.countBooks()), "#4CAF50");

        
        StackPane lentPane = createStatPane("Empruntés", String.valueOf(ManagerEmprunt.countEmpruntsForUser(currentUser)), "#2196F3");
        
        lentCountLabel = (Label) ((VBox) lentPane.getChildren().get(1)).getChildren().get(0);

        StackPane overduePane = createStatPane("En retard", "0", "#F44336");

        statsBox.getChildren().addAll(totalPane, lentPane, overduePane);

        
        VBox actionBox = new VBox(10);
        // actionBox.setAlignment(Pos.CENTER_LEFT);


        HBox borrowBox = new HBox(10);
        TextField borrowField = new TextField();
        borrowField.setPromptText("Nom du livre à emprunter");
        Button borrowButton = new Button("Emprunter");
        Label borrowResult = new Label();
        borrowBox.getChildren().addAll(borrowField, borrowButton);
        borrowBox.setAlignment(Pos.CENTER_LEFT);

        HBox returnBox = new HBox(10);
        TextField returnField = new TextField();
        returnField.setPromptText("Nom du livre à rendre");
        Button returnButton = new Button("Rendre");
        Label returnResult = new Label();
        returnBox.getChildren().addAll(returnField, returnButton);
        returnBox.setAlignment(Pos.CENTER_LEFT);

        

        HBox addBox = new HBox(10);
        // TextField addField = new TextField();
        Button addButton = new Button("Ajouter");
        addBox.getChildren().addAll(addButton);
        addBox.setAlignment(Pos.CENTER_LEFT);   

        if ("ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            actionBox.getChildren().addAll(borrowBox, borrowResult, returnBox, returnResult, addBox);
            System.out.println("Affichage des options admin pour " + currentUser.getPseudo());
        }
        else {
            actionBox.getChildren().addAll(borrowBox, borrowResult, returnBox, returnResult);
        }

        // livres
        ScrollPane booksScroll = new ScrollPane();
        booksScroll.setFitToWidth(true);
        booksScroll.setPrefHeight(450);
        VBox booksContainer = createBookCards(MainController.loadBooks());
        booksScroll.setContent(booksContainer);

        addButton.setOnAction(e -> {
            Stage addStage = new Stage();
            TextField titleField = new TextField();
            titleField.setPromptText("Titre du livre");
            TextField authorField = new TextField();
            authorField.setPromptText("Auteur du livre");
            TextField stockField = new TextField();
            stockField.setPromptText("Stock initial");
            Button saveButton = new Button("Enregistrer");
            VBox addRoot = new VBox(10, titleField, authorField, stockField, saveButton);
            addRoot.setPadding(new javafx.geometry.Insets(20));
            Scene addScene = new Scene(addRoot, 300, 250);
            addStage.setScene(addScene);
            addStage.setTitle("Ajout d'un Livre");
            addStage.setScene(addScene);
            addStage.show();
            saveButton.setOnAction(ev -> {
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                int stock;
                try {
                    stock = Integer.parseInt(stockField.getText().trim());
                } catch (NumberFormatException ex) {
                    stock = 0;
                }
                if (!title.isEmpty() && !author.isEmpty() && stock > 0) {
                    BookDao.insert(new Book(title, author, stock));
                    addStage.close();
                    refreshBookList(booksContainer);
                    System.out.println("Livre ajouté: " + title);
                }
            });
            String title = titleField.getText().trim();
            if (!title.isEmpty()) {
                BookDao.insert(new Book(title, "George Orwell", 3));
                // MainController.addBook(newBook);
                titleField.clear();
                refreshBookList(booksContainer);
                System.out.println("Livre ajouté: " + title);
            }
        });

        logoutButton.setOnAction(e -> {
            // Simple logout: close current stage and reopen login
            stage.close();
            //AppLauncher.showLoginStage();
            // javafx.application.Application.launch(PageConnexion.class);
            new PageConnexion().start(new Stage());
            System.out.println("User logged out.");
        });
        // bouton emprunter
        borrowButton.setOnAction(e -> {
            String name = borrowField.getText().trim();
            Book selected = MainController.loadBooks().stream()
                    .filter(b -> b.getTitle().equalsIgnoreCase(name))
                    .findFirst()
                    .orElse(null);

            if (selected == null) {
                borrowResult.setText("Livre introuvable !");
            } else if (ManagerEmprunt.emprunter(selected, currentUser)) {
                borrowResult.setText("Livre emprunté !");
                borrowField.clear();
                updateLentCount();
                refreshBookList(booksContainer);
            } else {
                borrowResult.setText("Stock insuffisant");
            }
        });

        // bouton Rendre
        returnButton.setOnAction(e -> {
            String name = returnField.getText().trim();
            Book selected = MainController.loadBooks().stream()
                    .filter(b -> b.getTitle().equalsIgnoreCase(name))
                    .findFirst()
                    .orElse(null);

            if (selected == null) {
                returnResult.setText("Livre introuvable !");
            } else if (ManagerEmprunt.rendre(selected, currentUser)) {
                returnResult.setText("Livre rendu !");
                returnField.clear();
                updateLentCount();
                refreshBookList(booksContainer);
            } else {
                returnResult.setText("Vous n'avez pas emprunté ce livre");
            }
        });

        root.getChildren().addAll(topBar, statsBox, actionBox, booksScroll);

        Scene scene = new Scene(root, 900, 700);
        stage.setTitle("Gestion de Bibliothèque");
        stage.setScene(scene);
        stage.show();
    }

    // mise à jour
    private void updateLentCount() {
        long count = ManagerEmprunt.countEmpruntsForUser(currentUser);
        lentCountLabel.setText(String.valueOf(count));
    }

    private StackPane createStatPane(String label, String value, String color) {
        StackPane pane = new StackPane();
        pane.setPrefSize(150, 100);

        Rectangle rect = new Rectangle(150, 100);
        rect.setArcWidth(20);
        rect.setArcHeight(20);
        rect.setFill(Color.valueOf(color));

        Label v = new Label(value);
        v.setStyle("-fx-font-size: 32px; -fx-text-fill: white; -fx-font-weight: bold;");
        Label t = new Label(label);
        t.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        VBox box = new VBox(5, v, t);
        box.setAlignment(Pos.CENTER);

        pane.getChildren().addAll(rect, box);
        return pane;
    }

    private VBox createBookCards(List<Book> books) {
        VBox vbox = new VBox(15);
        vbox.setPadding(new javafx.geometry.Insets(10));

        for (Book b : books) {
            HBox card = new HBox(15);
            card.setPadding(new javafx.geometry.Insets(10));
            card.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #bbb, 4, 0, 0, 2);");
            card.setAlignment(Pos.CENTER_LEFT);

            Rectangle colorBar = new Rectangle(8, 60);
            colorBar.setArcWidth(8);
            colorBar.setArcHeight(8);
            colorBar.setFill(Color.valueOf("#4CAF50"));

            VBox info = new VBox(5);
            Label title = new Label(b.getTitle());
            title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            Label author = new Label("by " + b.getAuthor());
            Label stock = new Label("Stock: " + b.getStock());

            if (b.getStock() <= 0) {
                card.setOpacity(0.6);
                Label out = new Label("(Indisponible)");
                out.setStyle("-fx-text-fill: #c00; -fx-font-weight: bold;");
                info.getChildren().addAll(title, author, stock, out);
            } else {
                info.getChildren().addAll(title, author, stock);
            }

            card.getChildren().addAll(colorBar, info);
            vbox.getChildren().add(card);
        }

        return vbox;
    }

    private void refreshBookList(VBox container) {
        container.getChildren().setAll(createBookCards(MainController.loadBooks()).getChildren());
    }
}








/*import com.buydens.Library.Book;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class App extends Application {
    @Override
    public void start(Stage stage1) {
        VBox root = new VBox(20);
        scene mainScene = new Scene(new Main(20));
        root.setPadding(new javafx.geometry.Insets(20));

        // Top statistics squares
        HBox statsBox = new HBox(20);
        statsBox.setAlignment(javafx.geometry.Pos.CENTER);

        StackPane totalPane = createStatPane("Total Livres", String.valueOf(countBooksInDatabase()), "#4CAF50");
        StackPane lentPane = createStatPane("Empruntés", "0", "#2196F3");
        StackPane overduePane = createStatPane("En retard", "0", "#F44336");
        statsBox.getChildren().addAll(totalPane, lentPane, overduePane);
    
}
public static void main(String[] args) {
        launch(args);
    }
}*/
