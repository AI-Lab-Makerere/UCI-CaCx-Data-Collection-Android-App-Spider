package com.ug.cancerapp.Models;

public class Gyneco {
    
    String notes;
    String viaResult;

    public Gyneco(String notes, String viaResult) {
        this.notes = notes;
        this.viaResult = viaResult;
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
}
