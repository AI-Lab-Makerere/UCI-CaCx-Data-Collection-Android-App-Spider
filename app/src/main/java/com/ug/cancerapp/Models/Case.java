package com.ug.cancerapp.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Case {

    String instanceID;
    String studyID;
    int age;
    String date;
    @SerializedName("gynecologist_reviews")
    List<Gyneco> gyneco;
    @SerializedName("mlresults")
    List<Mlresults> mlresults;

    public Case(String instanceID, String studyID, int age, String date, List<Gyneco> gyneco, List<Mlresults> mlresults) {
        this.instanceID = instanceID;
        this.studyID = studyID;
        this.age = age;
        this.date = date;
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
}
