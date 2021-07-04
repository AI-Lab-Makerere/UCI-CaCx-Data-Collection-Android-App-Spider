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
    Boolean consult;
    float picture1_nc;
    float picture1_pc;
    String picture1_via;
    float picture2_nc;
    float picture2_pc;
    String picture2_via;
    float picture3_nc;
    float picture3_pc;
    String picture3_via;
    float picture4_nc;
    float picture4_pc;
    String picture4_via;
    String instanceID;

    public Form(String date, String studyID, String initials, String district, String county, String village, int age, String have_symptoms, String symptoms, String other_symptoms, String screened_for_cancer, String last_screened, String screening_process, String treatment, String screening_results, String hiv_status, String on_haart, int years_on_haart, String pregnant, String last_menstrual, int parity, int abortion, String on_contraceptives, String contraceptives, String image1, String image2, String image3, String image4, String via, String location, String notes, String diagnosis, Boolean consult, float picture1_nc, float picture1_pc, String picture1_via, float picture2_nc, float picture2_pc, String picture2_via, float picture3_nc, float picture3_pc, String picture3_via, float picture4_nc, float picture4_pc, String picture4_via, String instanceID) {
        this.date = date;
        this.studyID = studyID;
        this.initials = initials;
        this.district = district;
        this.county = county;
        this.village = village;
        this.age = age;
        this.have_symptoms = have_symptoms;
        this.symptoms = symptoms;
        this.other_symptoms = other_symptoms;
        this.screened_for_cancer = screened_for_cancer;
        this.last_screened = last_screened;
        this.screening_process = screening_process;
        this.treatment = treatment;
        this.screening_results = screening_results;
        this.hiv_status = hiv_status;
        this.on_haart = on_haart;
        this.years_on_haart = years_on_haart;
        this.pregnant = pregnant;
        this.last_menstrual = last_menstrual;
        this.parity = parity;
        this.abortion = abortion;
        this.on_contraceptives = on_contraceptives;
        this.contraceptives = contraceptives;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.via = via;
        this.location = location;
        this.notes = notes;
        this.diagnosis = diagnosis;
        this.consult = consult;
        this.picture1_nc = picture1_nc;
        this.picture1_pc = picture1_pc;
        this.picture1_via = picture1_via;
        this.picture2_nc = picture2_nc;
        this.picture2_pc = picture2_pc;
        this.picture2_via = picture2_via;
        this.picture3_nc = picture3_nc;
        this.picture3_pc = picture3_pc;
        this.picture3_via = picture3_via;
        this.picture4_nc = picture4_nc;
        this.picture4_pc = picture4_pc;
        this.picture4_via = picture4_via;
        this.instanceID = instanceID;
    }

    public long getKey() {
        return key;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public String getScreening_results() {
        return screening_results;
    }

    public void setScreening_results(String screening_results) {
        this.screening_results = screening_results;
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

    public Boolean getConsult() {
        return consult;
    }

    public void setConsult(Boolean consult) {
        this.consult = consult;
    }

    public float getPicture1_nc() {
        return picture1_nc;
    }

    public void setPicture1_nc(float picture1_nc) {
        this.picture1_nc = picture1_nc;
    }

    public float getPicture1_pc() {
        return picture1_pc;
    }

    public void setPicture1_pc(float picture1_pc) {
        this.picture1_pc = picture1_pc;
    }

    public String getPicture1_via() {
        return picture1_via;
    }

    public void setPicture1_via(String picture1_via) {
        this.picture1_via = picture1_via;
    }

    public float getPicture2_nc() {
        return picture2_nc;
    }

    public void setPicture2_nc(float picture2_nc) {
        this.picture2_nc = picture2_nc;
    }

    public float getPicture2_pc() {
        return picture2_pc;
    }

    public void setPicture2_pc(float picture2_pc) {
        this.picture2_pc = picture2_pc;
    }

    public String getPicture2_via() {
        return picture2_via;
    }

    public void setPicture2_via(String picture2_via) {
        this.picture2_via = picture2_via;
    }

    public float getPicture3_nc() {
        return picture3_nc;
    }

    public void setPicture3_nc(float picture3_nc) {
        this.picture3_nc = picture3_nc;
    }

    public float getPicture3_pc() {
        return picture3_pc;
    }

    public void setPicture3_pc(float picture3_pc) {
        this.picture3_pc = picture3_pc;
    }

    public String getPicture3_via() {
        return picture3_via;
    }

    public void setPicture3_via(String picture3_via) {
        this.picture3_via = picture3_via;
    }

    public float getPicture4_nc() {
        return picture4_nc;
    }

    public void setPicture4_nc(float picture4_nc) {
        this.picture4_nc = picture4_nc;
    }

    public float getPicture4_pc() {
        return picture4_pc;
    }

    public void setPicture4_pc(float picture4_pc) {
        this.picture4_pc = picture4_pc;
    }

    public String getPicture4_via() {
        return picture4_via;
    }

    public void setPicture4_via(String picture4_via) {
        this.picture4_via = picture4_via;
    }

    public String getInstanceID() {
        return instanceID;
    }

    public void setInstanceID(String instanceID) {
        this.instanceID = instanceID;
    }
}
