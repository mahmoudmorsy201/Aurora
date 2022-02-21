package com.iti.aurora.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.iti.aurora.database.dao.DoseDAO;
import com.iti.aurora.database.dao.MedicineDAO;
import com.iti.aurora.database.dao.TreatmentDAO;
import com.iti.aurora.model.medicine.Dose;
import com.iti.aurora.model.medicine.Medicine;
import com.iti.aurora.model.medicine.Treatment;

import org.joda.time.DateTime;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;

public class ConcreteLocalSource implements LocalSource {

    private MedicineDAO medicineDAO;
    private DoseDAO doseDAO;
    private TreatmentDAO treatmentDAO;
    private static ConcreteLocalSource concreteLocalSource = null;
    private LiveData<List<Medicine>> storedMedicines;
    private LiveData<List<Dose>> storedDoses;
    private LiveData<List<Treatment>> storedTreatments;
    private LiveData<List<Dose>> getStoredDosesByDay;




    private ConcreteLocalSource(Context context) {
        AppDataBase db = AppDataBase.getInstance(context.getApplicationContext());
        medicineDAO = db.medicineDAO();
        storedMedicines = medicineDAO.getAllMedicines();

        doseDAO = db.doseDAO();
        storedDoses = doseDAO.getAllDoses();
        getStoredDosesByDay = doseDAO.getDosesByDay(new DateTime(System.currentTimeMillis()).getMillis() ,new DateTime(System.currentTimeMillis()).plusHours(24).getMillis());

        treatmentDAO = db.treatmentDAO();
        storedTreatments = treatmentDAO.getAllTreatments();
    }

    public static ConcreteLocalSource getInstance(Context context) {
        if(concreteLocalSource == null) {
           concreteLocalSource = new ConcreteLocalSource(context);
        }
        return concreteLocalSource;
    }


    @Override
    public @NonNull Single<Long> insertMedicine(Medicine medicine) {
        return Single.fromCallable(() -> medicineDAO.insertMedicine(medicine));
    }


    @Override
    public void deleteMedicine(Medicine medicine) {
        new Thread(() -> medicineDAO.deleteMedicine(medicine)).start();
    }

    @Override
    public void updateMedicine(Medicine medicine) {
        new Thread(() -> medicineDAO.updateMedicine(medicine)).start();
    }

    @Override
    public Medicine getSpecificMedicine(int medId) {
        return medicineDAO.getSpecificMedicine(medId);
    }

    @Override
    public LiveData<List<Medicine>> getAllStoredMedicines() {
        return storedMedicines;
    }

    @Override
    public @NonNull Single<Long> insetTreatment(Treatment treatment) {
        return Single.fromCallable(() -> treatmentDAO.insertTreatment(treatment));
    }

    @Override
    public void deleteTreatment(Treatment treatment) {
        new Thread(() -> treatmentDAO.deleteTreatment(treatment)).start();
    }

    @Override
    public void updateTreatment(Treatment treatment) {
        new Thread(() -> treatmentDAO.updateTreatment(treatment)).start();
    }

    @Override
    public Treatment getSpecificTreatment(long treatmentId) {
        return treatmentDAO.getSpecificTreatment(treatmentId);
    }

    @Override
    public LiveData<List<Treatment>> getAllStoredTreatments() {
        return storedTreatments;
    }

    @Override
    public void insertDose(Dose dose) {
        new Thread(() -> doseDAO.insertDose(dose)).start();
    }

    @Override
    public void deleteDose(Dose dose) {
        new Thread(() -> doseDAO.deleteDose(dose)).start();
    }

    @Override
    public void updateDose(Dose dose) {
        new Thread(() -> doseDAO.updateDose(dose)).start();

    }

    @Override
    public Dose getSpecificDose(int doseId) {
        return doseDAO.getSpecificDose(doseId);
    }

    @Override
    public LiveData<List<Dose>> getAllStoredDoses() {
        return storedDoses;
    }

    @Override
    public void insertDoses(List<Dose> doses) {
        new Thread(() -> doseDAO.insertDoses(doses)).start();
    }

    @Override
    public LiveData<List<Dose>> getDosesByDay(long start, long end) {
        return getStoredDosesByDay;
    }
}
