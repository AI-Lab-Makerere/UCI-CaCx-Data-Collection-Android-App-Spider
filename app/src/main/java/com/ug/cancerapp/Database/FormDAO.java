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

    @Query("SELECT * FROM Cervix_Screening")
    LiveData<List<Form>> getAllForms();

//    @Query("SELECT * FROM Cervix_Screening WHERE `key` = (SELECT MAX(`key`) FROM Cervix_Screening)")
//    Form filterdata(Form form);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertForm(Form form);

    @Update
    void updateForm(Form form);

    @Delete
    void deleteForm(Form form);
}
