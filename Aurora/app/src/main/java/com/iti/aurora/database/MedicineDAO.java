package com.iti.aurora.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.iti.aurora.model.medicine.Medicine;

import java.util.List;

@Dao
public interface MedicineDAO {
    @Query("SELECT * FROM medicine")
    LiveData<List<Medicine>> getAllMedicines();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMedicine(Medicine medicine);

    @Delete
    void deleteMedicine(Medicine medicine);
}
