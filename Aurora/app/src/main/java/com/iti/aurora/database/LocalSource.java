package com.iti.aurora.database;

import androidx.lifecycle.LiveData;


import com.iti.aurora.model.medicine.Dose;
import com.iti.aurora.model.medicine.Medicine;
import com.iti.aurora.model.medicine.Treatment;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;

public interface LocalSource {

    @NonNull Single<Long> insertMedicine(Medicine medicine);
    void deleteMedicine(Medicine medicine);
    void updateMedicine(Medicine medicine);
    Medicine getSpecificMedicine(int medId);
    LiveData<List<Medicine>> getAllStoredMedicines();

    @NonNull Single<Long> insetTreatment(Treatment treatment);
    void deleteTreatment(Treatment treatment);
    void updateTreatment(Treatment treatment);
    Treatment getSpecificTreatment(long treatmentId);
    LiveData<List<Treatment>> getAllStoredTreatments();

    void insertDose(Dose dose);
    void deleteDose(Dose dose);
    void updateDose(Dose dose);
    Dose getSpecificDose(int doseId);
    LiveData<List<Dose>> getAllStoredDoses();
    void insertDoses(List<Dose> doses);
}
