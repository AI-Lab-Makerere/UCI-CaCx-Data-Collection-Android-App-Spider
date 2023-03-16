/*
 * Copyright (c) 2023. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Models;

public class Nurses {

    int id;
    int health_facility_id;
    String name;
    String email;

    public Nurses() {

    }

    public Nurses(int id, int health_facility_id, String name, String email) {
        this.id = id;
        this.health_facility_id = health_facility_id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHealth_facility_id() {
        return health_facility_id;
    }

    public void setHealth_facility_id(int health_facility_id) {
        this.health_facility_id = health_facility_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
