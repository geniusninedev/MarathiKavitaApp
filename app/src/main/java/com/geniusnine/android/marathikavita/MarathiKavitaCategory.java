package com.geniusnine.android.marathikavita;

/**
 * Created by Supriya on 13-03-2017.
 */

public class MarathiKavitaCategory {

    @com.google.gson.annotations.SerializedName("category")
    private String category;
    @com.google.gson.annotations.SerializedName("id")
    private  String id;
    @com.google.gson.annotations.SerializedName("imagelink")
    private String imagelink;


    public  MarathiKavitaCategory(){//Change the parameter

    }

    public String getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImagelink() {
        return imagelink;
    }

    public void setImagelink(String imagelink) {
        this.imagelink = imagelink;
    }
}
