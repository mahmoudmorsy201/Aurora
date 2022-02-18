package com.iti.aurora.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.iti.aurora.model.medicine.Medicine;


@Database(entities = {Medicine.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase instance = null;
    public abstract MedicineDAO medicineDAO();

    public static synchronized AppDataBase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class,"medicines").build();
        }
        return instance;
    }


}



