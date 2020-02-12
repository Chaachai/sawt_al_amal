package com.example.sawt_al_amal.bean;


//CHAACHAI Youssef


public class Cours {

    private int id;
    private String nom;
    private Niveau niveau;

    public Cours() {
    }

    public Cours(int id) {
        this.id = id;
    }

    public Cours(int id, String nom, int id_niveau) {
        this.id = id;
        this.nom = nom;
        this.niveau = new Niveau(id_niveau);
    }

    public Cours(final String nom, final Niveau niveau) {
        this.nom = nom;
        this.niveau = niveau;
    }

    public String getNom() {
        return nom;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Cours{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", niveau=" + niveau.getId() +
                '}';
    }
}
