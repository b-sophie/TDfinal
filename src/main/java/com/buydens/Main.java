package com.buydens;

public class Main {

    public static void main(String[] args) {
        Database.initialize(); 
        System.out.println("Populate books");         
        MainController.populateBooks(); 
        System.out.println("System started.");

        javafx.application.Application.launch(PageConnexion.class, args);
        // javafx.application.Application.launch(App.class, args);
    }
}


 
 
 
 
 
 /*package com.buydens;

import com.buydens.Library.Book;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
// import javafx.scene.control.Label;
// import javafx.scene.control.Button;
// import javafx.scene.shape.Rectangle;
// import javafx.scene.paint.Paint;
// import javafx.geometry.Insets;
// import javafx.geometry.Pos;

/*public class Main extends VBox{
    public Main(double spacing) {
        super(spacing);
    }
public class Main extends Application {/*
    /*@Override
    public void start(Stage stage1) {
        VBox root = new VBox(20);
        root.setPadding(new javafx.geometry.Insets(20));

        // Top statistics squares
        HBox statsBox = new HBox(20);
        statsBox.setAlignment(javafx.geometry.Pos.CENTER);

        StackPane totalPane = createStatPane("Total Livres", String.valueOf(countBooksInDatabase()), "#4CAF50");
        StackPane lentPane = createStatPane("Empruntés", "0", "#2196F3");
        StackPane overduePane = createStatPane("En retard", "0", "#F44336");
        statsBox.getChildren().addAll(totalPane, lentPane, overduePane);

        // Buttons row
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(javafx.geometry.Pos.CENTER);
        javafx.scene.control.Button btnAll = new javafx.scene.control.Button("Afficher tous les livres");
        javafx.scene.control.Button btnLent = new javafx.scene.control.Button("Livres Empruntés");
        javafx.scene.control.Button btnOverdue = new javafx.scene.control.Button("Livres en Retard");
        javafx.scene.control.Button btnReturned = new javafx.scene.control.Button("Livres Rendus");
        javafx.scene.control.Button btnPopular = new javafx.scene.control.Button("Livres Populaires");
        buttonBox.getChildren().addAll(btnAll, btnLent, btnOverdue, btnReturned, btnPopular);

        root.getChildren().addAll(statsBox, buttonBox);

        Scene scene = new Scene(root, 900, 700);
        stage1.setTitle("Gestion de Bibliothèque");
        stage1.setScene(scene);


        btnAll.setOnAction(e -> {
            // Modern scrollable card layout for books
            javafx.scene.control.ScrollPane scrollPane = new javafx.scene.control.ScrollPane();
            VBox bookCards = createBookCards(loadBooksFromDatabase());
            scrollPane.setContent(bookCards);
            scrollPane.setFitToWidth(true);
            scrollPane.setPrefHeight(500);
            bookCards.getChildren().setAll(createBookCards(loadBooksFromDatabase()).getChildren());
            System.out.println("All books displayed.");
            root.getChildren().add(scrollPane);
        });


        stage1.show();
    }

    private StackPane createStatPane(String label, String value, String color) {
        StackPane pane = new StackPane();
        pane.setPrefSize(150, 100);
        javafx.scene.shape.Rectangle rect = new javafx.scene.shape.Rectangle(150, 100);
        rect.setArcWidth(20);
        rect.setArcHeight(20);
        rect.setFill(javafx.scene.paint.Paint.valueOf(color));
        javafx.scene.control.Label lblValue = new javafx.scene.control.Label(value);
        lblValue.setStyle("-fx-font-size: 32px; -fx-text-fill: white; -fx-font-weight: bold;");
        javafx.scene.control.Label lblText = new javafx.scene.control.Label(label);
        lblText.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");
        VBox vbox = new VBox(5, lblValue, lblText);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);
        pane.getChildren().addAll(rect, vbox);
        return pane;
    }

    // Create modern styled book cards
    private VBox createBookCards(Book[] books) {
        VBox vbox = new VBox(15);
        vbox.setPadding(new javafx.geometry.Insets(10));
        for (Book b : books) {
            javafx.scene.layout.HBox card = new javafx.scene.layout.HBox(15);
            card.setPadding(new javafx.geometry.Insets(15));
            card.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #bbb, 6, 0, 0, 2);");
            card.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

            // Optional: colored bar for accent
            javafx.scene.shape.Rectangle colorBar = new javafx.scene.shape.Rectangle(8, 60);
            colorBar.setArcWidth(8);
            colorBar.setArcHeight(8);
            colorBar.setFill(javafx.scene.paint.Paint.valueOf("#4CAF50"));

            // Book info
            VBox info = new VBox(5);
            javafx.scene.control.Label title = new javafx.scene.control.Label(b.getTitle());
            title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");
            javafx.scene.control.Label author = new javafx.scene.control.Label("by " + b.getAuthor());
            author.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");
            javafx.scene.control.Label id = new javafx.scene.control.Label("ID: " + b.getId());
            id.setStyle("-fx-font-size: 12px; -fx-text-fill: #999;");
            info.getChildren().addAll(title, author, id);

            card.getChildren().addAll(colorBar, info);
            vbox.getChildren().add(card);
        }
        return vbox;
    }

    public static void main(String[] args) {
        Database.initialize();   // Create database + tables

        System.out.println("Library System Started!");

        populateBooks();  
        // Book[] books = loadBooksFromDatabase();

         // Delete a book      
        // books[3].deleteBookFromDatabase();

        // System.out.println("Book successfully deleted.");

        launch(args); // Launch JavaFX application      
    }
    
    public static void populateBooks() {
        Book[] books = new Book[] {
            new Book("B1", "1984", "George Orwell"),
            new Book("B2", "Brave New World", "Aldous Huxley"),
            new Book("B3", "Fahrenheit 451", "Ray Bradbury"),
            new Book("B4", "To Kill a Mockingbird", "Harper Lee"),
            new Book("B5", "The Great Gatsby", "F. Scott Fitzgerald"),
            new Book("B6", "Moby Dick", "Herman Melville"),
            new Book("B7", "Pride and Prejudice", "Jane Austen"),
            new Book("B8", "The Catcher in the Rye", "J.D. Salinger"),
            new Book("B9", "The Hobbit", "J.R.R. Tolkien"),
            new Book("B10", "Animal Farm", "George Orwell")
        };
        for (Book b : books) {
            b.saveToDatabase();
        }
        System.out.println("10 books added to the database.");
    }
    
    public static Book[] loadBooksFromDatabase() {
        java.util.List<Book> bookList = new java.util.ArrayList<>();
        String sql = "SELECT id, title, author FROM books";
        try (java.sql.Connection conn = Database.connect();
             java.sql.Statement stmt = conn.createStatement();
             java.sql.ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String id = rs.getString("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                bookList.add(new Book(id, title, author));
            }
        } catch (Exception e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
        return bookList.toArray(new Book[0]);
    }

    public static int countBooksInDatabase() {
    String sql = "SELECT COUNT(*) FROM books";
    try (java.sql.Connection conn = Database.connect();
         java.sql.Statement stmt = conn.createStatement();
         java.sql.ResultSet rs = stmt.executeQuery(sql)) {
        if (rs.next()) {
            return rs.getInt(1);
        }
    } catch (Exception e) {
        System.out.println("Error counting books: " + e.getMessage());
    }
    return 0;
}*/




        