/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

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
