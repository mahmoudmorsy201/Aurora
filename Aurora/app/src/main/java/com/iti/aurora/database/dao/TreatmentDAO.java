package com.iti.aurora.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.iti.aurora.model.medicine.Treatment;
import java.util.List;

@Dao
public interface TreatmentDAO {

    @Query("SELECT * FROM treatment")
    LiveData<List<Treatment>> getAllTreatments();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTreatment(Treatment treatment);

    @Delete
    void deleteTreatment(Treatment treatment);

    @Update
    void updateTreatment(Treatment treatment);

    @Query("SELECT * FROM treatment WHERE treatmentId = :treatmentId")
    Treatment getSpecificTreatment(long treatmentId);
}
