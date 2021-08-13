/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Models;

import com.google.gson.annotations.SerializedName;

public class Gyneco {
    
    String notes;
    String viaResult;
    @SerializedName("created_at")
    String date;

    public Gyneco(String notes, String viaResult, String date) {
        this.notes = notes;
        this.viaResult = viaResult;
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getViaResult() {
        return viaResult;
    }

    public void setViaResult(String viaResult) {
        this.viaResult = viaResult;
    }

    public String getDate() {
        return date;
    }
}
