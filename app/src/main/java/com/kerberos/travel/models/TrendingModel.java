package com.kerberos.travel.models;

import android.graphics.drawable.Drawable;

public class TrendingModel {
    private String d, description, title;

    public TrendingModel(String d, String description, String title, int drawable){
        this.d = d;
        this.description = description;
        this.title = title;
        this.image = drawable;
    }

    private int image;

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }
}
