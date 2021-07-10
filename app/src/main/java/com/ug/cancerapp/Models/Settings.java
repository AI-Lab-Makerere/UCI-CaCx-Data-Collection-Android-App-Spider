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
