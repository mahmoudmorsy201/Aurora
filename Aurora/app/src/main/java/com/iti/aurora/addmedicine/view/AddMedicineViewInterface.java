package com.iti.aurora.addmedicine.view;

import com.iti.aurora.model.medicine.Medicine;
import com.iti.aurora.model.medicine.RecurrencyModel;
import com.iti.aurora.utils.selectdays.SelectDaysAlertDialog;

import org.joda.time.DateTime;

public interface AddMedicineViewInterface {
    void addMedicine(Medicine medicine, DateTime startDate, DateTime endDate, RecurrencyModel recurrencyModel);
    void setSelectedDaysAlertdialog();

}
