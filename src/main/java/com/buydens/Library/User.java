package com.buydens.Library;

import java.util.Objects;

public class User {
    private static long idCounter = 0;
    private int id;
    private String pseudo;
    private String mdp;
    private String role;

public User(int id, String pseudo, String mdp, String role) {
    this.id=id;
    this.pseudo=pseudo;
    this.mdp=mdp;
    this.role=role;
}

public User(String pseudo, String mdp, String role) {
    this.id=(int) idCounter++;
    this.pseudo=pseudo;
    this.mdp=mdp;
    this.role=role;
}

public int getId() {
        return id;
    }
public String getPseudo() {
        return pseudo;
    }

    public String getMdp() {
        return mdp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        System.out.println("Role demandé: " + role);
        return role;
    }

    public boolean isAdmin(){
        return "ADMIN".equalsIgnoreCase(role);
    }

    public String isrole() {
        return role;
    }
    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
