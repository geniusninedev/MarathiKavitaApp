package com.geniusnine.android.marathikavita;

/**
 * Created by Dev on 08-03-2017.
 */

public class Feedback {
    @com.google.gson.annotations.SerializedName("id")
    private String id ;
    @com.google.gson.annotations.SerializedName("firebaseid")
    private String firebaseid;
    @com.google.gson.annotations.SerializedName("appid")
    private String appid;
    @com.google.gson.annotations.SerializedName("subject")
    private String subject;
    @com.google.gson.annotations.SerializedName("description")
    private String description;

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

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Feedback() {
    }
}
