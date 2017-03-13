package com.geniusnine.android.marathikavita;

/**
 * Created by AndriodDev8 on 06-03-2017.
 */

public class AppUsers {

    @com.google.gson.annotations.SerializedName("appname")
    private String appname;
    @com.google.gson.annotations.SerializedName("id")
    private  String id;
    @com.google.gson.annotations.SerializedName("firebaseid")
    private String firebaseid;

    public AppUsers() {
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirebaseid() {
        return firebaseid;
    }

    public void setFirebaseid(String firebaseid) {
        this.firebaseid = firebaseid;
    }
}
