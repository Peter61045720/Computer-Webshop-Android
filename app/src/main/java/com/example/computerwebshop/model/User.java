package com.example.computerwebshop.model;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String userUID;
    private String username;
    private String phone;
    private String country;
    private String photoID;

    public User(String userUID, String username, String phone, String country, String photoID) {
        this.userUID = userUID;
        this.username = username;
        this.phone = phone;
        this.country = country;
        this.photoID = photoID;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhotoID() {
        return photoID;
    }

    public void setPhotoID(String photoID) {
        this.photoID = photoID;
    }

    public Map<String, Object> getUserMap() {
        Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("phone", phone);
        user.put("country", country);
        user.put("photo", photoID);
        return user;
    }
}
