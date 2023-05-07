package com.example.computerwebshop.model;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String username;
    private String phone;
    private String country;
    private String photo;

    public User() {}

    public User(String username, String phone, String country, String photo) {
        this.username = username;
        this.phone = phone;
        this.country = country;
        this.photo = photo;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Map<String, Object> _getUserMap() {
        Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("phone", phone);
        user.put("country", country);
        user.put("photo", photo);
        return user;

    }
}
