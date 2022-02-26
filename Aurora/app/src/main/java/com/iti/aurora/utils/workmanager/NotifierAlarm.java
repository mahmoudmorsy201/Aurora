package com.iti.aurora.utils.workmanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

import com.iti.aurora.MainActivity;
import com.iti.aurora.R;
import com.iti.aurora.NotificationDialogOverApp;
import com.iti.aurora.model.medicine.Dose;
import com.iti.aurora.model.medicine.Medicine;
import com.iti.aurora.utils.Constants;

import java.util.Random;

public class NotifierAlarm extends BroadcastReceiver {
    String time;


    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getBundleExtra("MED_DATA");
        Medicine medicine = (Medicine) bundle.getSerializable(Constants.NotificationUtil.MEDICNIE_SPECS);
        Dose dose= (Dose) bundle.getSerializable(Constants.NotificationUtil.DOSE_SPECS);
        time = intent.getStringExtra(Constants.NotificationUtil.MEDICINE_TIME);
        Uri alarmsound = Uri.parse("android.resource://" + context.getApplicationContext().getPackageName() + "/" + R.raw.messagetone);
        int NOTIFICATION_ID = new Random(System.currentTimeMillis()).nextInt(120);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build();
        //todo here luncher activity name form notification click
        Intent intent1 = new Intent(context, MainActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addParentStack(MainActivity.class);
        taskStackBuilder.addNextIntent(intent1);

        PendingIntent intent2 = taskStackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel("my_channel_01", "Medicine", NotificationManager.IMPORTANCE_HIGH);
            channel.setSound(alarmsound, audioAttributes);
        }
//todo add icon of medication
        Notification notification = builder

                .setContentTitle("Time to Take : " + medicine.getName() + " " + medicine.getMedicineForm())
                .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getString(R.string.youshould_take) + medicine.getName()
                        + " at " + time + "\n" + "don't forget " + medicine.getInstruction()))
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(intent2)
                .setChannelId("my_channel_01")
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(new Random(System.currentTimeMillis()).nextInt(120), notification);
        showDialog(context,medicine,time,dose);
    }

    private void showDialog(Context context, Medicine medicine, String time, Dose dose) {
        NotificationDialogOverApp notificationDialogOverApp = new NotificationDialogOverApp(context,medicine,time,dose);
        notificationDialogOverApp.open();

    }
}