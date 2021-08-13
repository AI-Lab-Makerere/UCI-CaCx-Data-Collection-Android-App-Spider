/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Models;

public class Review {

    String studyID;

    public Review(String studyID) {
        this.studyID = studyID;
    }

    public String getStudyID() {
        return studyID;
    }
}
