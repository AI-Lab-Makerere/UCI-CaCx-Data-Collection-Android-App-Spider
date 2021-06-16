package com.ug.cancerapp.Models;

import java.util.ArrayList;
import java.util.Date;

public class Capture {

    private Date entry_date;
    private String username;
    private String area;
    private String studyID, initials, district, county, village;
    private int age;
    private String symptoms, other_symptoms;
    private String more_symptoms;
    private String cancer_screening, screening_method, past_screening_results, treatment_given;
    private Date last_screening_date;
    private String hiv_status, on_haart;
    private int years_on_haart;
    private String patient_pregnant;
    private Date last_known_menstrual_period_date;
    private int parity, abortions;
    private String contraceptives, other_contraceptives;
    private String picture1_before, picture2_before, picture3_after, picture4_after;
    private String location_of_lesion, via_results, nurse_notes;

    public Capture(Date entry_date, String username, String area, String studyID, String initials, String district, String county, String village, int age, String symptoms, String other_symptoms, String more_symptoms, String cancer_screening, String screening_method, String past_screening_results, String treatment_given, Date last_screening_date, String hiv_status, String on_haart, int years_on_haart, String patient_pregnant, Date last_known_menstrual_period_date, int parity, int abortions, String contraceptives, String other_contraceptives, String picture1_before, String picture2_before, String picture3_after, String picture4_after, String location_of_lesion, String via_results, String nurse_notes) {
        this.entry_date = entry_date;
        this.username = username;
        this.area = area;
        this.studyID = studyID;
        this.initials = initials;
        this.district = district;
        this.county = county;
        this.village = village;
        this.age = age;
        this.symptoms = symptoms;
        this.other_symptoms = other_symptoms;
        this.more_symptoms = more_symptoms;
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
        this.picture1_before = picture1_before;
        this.picture2_before = picture2_before;
        this.picture3_after = picture3_after;
        this.picture4_after = picture4_after;
        this.location_of_lesion = location_of_lesion;
        this.via_results = via_results;
        this.nurse_notes = nurse_notes;
    }
}
