package com.geniusnine.android.marathikavita;

/**
 * Created by Dev on 05-03-2017.
 */

public class UserFacebookData {
    @com.google.gson.annotations.SerializedName("id")
    private String id ;
    @com.google.gson.annotations.SerializedName("firebaseid")
    private String firebaseid;
    @com.google.gson.annotations.SerializedName("facebookid")
    private String facebookid;
    @com.google.gson.annotations.SerializedName("email")
    private String email;
    @com.google.gson.annotations.SerializedName("username")
    private String username;
    @com.google.gson.annotations.SerializedName("gender")
    private String gender;

    public UserFacebookData() {
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

    public String getFacebookid() {
        return facebookid;
    }

    public void setFacebookid(String facebookid) {
        this.facebookid = facebookid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
