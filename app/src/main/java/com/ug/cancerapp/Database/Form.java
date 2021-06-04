package com.ug.cancerapp.Database;

import androidx.room.Entity;

@Entity(tableName = "Cervix_Screening")
public class Form {

    String studyID;
    String initials;
    int age;
    String district;
    String county;
    String village;
}
