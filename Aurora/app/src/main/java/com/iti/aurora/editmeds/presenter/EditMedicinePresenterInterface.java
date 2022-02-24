package com.iti.aurora.editmeds.presenter;

import android.content.Context;

import com.iti.aurora.model.medicine.Medicine;
import com.iti.aurora.model.medicine.RecurrencyModel;
import com.iti.aurora.utils.selectdays.SelectDaysAlertDialog;

import org.joda.time.DateTime;

public interface EditMedicinePresenterInterface {
    void editMedicineToDB(Medicine medicine);

    void getSelectedDaysAlertdialog(SelectDaysAlertDialog selectDaysAlertDialog);

    void getMedicineDetails(long medId);


    //for using alarm manager only
    void setContext(Context context);
}
