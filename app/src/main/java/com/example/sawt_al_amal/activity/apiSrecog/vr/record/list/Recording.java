package com.example.sawt_al_amal.activity.apiSrecog.vr.record.list;

import java.io.Serializable;
//FEKRANE Zakaria
//// la classe qui permet de recuperer les données
public class Recording implements Serializable {

    private byte[] image;

    private final String name;

    private final Long timestamp;
/////constructeur
    public Recording(String name, Long timestamp, byte[] image) {
        this.name = name;
        this.timestamp = timestamp;
        this.image = image;
    }

    public Recording(String name, Long timestamp) {
        this.name = name;
        this.timestamp = timestamp;
    }

    public byte[] getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public Long getTimestamp() {
        return timestamp;
    }

}
