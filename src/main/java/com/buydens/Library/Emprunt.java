package com.buydens.Library;

import java.time.LocalDate;

public class Emprunt {

    private Book book;
    private User utilisateur;
    private LocalDate dateEmprunt;

    public Emprunt(Book book, User utilisateur, LocalDate dateEmprunt) {
        this.book = book;
        this.utilisateur = utilisateur;
        this.dateEmprunt = dateEmprunt;
    }

    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }

    public User getUtilisateur() {
        return utilisateur;
    }
    public void setUtilisateur(User utilisateur) {
        this.utilisateur = utilisateur;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }
    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    /*public LocalDate getDateRetour() {
        return dateRetour;
    }
    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    } */
}

