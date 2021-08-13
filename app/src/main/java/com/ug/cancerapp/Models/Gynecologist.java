/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Models;

public class Gynecologist {

    String instanceID;
    String studyId;
    String age;
    String date;
    String viaResults;
    String initials;
    String gynResults;
    String gynNotes;

    public Gynecologist(String instanceID, String studyId, String age, String date, String viaResults,
                        String initials, String gynResults, String gynNotes) {
        this.instanceID = instanceID;
        this.studyId = studyId;
        this.age = age;
        this.date = date;
        this.viaResults = viaResults;
        this.initials = initials;
        this.gynResults = gynResults;
        this.gynNotes = gynNotes;
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

    public String getDate() {
        return date;
    }

    public String getViaResults() {
        return viaResults;
    }

    public String getInitials() {
        return initials;
    }

    public String getGynResults() {
        return gynResults;
    }

    public String getGynNotes() {
        return gynNotes;
    }
}
