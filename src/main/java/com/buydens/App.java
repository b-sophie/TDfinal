package com.buydens;

import com.buydens.Library.Book;
import com.buydens.Library.BookDao;
import com.buydens.Library.User;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.List;

public class App extends Application {

    private User currentUser;
    private Label lentCountLabel;

    // Retrieve the logged-in user
    public void setCurrentUser(User user) {
        // this.currentUser = user;
        this.currentUser = new User("admin", "adminpass", "ADMIN");
    }

    @Override
    // public void start(Stage stage) {
    public void start(Stage stage) {
        
        this.currentUser = new User("admin", "adminpass", "ADMIN");

        VBox root = new VBox(20);
        root.setPadding(new javafx.geometry.Insets(20));

        //------------------------------------------------------------------------------------------
        // Top bar with welcome and logout
        Label welcomeLabel = new Label("Bienvenue, " + currentUser.getPseudo());
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button logoutButton = new Button("Log Out");
        logoutButton.setStyle("-fx-background-color: linear-gradient(90deg, #ff5252, #ffb300); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 18; -fx-padding: 8 28; -fx-font-size: 15px; -fx-effect: none; -fx-border-width: 0;");
        logoutButton.setOnMouseEntered(ev -> logoutButton.setStyle("-fx-background-color: linear-gradient(90deg, #ff7043, #ffd740); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 18; -fx-padding: 8 28; -fx-font-size: 15px; -fx-effect: none; -fx-border-width: 0; -fx-cursor: hand;"));
        logoutButton.setOnMouseExited(ev -> logoutButton.setStyle("-fx-background-color: linear-gradient(90deg, #ff5252, #ffb300); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 18; -fx-padding: 8 28; -fx-font-size: 15px; -fx-effect: none; -fx-border-width: 0;"));
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topBar.getChildren().addAll(welcomeLabel, spacer, logoutButton);

        //------------------------------------------------------------------------------------------
        // Top statistics squares
        HBox statsBox = new HBox(20);
        statsBox.setAlignment(Pos.CENTER);

        StackPane totalPane = createStatPane("Total Books", String.valueOf(MainController.countBooks()), "#4CAF50");

        
        // StackPane lentPane = createStatPane("Borrowed", String.valueOf(ManagerEmprunt.countEmpruntsForUser(currentUser)), "#2196F3");
        
        // lentCountLabel = (Label) ((VBox) lentPane.getChildren().get(1)).getChildren().get(0);

        StackPane overduePane = createStatPane("Overdue", "0", "#F44336");
        // statsBox.getChildren().addAll(totalPane, lentPane, overduePane);

        
        VBox actionBox = new VBox(10);
        // actionBox.setAlignment(Pos.CENTER_LEFT);

        //------------------------------------------------------------------------------------------
        // Box for borrow/return/add
        HBox borrowBox = new HBox(10);
        TextField borrowField = new TextField();
        borrowField.setPromptText("Name of the book to borrow");
        Button borrowButton = new Button("Borrow");
        Label borrowResult = new Label();
        borrowBox.getChildren().addAll(borrowField, borrowButton);
        borrowBox.setAlignment(Pos.CENTER_LEFT);

        HBox returnBox = new HBox(10);
        TextField returnField = new TextField();
        returnField.setPromptText("Name of the book to return");
        Button returnButton = new Button("Return");
        Label returnResult = new Label();
        returnBox.getChildren().addAll(returnField, returnButton);
        returnBox.setAlignment(Pos.CENTER_LEFT);

        HBox addBox = new HBox(10);
        // TextField addField = new TextField();
        Button addButton = new Button("Add new Book");
        addBox.getChildren().addAll(addButton);
        addBox.setAlignment(Pos.CENTER_LEFT);   

        if ("ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            actionBox.getChildren().addAll(borrowBox, borrowResult, returnBox, returnResult, addBox);
            System.out.println("Affichage des options admin pour " + currentUser.getPseudo());
        }
        else {
            actionBox.getChildren().addAll(borrowBox, borrowResult, returnBox, returnResult);
        }

        // ------------------------------------------------------------------------------------------
        // Book list area
        ScrollPane booksScroll = new ScrollPane();
        booksScroll.setFitToWidth(true);
        booksScroll.setPrefHeight(450);
        VBox booksContainer = createBookCards(MainController.loadBooks());
        booksScroll.setContent(booksContainer);
        // Displat options related to this Book
        addButton.setOnAction(e -> {
            Stage addStage = new Stage();
            TextField linkField = new TextField();
            linkField.setPromptText("Audible/Fnac Link");
            TextField titleField = new TextField();
            titleField.setPromptText("Title");
            TextField authorField = new TextField();
            authorField.setPromptText("Author");
            TextField stockField = new TextField();
            stockField.setPromptText("Initial Stock");
            TextField coverField = new TextField();
            coverField.setPromptText("Cover Image URL");
            TextArea descField = new TextArea();
            descField.setPromptText("Description");
            descField.setPrefRowCount(3);
            Button autofillButton = new Button("Auto-fill from Link");
            Button saveButton = new Button("Save");
            VBox addRoot = new VBox(10, linkField, autofillButton, titleField, authorField, stockField, coverField, descField, saveButton);
            addRoot.setPadding(new javafx.geometry.Insets(20));
            Scene addScene = new Scene(addRoot, 400, 420);
            addStage.setScene(addScene);
            addStage.setTitle("Add a Book");
            addStage.show();

            autofillButton.setOnAction(ev -> {
                String link = linkField.getText().trim();
                // Demo: If link contains 'audible', fill with Wise Man's Fear info
                if (link.contains("audible")) {
                    titleField.setText("The Wise Man's Fear");
                    authorField.setText("Patrick Rothfuss");
                    coverField.setText("https://m.media-amazon.com/images/I/51y5F1kQGGL._SL500_.jpg");
                    descField.setText("Day two: the wise man’s fear. 'There are three things all wise men fear: the sea in storm, a night with no moon, and the anger of a gentle man.' My name is Kvothe. You may have heard of me. So begins a tale told from his own point of view—a story unequaled in fantasy literature. Now in The Wise Man’s Fear, Day Two of The Kingkiller Chronicle, Kvothe takes his first steps on the path of the hero and learns how difficult life can be when a man becomes a legend in his own time.");
                } else {
                    // Could add more logic for Fnac or other links
                }
            });

            saveButton.setOnAction(ev -> {
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                int stock;
                try {
                    stock = Integer.parseInt(stockField.getText().trim());
                } catch (NumberFormatException ex) {
                    stock = 0;
                }
                String cover = coverField.getText().trim();
                String desc = descField.getText().trim();
                if (!title.isEmpty() && !author.isEmpty() && stock > 0) {
                    BookDao.insert(new Book(title, author, stock, cover, desc)); // You may need to update Book class/DB
                    addStage.close();
                    refreshBookList(booksContainer);
                    System.out.println("Livre ajouté: " + title);
                }
            });
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
        // borrowButton.setOnAction(e -> {
        //     String name = borrowField.getText().trim();
        //     Book selected = MainController.loadBooks().stream()
        //             .filter(b -> b.getTitle().equalsIgnoreCase(name))
        //             .findFirst()
        //             .orElse(null);

        //     if (selected == null) {
        //         borrowResult.setText("Livre introuvable !");
        //     } else if (ManagerEmprunt.emprunter(selected, currentUser)) {
        //         borrowResult.setText("Livre emprunté !");
        //         borrowField.clear();
        //         updateLentCount();
        //         refreshBookList(booksContainer);
        //     } else {
        //         borrowResult.setText("Stock insuffisant");
        //     }
        // });

        // bouton Rendre
        // returnButton.setOnAction(e -> {
        //     String name = returnField.getText().trim();
        //     Book selected = MainController.loadBooks().stream()
        //             .filter(b -> b.getTitle().equalsIgnoreCase(name))
        //             .findFirst()
        //             .orElse(null);

        //     if (selected == null) {
        //         returnResult.setText("Livre introuvable !");
        //     } else if (ManagerEmprunt.rendre(selected, currentUser)) {
        //         returnResult.setText("Livre rendu !");
        //         returnField.clear();
        //         updateLentCount();
        //         refreshBookList(booksContainer);
        //     } else {
        //         returnResult.setText("Vous n'avez pas emprunté ce livre");
        //     }
        // });

        root.getChildren().addAll(topBar, statsBox, actionBox, booksScroll);

        Scene scene = new Scene(root, 900, 700);
        stage.setTitle("Gestion de Bibliothèque");
        stage.setScene(scene);
        stage.show();
    }

    // mise à jour
    // private void updateLentCount() {
    //     long count = ManagerEmprunt.countEmpruntsForUser(currentUser);
    //     lentCountLabel.setText(String.valueOf(count));
    // }

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
            HBox card = new HBox(18);
            card.setPadding(new javafx.geometry.Insets(12));
            card.setStyle("-fx-background-color: linear-gradient(90deg, #fff 80%, #e3f2fd 100%); -fx-background-radius: 18; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, #e3f2fd, 8, 0.2, 0, 2);");
            card.setAlignment(Pos.CENTER_LEFT);
            card.setMinHeight(110);

            // Cover image (Audible style)
            ImageView coverView = new ImageView();
            String coverUrl = b.getCoverUrl();
            System.out.println("Loading cover for book ID " + b.getId() + ": " + coverUrl);
            if (coverUrl != null && !coverUrl.isEmpty()) {
                try {
                    coverView.setImage(new javafx.scene.image.Image(coverUrl, 80, 110, true, true));
                } catch (Exception e) {
                    coverView.setImage(new javafx.scene.image.Image("https://via.placeholder.com/80x110?text=No+Cover"));
                }
            } else {
                coverView.setImage(new javafx.scene.image.Image("https://via.placeholder.com/80x110?text=No+Cover"));
            }
            coverView.setFitWidth(80);
            coverView.setFitHeight(110);
            coverView.setSmooth(true);
            coverView.setStyle("-fx-effect: dropshadow(gaussian, #bdbdbd, 6, 0.2, 0, 2); -fx-background-radius: 8;");

            VBox info = new VBox(6);
            Label title = new Label(b.getTitle());
            title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #222;");
            Label author = new Label("by " + b.getAuthor());
            author.setStyle("-fx-text-fill: #666; -fx-font-size: 13px;");
            Label stock = new Label("Stock: " + b.getStock());
            stock.setStyle("-fx-text-fill: #1976D2; -fx-font-weight: bold; -fx-font-size: 13px;");
            Label desc = null;
            if (b.getDescription() != null && !b.getDescription().isEmpty()) {
                desc = new Label(b.getDescription());
                desc.setStyle("-fx-text-fill: #444; -fx-font-size: 12px; -fx-padding: 4 0 0 0;");
                desc.setWrapText(true);
                desc.setMaxWidth(320);
            }

            if (b.getStock() <= 0) {
                card.setOpacity(0.5);
                Label out = new Label("(Indisponible)");
                out.setStyle("-fx-text-fill: #c00; -fx-font-weight: bold; -fx-font-size: 13px;");
                if (desc != null) {
                    info.getChildren().addAll(title, author, stock, out, desc);
                } else {
                    info.getChildren().addAll(title, author, stock, out);
                }
            } else {
                if (desc != null) {
                    info.getChildren().addAll(title, author, stock, desc);
                } else {
                    info.getChildren().addAll(title, author, stock);
                }
            }

            card.getChildren().addAll(coverView, info);

            // Clickable: show borrow dialog if available
            if (b.getStock() > 0) {
                card.setOnMouseClicked(e -> {
                    Dialog<ButtonType> dialog = new Dialog<>();
                    dialog.setTitle("Emprunter le livre");
                    dialog.setHeaderText(null);
                    dialog.getDialogPane().setStyle("-fx-background-color: #fff; -fx-border-radius: 16; -fx-background-radius: 16;");
                    dialog.getDialogPane().setPrefWidth(400);
                    dialog.getDialogPane().setPrefHeight(320);

                    VBox content = new VBox(18);
                    content.setAlignment(Pos.CENTER);
                    content.setStyle("-fx-padding: 24;");
                    ImageView dialogCover = new ImageView(coverView.getImage());
                    dialogCover.setFitWidth(90);
                    dialogCover.setFitHeight(120);
                    dialogCover.setSmooth(true);
                    dialogCover.setStyle("-fx-effect: dropshadow(gaussian, #bdbdbd, 8, 0.2, 0, 2); -fx-background-radius: 8;");
                    Label bookTitle = new Label(b.getTitle());
                    bookTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1976D2;");
                    Label bookAuthor = new Label("par " + b.getAuthor());
                    bookAuthor.setStyle("-fx-font-size: 15px; -fx-text-fill: #666;");
                    Label bookStock = new Label("Stock disponible: " + b.getStock());
                    bookStock.setStyle("-fx-font-size: 14px; -fx-text-fill: #388E3C;");
                    Label bookDesc = null;
                    if (b.getDescription() != null && !b.getDescription().isEmpty()) {
                        bookDesc = new Label(b.getDescription());
                        bookDesc.setStyle("-fx-text-fill: #444; -fx-font-size: 13px; -fx-padding: 8 0 0 0;");
                        bookDesc.setWrapText(true);
                        bookDesc.setMaxWidth(320);
                    }
                    if (bookDesc != null) {
                        content.getChildren().addAll(dialogCover, bookTitle, bookAuthor, bookStock, bookDesc);
                    } else {
                        content.getChildren().addAll(dialogCover, bookTitle, bookAuthor, bookStock);
                    }

                    dialog.getDialogPane().setContent(content);
                    ButtonType borrowBtn = new ButtonType("Emprunter", ButtonBar.ButtonData.OK_DONE);
                    ButtonType cancelBtn = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
                    dialog.getDialogPane().getButtonTypes().addAll(borrowBtn, cancelBtn);

                    // Custom styling for buttons (2025 look)
                    Button okButton = (Button) dialog.getDialogPane().lookupButton(borrowBtn);
                    if (okButton != null) {
                        okButton.setStyle("-fx-background-color: linear-gradient(90deg, #1976D2, #64B5F6); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 8 24;");
                    }
                    Button cancelButton = (Button) dialog.getDialogPane().lookupButton(cancelBtn);
                    if (cancelButton != null) {
                        cancelButton.setStyle("-fx-background-color: #f5f5f5; -fx-text-fill: #1976D2; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 8 24;");
                    }

                    // dialog.setResultConverter(dialogButton -> {
                    //     if (dialogButton == borrowBtn) {
                    //         // Try to borrow
                    //         boolean success = ManagerEmprunt.emprunter(b, currentUser);
                    //         if (success) {
                    //             updateLentCount();
                    //             refreshBookList((VBox) card.getParent());
                    //             Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    //             alert.setTitle("Succès");
                    //             alert.setHeaderText(null);
                    //             alert.setContentText("Livre emprunté avec succès !");
                    //             alert.getDialogPane().setStyle("-fx-background-color: #e3f2fd; -fx-font-size: 15px; -fx-background-radius: 12;");
                    //             alert.showAndWait();
                    //         } else {
                    //             Alert alert = new Alert(Alert.AlertType.ERROR);
                    //             alert.setTitle("Erreur");
                    //             alert.setHeaderText(null);
                    //             alert.setContentText("Stock insuffisant ou déjà emprunté.");
                    //             alert.getDialogPane().setStyle("-fx-background-color: #ffebee; -fx-font-size: 15px; -fx-background-radius: 12;");
                    //             alert.showAndWait();
                    //         }
                    //     }
                    //     return null;
                    // });

                    dialog.showAndWait();
                });
            }

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
