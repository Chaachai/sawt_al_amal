package com.example.sawt_al_amal.bean;

public class Niveau {

    private int id;
    private String nom;
    private String description;
    private byte[] icon;
    private int reqPoints;


    public Niveau() {
    }

    public Niveau(int id, String nom, String description, byte[] icon, int reqPoints) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.icon = icon;
        this.reqPoints = reqPoints;
    }

    public Niveau(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public int getReqPoints() {
        return reqPoints;
    }

    public void setReqPoints(int reqPoints) {
        this.reqPoints = reqPoints;
    }
}
