package com.example.sawt_al_amal.util;

public class SessionItem {

    private String key;

    private Object object;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "SessionItem{" + "key=" + key + ", object=" + object + '}';
    }


}