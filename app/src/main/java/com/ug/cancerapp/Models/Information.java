package com.ug.cancerapp.Models;

import java.util.Date;

public class Information {

    private String instanceID;
    private String studyID, initials, district, county, village;
    private int age;
    private String symptoms, selectSymptoms, otherSymptoms;
    private String priorCaCxScreening, pastScreeningMethod, pastCaCxScreeningResults, priorTreatment;
    private String whenLastScreening;
    private String hivStatus, onHAART;
    private String hAARTDuration;
    private String untitled67;
    private String lnmp;
    private String parity, abortions;
    private String contraceptives, otherContraceptive;
    private String viaResult, nurse_notes;

    public Information(String instanceID, String studyID, String initials, String district, String county,
                       String village, int age, String symptoms, String selectSymptoms, String otherSymptoms,
                       String priorCaCxScreening, String pastScreeningMethod, String pastCaCxScreeningResults,
                       String priorTreatment, String whenLastScreening, String hivStatus, String onHAART,
                       String hAARTDuration, String untitled67, String lnmp, String parity, String abortions,
                       String contraceptives, String otherContraceptive, String viaResult, String nurse_notes) {
        this.instanceID = instanceID;
        this.studyID = studyID;
        this.initials = initials;
        this.district = district;
        this.county = county;
        this.village = village;
        this.age = age;
        this.symptoms = symptoms;
        this.selectSymptoms = selectSymptoms;
        this.otherSymptoms = otherSymptoms;
        this.priorCaCxScreening = priorCaCxScreening;
        this.pastScreeningMethod = pastScreeningMethod;
        this.pastCaCxScreeningResults = pastCaCxScreeningResults;
        this.priorTreatment = priorTreatment;
        this.whenLastScreening = whenLastScreening;
        this.hivStatus = hivStatus;
        this.onHAART = onHAART;
        this.hAARTDuration = hAARTDuration;
        this.untitled67 = untitled67;
        this.lnmp = lnmp;
        this.parity = parity;
        this.abortions = abortions;
        this.contraceptives = contraceptives;
        this.otherContraceptive = otherContraceptive;
        this.viaResult = viaResult;
        this.nurse_notes = nurse_notes;
    }

    public String getInstanceID() {
        return instanceID;
    }

    public String getStudyID() {
        return studyID;
    }

    public String getInitials() {
        return initials;
    }

    public String getDistrict() {
        return district;
    }

    public String getCounty() {
        return county;
    }

    public String getVillage() {
        return village;
    }

    public int getAge() {
        return age;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public String getSelectSymptoms() {
        return selectSymptoms;
    }

    public String getOtherSymptoms() {
        return otherSymptoms;
    }

    public String getPriorCaCxScreening() {
        return priorCaCxScreening;
    }

    public String getPastScreeningMethod() {
        return pastScreeningMethod;
    }

    public String getPastCaCxScreeningResults() {
        return pastCaCxScreeningResults;
    }

    public String getPriorTreatment() {
        return priorTreatment;
    }

    public String getWhenLastScreening() {
        return whenLastScreening;
    }

    public String getHivStatus() {
        return hivStatus;
    }

    public String getOnHAART() {
        return onHAART;
    }

    public String gethAARTDuration() {
        return hAARTDuration;
    }

    public String getUntitled67() {
        return untitled67;
    }

    public String getLnmp() {
        return lnmp;
    }

    public String getParity() {
        return parity;
    }

    public String getAbortions() {
        return abortions;
    }

    public String getContraceptives() {
        return contraceptives;
    }

    public String getOtherContraceptive() {
        return otherContraceptive;
    }

    public String getViaResult() {
        return viaResult;
    }

    public String getNurse_notes() {
        return nurse_notes;
    }
}
