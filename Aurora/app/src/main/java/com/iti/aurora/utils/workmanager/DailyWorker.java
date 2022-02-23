package com.iti.aurora.utils.workmanager;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.iti.aurora.model.RepositoryInterface;
import com.iti.aurora.model.medicine.Dose;

import org.joda.time.DateTime;

import java.util.List;

public class DailyWorker extends Worker {
    List<Dose> doseModels;
    RepositoryInterface repositoryInterface;


    public DailyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams, RepositoryInterface repositoryInterface) {
        super(context, workerParams);
        this.repositoryInterface = repositoryInterface;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public Result doWork() {
        Log.i("Daily", "doWork: DailyWorkManger");

        DateTime startDate = new DateTime(System.currentTimeMillis());

        DateTime start2 = new DateTime(startDate.getYear(), startDate.getMonthOfYear(), startDate.getDayOfMonth(), 0, 0);
        DateTime endDate = new DateTime(start2.plusDays(1));

        List<Dose> doses = repositoryInterface.getDosesByDay(start2.getMillis(), endDate.getMillis()).getValue();

        for (Dose doseModelLoop : doses) {
            DoseAlarmManager doseAlarmManager = new DoseAlarmManager(getApplicationContext(), doseModelLoop);
        }

        return Result.success();
    }


}