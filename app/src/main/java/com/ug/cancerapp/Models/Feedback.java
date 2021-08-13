/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Models;

public class Feedback {

    String instanceID;
    String via_results;
    String username;
    String notes;

    public Feedback(String instanceID, String via_results, String username, String notes) {
        this.instanceID = instanceID;
        this.via_results = via_results;
        this.username = username;
        this.notes = notes;
    }

    public String getInstanceID() {
        return instanceID;
    }

    public void setInstanceID(String instanceID) {
        this.instanceID = instanceID;
    }

    public String getVia_results() {
        return via_results;
    }

    public void setVia_results(String via_results) {
        this.via_results = via_results;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
