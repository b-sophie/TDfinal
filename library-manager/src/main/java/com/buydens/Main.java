    

package com.buydens;
import com.buydens.Library.Book;


public class Main {
    public static void main(String[] args) {
        Database.initialize();   // Create database + tables

        System.out.println("Library System Started!");

        // Book[] books = populateBooks();  
        Book[] books = loadBooksFromDatabase();

         // Delete a book      
        books[3].deleteBookFromDatabase();

        System.out.println("Book successfully deleted.");

                
    }
    public static Book[] populateBooks() {
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
        return books;
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
}


        