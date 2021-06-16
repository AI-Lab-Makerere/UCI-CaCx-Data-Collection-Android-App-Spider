package com.ug.cancerapp.Models;

public class User {

    private String email;
    private String password;
    private String device_name;

    public User(String email, String password, String device_name) {
        this.email = email;
        this.password = password;
        this.device_name = device_name;
    }
}
