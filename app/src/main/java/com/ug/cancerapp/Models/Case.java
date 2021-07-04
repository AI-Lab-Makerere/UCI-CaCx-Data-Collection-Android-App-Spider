package com.ug.cancerapp.Models;

public class Case {

    String instanceID;
    String studyID;
    int age;
    String viaResult;

    public Case(String instanceID, String studyID, int age, String viaResult) {
        this.instanceID = instanceID;
        this.studyID = studyID;
        this.age = age;
        this.viaResult = viaResult;
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
}
