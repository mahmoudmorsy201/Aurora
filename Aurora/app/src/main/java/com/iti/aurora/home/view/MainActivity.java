package com.iti.aurora.home.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.CalendarView;

import com.google.firebase.auth.FirebaseUser;
import com.iti.aurora.R;
import com.iti.aurora.database.ConcreteLocalSource;
import com.iti.aurora.home.presenter.MainActivityPresenter;
import com.iti.aurora.home.presenter.MainActivityPresenterInterface;
import com.iti.aurora.model.Repository;
import com.iti.aurora.model.medicine.Dose;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityViewInterface {

    CalendarView calendarView;
    RecyclerView medsRecyclerView;
    public static final String TAG = "MainActivity";

    MainActivityPresenterInterface mainActivityPresenterInterface;
    MedsAdapter adapter;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityPresenterInterface = new MainActivityPresenter(MainActivity.this,
                Repository.getInstance(ConcreteLocalSource.getInstance(this), this)
        );

        DateTime startDate = new DateTime(System.currentTimeMillis());
        DateTime start2 = new DateTime(startDate.getYear(), startDate.getMonthOfYear(), startDate.getDayOfMonth(), 0, 0);
        DateTime endDate = new DateTime(start2.plusDays(1));
        mainActivityPresenterInterface.getDosesByDay(start2.getMillis(), endDate.getMillis());

        calendarView = findViewById(R.id.mainCalendarView);
        medsRecyclerView = findViewById(R.id.medsRecyclerView);

        calendarView.setOnDateChangeListener((calendarView, year, month, day) -> {
            mainActivityPresenterInterface.getDosesByDay(new DateTime(year, month + 1, day, 0, 0).getMillis(), new DateTime(year, month + 1, day, 0, 0).plusDays(1).getMillis());
        });

        List<Dose> list = new ArrayList<>();
        medsRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
        adapter = new MedsAdapter(list, MainActivity.this, Repository.getInstance(ConcreteLocalSource.getInstance(this), this));
        medsRecyclerView.setAdapter(adapter);

        FirebaseUser user = getIntent().getParcelableExtra("GOOGLE_ACCOUNT");
        mainActivityPresenterInterface.getLocalDoses();

    }

    @Override
    public void showLocalData(LiveData<List<Dose>> doses) {
        doses.observe(this, doseList -> {

        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showLocalDataByDay(LiveData<List<Dose>> doseList) {
        doseList.observe(this, doses -> {
            adapter.setDoseList(doses);
            adapter.notifyDataSetChanged();
        });
    }
}