package com.iti.aurora;
import static android.content.Context.WINDOW_SERVICE;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.iti.aurora.utils.workmanager.DoseAlarmManager;

public class NotificationDialogOverApp {

    private Context context;
    private View mView;
    private WindowManager.LayoutParams mParams;
    private WindowManager mWindowManager;
    private LayoutInflater layoutInflater;

    public NotificationDialogOverApp(Context context){
        this.context=context;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);

        }
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = layoutInflater.inflate(R.layout.dialog_dosage_reminder, null);
        mView.findViewById(R.id.skip_button).setOnClickListener(view -> close());
        mView.findViewById(R.id.snooze_button).setOnClickListener(view -> {
            DoseAlarmManager doseAlarmManager=new DoseAlarmManager(context);
            Toast.makeText(context, "will back in( 5 )minutes", Toast.LENGTH_SHORT).show();
            close();
        });
        mView.findViewById(R.id.take_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo add is taken in DB here
            }
        });
        mParams.gravity = Gravity.CENTER;
        mWindowManager = (WindowManager)context.getSystemService(WINDOW_SERVICE);

    }

    public void open() {

        try {

            if(mView.getWindowToken()==null) {
                if(mView.getParent()==null) {
                    mWindowManager.addView(mView, mParams);
                }
            }
        } catch (Exception e) {
            Log.d("Error1",e.toString());
        }

    }

    public void close() {
        try {
            ((WindowManager)context.getSystemService(WINDOW_SERVICE)).removeView(mView);
            mView.invalidate();
            ((ViewGroup)mView.getParent()).removeAllViews();
        } catch (Exception e) {
            Log.d("Error2",e.toString());
        }
    }
}
