package com.iti.aurora.home.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.CalendarView;

import com.iti.aurora.R;

public class MainActivity extends AppCompatActivity {

    CalendarView calendarView;
    RecyclerView medsRecyclerView;

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


    }
}