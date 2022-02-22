package com.iti.aurora.addmedicine.presenter;

import com.iti.aurora.model.medicine.Medicine;
import com.iti.aurora.model.medicine.RecurrencyModel;
import com.iti.aurora.utils.selectdays.SelectDaysAlertDialog;

import org.joda.time.DateTime;

public interface AddMedicinePresenterInterface {
    void addMedicineToDB(Medicine medicine, DateTime startDate, DateTime endDate, RecurrencyModel recurrencyModel);
    void getSelectedDaysAlertdialog(SelectDaysAlertDialog selectDaysAlertDialog);
}
