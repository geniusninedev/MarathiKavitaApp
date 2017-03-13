package com.geniusnine.android.marathikavita;

/**
 * Created by Dev on 08-03-2017.
 */

public class OrderApp {

    @com.google.gson.annotations.SerializedName("id")
    private String id ;
    @com.google.gson.annotations.SerializedName("firebaseid")
    private String firebaseid;
    @com.google.gson.annotations.SerializedName("appid")
    private String appid;
    @com.google.gson.annotations.SerializedName("device")
    private String device;
    @com.google.gson.annotations.SerializedName("os")
    private String os;
    @com.google.gson.annotations.SerializedName("apptype")
    private String apptype;
    @com.google.gson.annotations.SerializedName("industry")
    private String industry;
    @com.google.gson.annotations.SerializedName("description")
    private String description;
    @com.google.gson.annotations.SerializedName("phone")
    private String phone;
    @com.google.gson.annotations.SerializedName("email")
    private String email;

    public OrderApp() {
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

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getApptype() {
        return apptype;
    }

    public void setApptype(String apptype) {
        this.apptype = apptype;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
