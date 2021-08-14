/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ClientDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertClient(Client client);

    @Query("SELECT EXISTS(SELECT * FROM Nurses WHERE `id` = :id)")
    boolean clientExist(int id);

    @Query("SELECT * FROM Nurses")
    List<Client> getAllClient();

    @Query("DELETE FROM Nurses")
    void deleteTable();
}
