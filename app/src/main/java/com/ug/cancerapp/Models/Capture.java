/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class Capture {

    private String instanceID;
    private Date entry_date;
    private String username;
    private int health_facility_id;
    private String studyID, initials, district, county, zone;
    private int age;
    private String symptoms, more_symptoms, other_symptoms;
    private String cancer_screening, screening_method, past_screening_results, treatment_given;
    private Date last_screening_date;
    private String hiv_status, on_haart;
    private int years_on_haart;
    private String patient_pregnant;
    private Date last_known_menstrual_period_date;
    private int parity, abortions;
    private String contraceptives, other_contraceptives;
    private String lesionLocation, via_results, nurse_notes;
    private String ml_via_result;
    private Boolean require_clinician_review;
    private String other_nurses;
    private float positive_threshold;
    private String model_version;

    @SerializedName("picture1_before")
    private Picture picture1_before;

    @SerializedName("picture2_before")
    private Picture picture2_before;

    @SerializedName("picture3_after")
    private Picture picture3_after;

    @SerializedName("picture4_after")
    private Picture picture4_after;

    public Capture(String instanceID, Date entry_date, String username, int health_facility_id,
                   String studyID, String initials, String district, String county, String zone,
                   int age, String symptoms, String more_symptoms, String other_symptoms,
                   String cancer_screening, String screening_method, String past_screening_results,
                   String treatment_given, Date last_screening_date, String hiv_status, String on_haart,
                   int years_on_haart, String patient_pregnant, Date last_known_menstrual_period_date,
                   int parity, int abortions, String contraceptives, String other_contraceptives,
                   String lesionLocation, String via_results, String nurse_notes, String ml_via_result,
                   Boolean require_clinician_review, String other_nurses, float positive_threshold,
                   String model_version, Picture picture1_before, Picture picture2_before,
                   Picture picture3_after, Picture picture4_after) {
        this.instanceID = instanceID;
        this.entry_date = entry_date;
        this.username = username;
        this.health_facility_id = health_facility_id;
        this.studyID = studyID;
        this.initials = initials;
        this.district = district;
        this.county = county;
        this.zone = zone;
        this.age = age;
        this.symptoms = symptoms;
        this.more_symptoms = more_symptoms;
        this.other_symptoms = other_symptoms;
        this.cancer_screening = cancer_screening;
        this.screening_method = screening_method;
        this.past_screening_results = past_screening_results;
        this.treatment_given = treatment_given;
        this.last_screening_date = last_screening_date;
        this.hiv_status = hiv_status;
        this.on_haart = on_haart;
        this.years_on_haart = years_on_haart;
        this.patient_pregnant = patient_pregnant;
        this.last_known_menstrual_period_date = last_known_menstrual_period_date;
        this.parity = parity;
        this.abortions = abortions;
        this.contraceptives = contraceptives;
        this.other_contraceptives = other_contraceptives;
        this.lesionLocation = lesionLocation;
        this.via_results = via_results;
        this.nurse_notes = nurse_notes;
        this.ml_via_result = ml_via_result;
        this.require_clinician_review = require_clinician_review;
        this.other_nurses = other_nurses;
        this.positive_threshold = positive_threshold;
        this.model_version = model_version;
        this.picture1_before = picture1_before;
        this.picture2_before = picture2_before;
        this.picture3_after = picture3_after;
        this.picture4_after = picture4_after;
    }
}