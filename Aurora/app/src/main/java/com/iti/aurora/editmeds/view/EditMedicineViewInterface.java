package com.iti.aurora.editmeds.view;

import com.iti.aurora.model.medicine.Medicine;
import com.iti.aurora.model.medicine.RecurrencyModel;

import org.joda.time.DateTime;

public interface EditMedicineViewInterface {
    void editMedicine(Medicine medicine);
    void showMedicine(Medicine medicine);
}
