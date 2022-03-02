package com.iti.aurora.utils.workmanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.iti.aurora.model.medicine.Dose;
import com.iti.aurora.model.medicine.Medicine;
import com.iti.aurora.utils.Constants;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;
import java.util.List;

public class DoseAlarmManager {

    /* public DoseAlarmManager(Context context, List<Dose> doseModelList) {
         setAlarm(context, doseModelList);
     }*/
    Medicine medicine;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public DoseAlarmManager(Context context, Dose doseMode, Medicine medicine) {
        setAlarmSingle(context, doseMode, medicine);
        Log.d("WORK_MANAGER", "DoseAlarmManager: Constructor");
    }

    //todo add dose model to dialog notification
    public DoseAlarmManager(Context context) {
        setAlarmSnooze(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setAlarmSingle(Context context, Dose doseModel, Medicine medicine) {
        Intent intent = new Intent(context, NotifierAlarm.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.NotificationUtil.MEDICNIE_SPECS, (Serializable) medicine);
        intent.putExtra("MED_DATA", bundle);

        bundle.putSerializable(Constants.NotificationUtil.DOSE_SPECS, (Serializable) doseModel);
        intent.putExtra("DOSE_DATA", bundle);
        //Log.d("WORK_MANAGER", "setAlarmSingle: constructor");
        intent.putExtra(Constants.NotificationUtil.MEDICINE_TIME, new DateTime(doseModel.getTimeToTake()).toString(DateTimeFormat.forPattern("hh:mm")));
        intent.putExtra(Constants.NotificationUtil.DOSE_ID_KEY, doseModel.getDoseId());
        PendingIntent intent1 = PendingIntent.getBroadcast(context, Integer.parseInt(String.valueOf(doseModel.getDoseId())), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, new DateTime(doseModel.getTimeToTake()).getMillis(), intent1);

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setAlarm(Context context, List<Dose> doseModelList) {
        int i = 0;
        AlarmManager[] alaManager = new AlarmManager[doseModelList.size()];
        Log.d("WORK_MANAGER", "setAlarm: ");
        for (Dose doseModel : doseModelList) {
            Intent intent = new Intent(context, NotifierAlarm.class);

            //intent.putExtra("Message", doseModel.getDoseId());
            intent.putExtra("RemindDate", new DateTime(doseModel.getTimeToTake()).toString(DateTimeFormat.forPattern("hh:mm")));
            //intent.putExtra("id", doseModel.getDoseId());
            PendingIntent intent1 = PendingIntent.getBroadcast(context, Integer.parseInt(String.valueOf(doseModel.getDoseId())), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alaManager[i] = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alaManager[i].setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, new DateTime(doseModel.getTimeToTake()).getMillis(), intent1);
            i++;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setAlarmSnooze(Context context) {
        Intent intent = new Intent(context, NotifierAlarm.class);
        Log.d("WORK_MANAGER", "setAlarmSingle: constructor");
        intent.putExtra("Message", "doseModel.getTimeTaken()");
        intent.putExtra("RemindDate", "reminder daate");
        intent.putExtra("id", 12);
        PendingIntent intent1 = PendingIntent.getBroadcast(context, 12, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000 * 8, intent1);
    }

}