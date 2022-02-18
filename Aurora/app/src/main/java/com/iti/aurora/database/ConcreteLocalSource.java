package com.iti.aurora.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.iti.aurora.model.medicine.Medicine;

import java.util.List;

public class ConcreteLocalSource implements LocalSource {

    private  MedicineDAO medicineDAO;
    private static ConcreteLocalSource concreteLocalSource = null;
    private LiveData<List<Medicine>> storedMedicines;


    private ConcreteLocalSource(Context context) {
        AppDataBase db = AppDataBase.getInstance(context.getApplicationContext());
        medicineDAO = db.medicineDAO();
        storedMedicines = medicineDAO.getAllMedicines();

    }

    public static ConcreteLocalSource getInstance(Context context) {
        if(concreteLocalSource == null) {
           concreteLocalSource = new ConcreteLocalSource(context);
        }
        return concreteLocalSource;
    }


    @Override
    public void insertMedicine(Medicine medicine) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                medicineDAO.insertMedicine(medicine);
            }
        }).start();
    }


    @Override
    public void deleteMedicine(Medicine medicine) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                medicineDAO.deleteMedicine(medicine);
            }
        }).start();
    }

    @Override
    public LiveData<List<Medicine>> getAllStoredMedicines() {
        return storedMedicines;
    }
}
