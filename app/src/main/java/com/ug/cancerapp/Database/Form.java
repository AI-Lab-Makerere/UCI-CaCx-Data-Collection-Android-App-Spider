package com.ug.cancerapp.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Cervix_Screening")
public class Form {

    @PrimaryKey(autoGenerate = true)
    long key;
    String date;
    String studyID, initials, district, county, village;
    int age;
    String have_symptoms, symptoms, other_symptoms;
    String screened_for_cancer, last_screened, screening_process, treatment, screening_results;
    String hiv_status, on_haart;
    int years_on_haart;
    String pregnant, last_menstrual;
    int parity, abortion;
    String on_contraceptives, contraceptives;
    String image1, image2, image3, image4;
    String via, location, notes;
    String diagnosis;

//    public Form(long key, String date, String studyID, String initials, String district, String county,
//                String village, int age, String have_symptoms, String symptoms, String other_symptoms,
//                String screened_for_cancer, String last_screened, String screening_process, String treatment,
//                String screening_results, String hiv_status, String on_haart, int years_on_haart, String pregnant,
//                String last_menstrual, int parity, int abortion, String on_contraceptives, String contraceptives,
//                String image1, String image2, String image3, String image4, String via, String location,
//                String notes, String diagnosis) {
//        this.key = key;
//        this.date = date;
//        this.studyID = studyID;
//        this.initials = initials;
//        this.district = district;
//        this.county = county;
//        this.village = village;
//        this.age = age;
//        this.have_symptoms = have_symptoms;
//        this.symptoms = symptoms;
//        this.other_symptoms = other_symptoms;
//        this.screened_for_cancer = screened_for_cancer;
//        this.last_screened = last_screened;
//        this.screening_process = screening_process;
//        this.treatment = treatment;
//        this.screening_results = screening_results;
//        this.hiv_status = hiv_status;
//        this.on_haart = on_haart;
//        this.years_on_haart = years_on_haart;
//        this.pregnant = pregnant;
//        this.last_menstrual = last_menstrual;
//        this.parity = parity;
//        this.abortion = abortion;
//        this.on_contraceptives = on_contraceptives;
//        this.contraceptives = contraceptives;
//        this.image1 = image1;
//        this.image2 = image2;
//        this.image3 = image3;
//        this.image4 = image4;
//        this.via = via;
//        this.location = location;
//        this.notes = notes;
//        this.diagnosis = diagnosis;
//    }

    public String getStudyID() {
        return studyID;
    }

    public void setStudyID(String studyID) {
        this.studyID = studyID;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHave_symptoms() {
        return have_symptoms;
    }

    public void setHave_symptoms(String have_symptoms) {
        this.have_symptoms = have_symptoms;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getOther_symptoms() {
        return other_symptoms;
    }

    public void setOther_symptoms(String other_symptoms) {
        this.other_symptoms = other_symptoms;
    }

    public String getScreened_for_cancer() {
        return screened_for_cancer;
    }

    public void setScreened_for_cancer(String screened_for_cancer) {
        this.screened_for_cancer = screened_for_cancer;
    }

    public String getLast_screened() {
        return last_screened;
    }

    public void setLast_screened(String last_screened) {
        this.last_screened = last_screened;
    }

    public String getScreening_process() {
        return screening_process;
    }

    public void setScreening_process(String screening_process) {
        this.screening_process = screening_process;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getHiv_status() {
        return hiv_status;
    }

    public void setHiv_status(String hiv_status) {
        this.hiv_status = hiv_status;
    }

    public String getOn_haart() {
        return on_haart;
    }

    public void setOn_haart(String on_haart) {
        this.on_haart = on_haart;
    }

    public int getYears_on_haart() {
        return years_on_haart;
    }

    public void setYears_on_haart(int years_on_haart) {
        this.years_on_haart = years_on_haart;
    }

    public String getPregnant() {
        return pregnant;
    }

    public void setPregnant(String pregnant) {
        this.pregnant = pregnant;
    }

    public String getLast_menstrual() {
        return last_menstrual;
    }

    public void setLast_menstrual(String last_menstrual) {
        this.last_menstrual = last_menstrual;
    }

    public int getParity() {
        return parity;
    }

    public void setParity(int parity) {
        this.parity = parity;
    }

    public int getAbortion() {
        return abortion;
    }

    public void setAbortion(int abortion) {
        this.abortion = abortion;
    }

    public String getOn_contraceptives() {
        return on_contraceptives;
    }

    public void setOn_contraceptives(String on_contraceptives) {
        this.on_contraceptives = on_contraceptives;
    }

    public String getContraceptives() {
        return contraceptives;
    }

    public void setContraceptives(String contraceptives) {
        this.contraceptives = contraceptives;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getScreening_results() {
        return screening_results;
    }

    public void setScreening_results(String screening_results) {
        this.screening_results = screening_results;
    }
}
