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

    @Query("SELECT * FROM Cervix_Screening ORDER BY `key` DESC")
    List<Form> getAllForms();

//    @Query("SELECT * FROM Cervix_Screening WHERE `key` = (SELECT MAX(`key`) FROM Cervix_Screening)")
//    Form filterdata(Form form);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertForm(Form form);

    @Update
    void updateForm(Form form);

    @Query("UPDATE Cervix_Screening SET consult = :text WHERE `key` = :id")
    void UpdateConsult (Boolean text, long id);

    @Query("SELECT * FROM Cervix_Screening WHERE `key` = :id")
    Form getOne (long id);

    @Delete
    void deleteForm(Form form);
}
