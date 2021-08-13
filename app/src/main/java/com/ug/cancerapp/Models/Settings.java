/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Models;

public class Settings {

    float positive_analysis_threshold;

    public Settings(float positive_analysis_threshold) {
        this.positive_analysis_threshold = positive_analysis_threshold;
    }

    public float getPositive_analysis_threshold() {
        return positive_analysis_threshold;
    }
}
