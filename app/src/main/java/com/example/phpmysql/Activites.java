package com.example.phpmysql;

public class Activites {
    private String id_Act;
    private String nom;
    private String lieu;
    private String desc;
    private String prix;
    private String dd;
    private String df;




    public Activites() {
    }

    public Activites(String id_Act, String nom, String lieu, String desc, String prix, String dd, String df) {
        this.id_Act = id_Act;
        this.nom = nom;
        this.lieu = lieu;
        this.desc = desc;
        this.prix = prix;
        this.dd = dd;
        this.df = df;
    }

    public String getNom() {
        return nom;
    }

    public String getId_Act() {
        return id_Act;
    }

    public void setId_Act(String id_Act) {
        this.id_Act = id_Act;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }

    public String getDf() {
        return df;
    }

    public void setDf(String df) {
        this.df = df;
    }
}
