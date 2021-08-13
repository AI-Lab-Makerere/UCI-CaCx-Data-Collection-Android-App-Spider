/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Models;

public class Nurse {

    String name;
    int id;
    String email;
    int health_facility_id;

    public Nurse(String name, int id, String email, int health_facility_id) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.health_facility_id = health_facility_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getHealth_facility_id() {
        return health_facility_id;
    }

    public void setHealth_facility_id(int health_facility_id) {
        this.health_facility_id = health_facility_id;
    }
}
