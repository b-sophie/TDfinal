package com.buydens;

import com.buydens.Library.Book;
import com.buydens.Library.User;
import java.util.List;

public class DebugBorrow {
    public static void main(String[] args) {
        Database.initialize();
        MainController.populateBooks();

        List<Book> books = MainController.loadBooks();
        if (books.size() == 0) {
            System.out.println("No books in DB");
            return;
        }

        // pick B1
        Book b1 = null;
        for (Book b : books) {
            if ("B1".equals(b.getId())) {
                b1 = b;
                break;
            }
        }

        if (b1 == null) {
            System.out.println("B1 not found");
            return;
        }

        System.out.println("Before borrow: " + b1.getId() + " - " + b1.getTitle() + " (stock:" + b1.getStock() + ")");

        User thomas = MainController.authenticateUser("thomas", "1234");
        if (thomas == null) {
            System.out.println("User not found");
            return;
        }

        boolean ok = ManagerEmprunt.emprunter(b1, thomas);
        System.out.println("Borrow result: " + ok);

        // reload to check DB persisted
        List<Book> after = MainController.loadBooks();
        for (Book b : after) {
            if ("B1".equals(b.getId())) {
                System.out.println("After borrow DB: " + b.getId() + " - " + b.getTitle() + " (stock:" + b.getStock() + ")");
            }
        }
    }
}
