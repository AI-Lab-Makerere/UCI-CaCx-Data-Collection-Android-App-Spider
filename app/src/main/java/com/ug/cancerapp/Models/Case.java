package com.ug.cancerapp.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Case {

    String instanceID;
    String studyID;
    int age;
    @SerializedName("created_at")
    String date;
    String initials;
    String viaResult;
    String ml_via_result;
    @SerializedName("gynecologist_reviews")
    List<Gyneco> gyneco;
    @SerializedName("mlresults")
    List<Mlresults> mlresults;

    public Case(String instanceID, String studyID, int age, String date, String initials,
                String viaResult, String ml_via_result, List<Gyneco> gyneco, List<Mlresults> mlresults) {
        this.instanceID = instanceID;
        this.studyID = studyID;
        this.age = age;
        this.date = date;
        this.initials = initials;
        this.viaResult = viaResult;
        this.ml_via_result = ml_via_result;
        this.gyneco = gyneco;
        this.mlresults = mlresults;
    }

    public String getInstanceID() {
        return instanceID;
    }

    public String getStudyID() {
        return studyID;
    }

    public int getAge() {
        return age;
    }

    public String getDate() {
        return date;
    }

    public List<Gyneco> getGyneco() {
        return gyneco;
    }

    public List<Mlresults> getMlresults() {
        return mlresults;
    }

    public String getInitials() {
        return initials;
    }

    public String getViaResult() {
        return viaResult;
    }

    public String getMl_via_result() {
        return ml_via_result;
    }
}
