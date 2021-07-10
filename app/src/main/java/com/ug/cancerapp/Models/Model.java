package com.ug.cancerapp.Models;

import com.google.gson.annotations.SerializedName;

public class Model {

    String viaResult;
    String notes;
    @SerializedName("entry")
    Entry entry;

    public Model(String viaResult, String notes, Entry entry) {
        this.viaResult = viaResult;
        this.notes = notes;
        this.entry = entry;
    }

    public String getViaResult() {
        return viaResult;
    }

    public String getNotes() {
        return notes;
    }

    public Entry getEntry() {
        return entry;
    }
}
