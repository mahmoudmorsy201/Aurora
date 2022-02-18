package com.iti.aurora.home.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;
import com.iti.aurora.R;

public class MainActivity extends AppCompatActivity {

    CalendarView calendarView;
    RecyclerView medsRecyclerView;
    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView = findViewById(R.id.mainCalendarView);
        medsRecyclerView = findViewById(R.id.medsRecyclerView);

        calendarView.setOnDateChangeListener((calendarView, year, month, day) -> {
            //month starts from 0 not 1
            //TODO handle on click listener
            //date Clicked
        });

        FirebaseUser user = getIntent().getParcelableExtra("GOOGLE_ACCOUNT");
        Log.i(TAG, "User Info ");





    }
}