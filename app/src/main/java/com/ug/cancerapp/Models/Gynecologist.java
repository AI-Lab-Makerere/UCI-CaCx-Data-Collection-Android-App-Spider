package com.ug.cancerapp.Models;

public class Gynecologist {

    String instanceID;
    String studyId;
    String age;
    String nurse;

    public Gynecologist(String instanceID, String studyId, String age, String nurse) {
        this.instanceID = instanceID;
        this.studyId = studyId;
        this.age = age;
        this.nurse = nurse;
    }

    public String getInstanceID() {
        return instanceID;
    }

    public void setInstanceID(String instanceID) {
        this.instanceID = instanceID;
    }

    public String getStudyId() {
        return studyId;
    }

    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNurse() {
        return nurse;
    }

    public void setNurse(String nurse) {
        this.nurse = nurse;
    }
}
