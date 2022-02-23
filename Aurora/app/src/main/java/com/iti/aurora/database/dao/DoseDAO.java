package com.iti.aurora.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.iti.aurora.model.medicine.Dose;
import com.iti.aurora.model.medicine.Medicine;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface DoseDAO {
    @Query("SELECT * FROM dose")
    LiveData<List<Dose>> getAllDoses();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDose(Dose dose);

    @Delete
    void deleteDose(Dose dose);

    @Update
    void updateDose(Dose dose);

    @Query("SELECT * FROM dose WHERE doseId = :doseId")
    Dose getSpecificDose(int doseId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDoses(List<Dose> doses);

    @Query("SELECT * FROM dose WHERE timeToTake > :start AND timeToTake <= :end")
    LiveData<List<Dose>> getDosesByDay(long start, long end);

    @Query("SELECT * FROM dose WHERE timeToTake > :start AND timeToTake <= :end")
    List<Dose> getDosesByDayOverLoad(long start,long end);

}
