package com.iti.aurora.addmedicine.presenter;


import com.iti.aurora.addmedicine.view.AddMedicineViewInterface;
import com.iti.aurora.model.RepositoryInterface;
import com.iti.aurora.model.medicine.Dose;
import com.iti.aurora.model.medicine.Medicine;
import com.iti.aurora.model.medicine.RecurrencyModel;
import com.iti.aurora.model.medicine.Treatment;
import com.iti.aurora.utils.selectdays.DaysOfWeek;
import com.iti.aurora.utils.selectdays.SelectDaysAlertDialog;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddMedicinePresenter implements AddMedicinePresenterInterface {
    private AddMedicineViewInterface _view;
    private RepositoryInterface _repo;
    SelectDaysAlertDialog selectDaysAlertDialog;

    public AddMedicinePresenter(AddMedicineViewInterface _view, RepositoryInterface _repo) {
        this._view = _view;
        this._repo = _repo;
    }


    @Override
    public void addMedicineToDB(Medicine medicine, DateTime startDate, DateTime endDate, RecurrencyModel recurrencyModel) {
        _repo.insertMedicine(medicine)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onSuccess(@NonNull Long aLong) {
                        medicine.setMedId(aLong);
                        Treatment treatment = new Treatment(medicine.getMedId(), startDate.toDate(), endDate.toDate());
                        insertTreatment(treatment, aLong, startDate, endDate, recurrencyModel);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }
                });
    }

    @Override
    public void getSelectedDaysAlertdialog(SelectDaysAlertDialog selectDaysAlertDialog) {
        _view.setSelectedDaysAlertdialog();
        this.selectDaysAlertDialog = selectDaysAlertDialog;
    }

    private void insertTreatment(Treatment treatment, long medicineId, DateTime startDate, DateTime endDate, RecurrencyModel recurrencyModel) {
        _repo.insetTreatment(treatment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Long aLong) {
                        long treatmentId = aLong;
                        List<Dose> doses;
                        if (recurrencyModel == RecurrencyModel.Every_day) {
                            doses = generatePeriodicDoses(medicineId, treatmentId, startDate, endDate, 24);
                        } else if (recurrencyModel == RecurrencyModel.Every_2_days) {
                            doses = generatePeriodicDoses(medicineId, treatmentId, startDate, endDate, 24 * 2);
                        } else if (recurrencyModel == RecurrencyModel.Every_3_days) {
                            doses = generatePeriodicDoses(medicineId, treatmentId, startDate, endDate, 24 * 3);
                        } else if (recurrencyModel == RecurrencyModel.Every_28_days) {
                            doses = generatePeriodicDoses(medicineId, treatmentId, startDate, endDate, 24 * 28);
                        } else if (recurrencyModel == RecurrencyModel.Two_days_a_week || recurrencyModel == RecurrencyModel.Three_days_a_week || recurrencyModel == RecurrencyModel.Five_days_a_week || recurrencyModel == RecurrencyModel.Once_a_week) {
                            doses = generateNonPeriodicDoses(medicineId, treatmentId, startDate, endDate, selectDaysAlertDialog.getSelectedDaysFromDialog());
                        } else {
                            doses = new ArrayList<>();
                        }
                        _repo.insertDoses(doses);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }


    private List<Dose> generatePeriodicDoses(long medID, long treatmentId, DateTime startDate, DateTime endDate, int noOfHours) {
        List<Dose> doseList = new ArrayList<>();
        while (startDate.isBefore(endDate)) {
            Dose dose = new Dose(medID, treatmentId, startDate.toDate());
            doseList.add(dose);
            startDate = startDate.plusHours(noOfHours);
        }
        return doseList;
    }

    @androidx.annotation.NonNull
    private List<Dose> generateNonPeriodicDoses(long medID, long treatmentId, DateTime startDate, DateTime endDate, @androidx.annotation.NonNull List<DaysOfWeek> days) {
        List<Dose> doseList = new ArrayList<>();
        for (DaysOfWeek day : days) {
            DateTime nextDayDateTime = findDateOfNextDayOfWeek(startDate, day);
            doseList.addAll(generatePeriodicDoses(medID, treatmentId, nextDayDateTime, endDate, 24 * 7));
        }
        return doseList;
    }

    private DateTime findDateOfNextDayOfWeek(@androidx.annotation.NonNull DateTime startDate, @androidx.annotation.NonNull DaysOfWeek day) {
        while (!startDate.dayOfWeek().getAsText().equalsIgnoreCase(day.toString())) {
            startDate = startDate.plusHours(24);
        }
        return startDate;
    }


}
