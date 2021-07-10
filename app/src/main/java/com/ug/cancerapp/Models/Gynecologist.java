package com.ug.cancerapp.Models;

public class Gynecologist {

    String instanceID;
    String studyId;
    String age;
    String nurse;
    String gyneNotes;
    String gyneVia;
    String ml_via_result;

    public Gynecologist(String instanceID, String studyId, String age, String nurse, String gyneNotes, String gyneVia, String ml_via_result) {
        this.instanceID = instanceID;
        this.studyId = studyId;
        this.age = age;
        this.nurse = nurse;
        this.gyneNotes = gyneNotes;
        this.gyneVia = gyneVia;
        this.ml_via_result = ml_via_result;
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

    public String getGyneNotes() {
        return gyneNotes;
    }

    public String getGyneVia() {
        return gyneVia;
    }

    public String getMl_via_result() {
        return ml_via_result;
    }
}
