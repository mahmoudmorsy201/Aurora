package com.iti.aurora.home.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ListViewAutoScrollHelper;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;
import com.iti.aurora.R;
import com.iti.aurora.database.ConcreteLocalSource;
import com.iti.aurora.home.presenter.MainActivityPresenter;
import com.iti.aurora.home.presenter.MainActivityPresenterInterface;
import com.iti.aurora.model.Repository;
import com.iti.aurora.model.medicine.Dose;
import com.iti.aurora.model.medicine.Medicine;

import org.joda.time.DateTime;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityViewInterface {

    CalendarView calendarView;
    RecyclerView medsRecyclerView;
    public static final String TAG = "TAG";

    MainActivityPresenterInterface mainActivityPresenterInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityPresenterInterface = new MainActivityPresenter(MainActivity.this,
                Repository.getInstance(ConcreteLocalSource.getInstance(this), this)
        );

        calendarView = findViewById(R.id.mainCalendarView);
        medsRecyclerView = findViewById(R.id.medsRecyclerView);

        calendarView.setOnDateChangeListener((calendarView, year, month, day) -> {
            mainActivityPresenterInterface.getDosesByDay(new DateTime(year, month + 1, day, 0, 0).getMillis(), new DateTime(year, month + 1, day, 0, 0).plusDays(1).getMillis());
        });

        FirebaseUser user = getIntent().getParcelableExtra("GOOGLE_ACCOUNT");
        Log.i(TAG, "User Info ");


    }

    @Override
    public void showLocalData(LiveData<List<Dose>> doses) {
        doses.observe(this, new Observer<List<Dose>>() {
            @Override
            public void onChanged(List<Dose> doses) {

            }
        });
    }
}