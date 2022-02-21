package com.iti.aurora.home.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
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
    public static final String TAG = "TAG";

    MainActivityPresenterInterface mainActivityPresenterInterface;
    MedsAdapter adapter;

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

    @Override
    public void showLocalDataByDay(LiveData<List<Dose>> doseList) {
        doseList.observe(this, doses -> {
            adapter.setDoseList(doses);
            adapter.notifyDataSetChanged();
        });
    }
}