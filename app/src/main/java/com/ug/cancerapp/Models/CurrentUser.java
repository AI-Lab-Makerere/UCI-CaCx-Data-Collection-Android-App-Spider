/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Models;

public class CurrentUser {
    private String email;
    private int health_facility_id;
    private int user_id;
    private String health_facility_name;

    public CurrentUser(String email, int health_facility_id, int user_id, String health_facility_name) {
        this.email = email;
        this.health_facility_id = health_facility_id;
        this.user_id = user_id;
        this.health_facility_name = health_facility_name;
    }

    public String getEmail() {
        return email;
    }

    public int getHealth_facility_id() {
        return health_facility_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getHealth_facility_name() {
        return health_facility_name;
    }
}
