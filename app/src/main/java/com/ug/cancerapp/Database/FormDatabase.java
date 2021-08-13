/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {
                Form.class,
                Client.class,
        },
        version = 1,
        exportSchema = false
)
//@TypeConverter(DataConverter.class)
public abstract class FormDatabase extends RoomDatabase {

    private static FormDatabase formDatabase = null;

    public abstract FormDAO formDAO();
    public abstract ClientDAO clientDAO();

    public static synchronized FormDatabase getInstance(Context context){
        if (formDatabase == null){
            formDatabase = Room.databaseBuilder(
                    context.getApplicationContext(),
                    FormDatabase.class,
                    "form19b2"
            )
                    .allowMainThreadQueries()
                    .build();
        }
        return formDatabase;
    }
}
