package com.iti.aurora.editmeds.presenter;

import android.content.Context;

import com.iti.aurora.addmedicine.view.AddMedicineViewInterface;
import com.iti.aurora.editmeds.view.EditMedicineViewInterface;
import com.iti.aurora.model.RepositoryInterface;
import com.iti.aurora.model.medicine.Dose;
import com.iti.aurora.model.medicine.Medicine;
import com.iti.aurora.model.medicine.RecurrencyModel;
import com.iti.aurora.model.medicine.Treatment;
import com.iti.aurora.utils.selectdays.DaysOfWeek;
import com.iti.aurora.utils.selectdays.SelectDaysAlertDialog;
import com.iti.aurora.utils.workmanager.DoseAlarmManager;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EditMedicinePresenter implements EditMedicinePresenterInterface {
    EditMedicineViewInterface _view;
    RepositoryInterface _repo;
    SelectDaysAlertDialog selectDaysAlertDialog;
    Medicine medicineReference;

    Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public EditMedicinePresenter(EditMedicineViewInterface _view, RepositoryInterface _repo) {
        this._view = _view;
        this._repo = _repo;
    }


    @Override
    public void editMedicineToDB(Medicine medicine) {
        _repo.updateMedicine(medicine);
    }

    @Override
    public void getSelectedDaysAlertdialog(SelectDaysAlertDialog selectDaysAlertDialog) {
        this.selectDaysAlertDialog = selectDaysAlertDialog;
    }

    @Override
    public void getMedicineDetails(long medId) {
        _repo.getSpecificMedicine(medId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<Medicine>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Medicine medicine) {
                        getListOfTreatmentsFromRepo(medicine);
                        medicineReference = medicine;

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getListOfTreatmentsFromRepo(Medicine medicine) {
        _repo.getTreatmentsMedId(medicine.getMedId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Treatment>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<Treatment> treatments) {
                        _view.showMedicine(medicine, treatments);
                        ;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }


    private List<Dose> generatePeriodicDoses(long medID, long treatmentId, DateTime startDate, DateTime endDate, int noOfHours) {
        List<Dose> doseList = new ArrayList<>();

        DateTime currentWhole = new DateTime(System.currentTimeMillis());
        DateTime nextDayAt12Am = new DateTime(currentWhole.getYear(), currentWhole.getMonthOfYear(), currentWhole.getDayOfMonth(), 0, 0).plusDays(1);
        if (startDate.isBefore(nextDayAt12Am)) {
            DoseAlarmManager alarmManager = new DoseAlarmManager(this.context, new Dose(medID, treatmentId, startDate.toDate()), medicineReference);
        }

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
