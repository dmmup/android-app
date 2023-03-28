package com.example.phpmysql;

public class Users {
    private String id_User;
    private String nom;
    private String prenom;
    private String email;
    private String note;

    public Users() {
    }

    public Users(String id_User, String nom, String prenom, String email, String note) {
        this.id_User = id_User;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.note = note;
    }

    public Users(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getId_User() {
        return id_User;
    }

    public void setId_User(String id_User) {
        this.id_User = id_User;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
