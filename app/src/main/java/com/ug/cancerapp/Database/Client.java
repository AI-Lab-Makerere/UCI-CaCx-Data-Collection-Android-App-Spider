/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Nurses")
public class Client {

    @PrimaryKey(autoGenerate = true)
    long key;
    int id;
    int healthy_facility_id;
    String name, email;

    public Client(int id, int healthy_facility_id, String name, String email) {
        this.id = id;
        this.healthy_facility_id = healthy_facility_id;
        this.name = name;
        this.email = email;
    }

    public long getKey() {
        return key;
    }

    public int getId() {
        return id;
    }

    public int getHealthy_facility_id() {
        return healthy_facility_id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
