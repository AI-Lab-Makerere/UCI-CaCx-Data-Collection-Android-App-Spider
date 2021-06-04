package com.ug.cancerapp.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = Form.class,
        version = 1,
        exportSchema = false
)
//@TypeConverter(DataConverter.class)
public abstract class FormDatabase extends RoomDatabase {

    private static FormDatabase formDatabase = null;

    public abstract FormDAO formDAO();

    private static volatile FormDatabase INSTANCE;
    public static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized FormDatabase getInstance(Context context){
        if (formDatabase == null){
            formDatabase = Room.databaseBuilder(
                    context.getApplicationContext(),
                    FormDatabase.class,
                    "form19b2"
            )
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return formDatabase;
    }

//    static FormDatabase getDatabase(final Context context){
//        if (INSTANCE == null){
//            synchronized (FormDatabase.class){
//                if (INSTANCE == null){
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                            FormDatabase.class, "form19b2")
//                            .build();
//                }
//            }
//        }
//        return INSTANCE;
//    }
}
