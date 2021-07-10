package com.ug.cancerapp.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Case {

    String instanceID;
    String studyID;
    int age;
    String viaResult;
    String ml_via_result;
    @SerializedName("gynecologist_reviews")
    List<Gyneco> gyneco;

    public Case(String instanceID, String studyID, int age, String viaResult, String ml_via_result, List<Gyneco> gyneco) {
        this.instanceID = instanceID;
        this.studyID = studyID;
        this.age = age;
        this.viaResult = viaResult;
        this.ml_via_result = ml_via_result;
        this.gyneco = gyneco;
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

    public String getViaResults() {
        return viaResult;
    }

    public List<Gyneco> getGyneco() {
        return gyneco;
    }

    public String getViaResult() {
        return viaResult;
    }

    public String getMl_via_result() {
        return ml_via_result;
    }
}
