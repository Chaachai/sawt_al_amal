package com.example.sawt_al_amal.bean;

//CHAACHAI Youssef

public class Geste {

    private int id;

    private byte[] gif;

    private byte[] image;

    private String text;

    private Cours cours;

    private Category category;


    public Geste() {
    }

    public Geste(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getGif() {
        return gif;
    }

    public void setGif(byte[] gif) {
        this.gif = gif;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
