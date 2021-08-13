/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

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
