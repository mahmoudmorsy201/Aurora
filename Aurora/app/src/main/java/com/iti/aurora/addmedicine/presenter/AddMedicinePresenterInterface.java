package com.iti.aurora.addmedicine.presenter;

import android.content.Context;

import com.iti.aurora.model.medicine.Medicine;
import com.iti.aurora.model.medicine.RecurrencyModel;
import com.iti.aurora.utils.selectdays.DaysOfWeek;
import com.iti.aurora.utils.selectdays.SelectDaysAlertDialog;

import org.joda.time.DateTime;

import java.util.List;

public interface AddMedicinePresenterInterface {
    void addMedicineToDB(Medicine medicine, DateTime startDate, DateTime endDate, RecurrencyModel recurrencyModel, List<DaysOfWeek> daysSelected);

    void getSelectedDaysAlertdialog(SelectDaysAlertDialog selectDaysAlertDialog);


    //for using alarm manager only
    void setContext(Context context);
}