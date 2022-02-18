package com.iti.aurora.database;

import androidx.lifecycle.LiveData;


import com.iti.aurora.model.medicine.Medicine;

import java.util.List;

public interface LocalSource {
    void insertMedicine(Medicine medicine);
    void deleteMedicine(Medicine medicine);
    LiveData<List<Medicine>> getAllStoredMedicines();
}
