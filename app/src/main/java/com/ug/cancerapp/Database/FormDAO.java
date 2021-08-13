/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FormDAO {

    @Query("SELECT * FROM Cervix_Screening WHERE uploaded = 0 ORDER BY `key` DESC")
    List<Form> getAllForms();

    @Query("SELECT * FROM Cervix_Screening WHERE uploaded = 1 ORDER BY `key` DESC")
    List<Form> getAllFormsUploaded();

//    @Query("SELECT * FROM Cervix_Screening WHERE `key` = (SELECT MAX(`key`) FROM Cervix_Screening)")
//    Form filterdata(Form form);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertForm(Form form);

    @Update
    void updateForm(Form form);

    @Query("DELETE FROM Cervix_Screening WHERE `key` = :id")
    void DeleteForm(long id);

    @Query("UPDATE Cervix_Screening SET consult = :text WHERE `key` = :id")
    void UpdateConsult (Boolean text, long id);

    @Query("UPDATE Cervix_Screening SET uploaded = :text WHERE `key` = :id")
    void UpdateUpload (Boolean text, long id);

    @Query("SELECT * FROM Cervix_Screening WHERE `key` = :id")
    Form getOne (long id);

    @Delete
    void deleteForm(Form form);
}
