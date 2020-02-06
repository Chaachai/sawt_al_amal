package com.example.sawt_al_amal.bean;

//CHAACHAI Youssef

public class Category {

    private int id;
    private String libelle;

    public Category() {
    }

    public Category(int id) {
        this.id = id;
    }

    public Category(String libelle) {
        this.libelle = libelle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
