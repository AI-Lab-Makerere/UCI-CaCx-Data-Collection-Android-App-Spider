package com.ug.cancerapp.Models;

public class Entry {

    String studyID;
    int age;
    String instanceID;

    public Entry(String studyID, int age, String instanceID) {
        this.studyID = studyID;
        this.age = age;
        this.instanceID = instanceID;
    }

    public String getStudyID() {
        return studyID;
    }

    public int getAge() {
        return age;
    }

    public String getInstanceID() {
        return instanceID;
    }
}
