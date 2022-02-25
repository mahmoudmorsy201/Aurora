package com.iti.aurora.mainactivity.home.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.iti.aurora.R;
import com.iti.aurora.addmedicine.view.AddMedicineActivity;
import com.iti.aurora.database.ConcreteLocalSource;
import com.iti.aurora.mainactivity.home.presenter.HomeFragmentPresenter;
import com.iti.aurora.mainactivity.home.presenter.HomeFragmentPresenterInterface;
import com.iti.aurora.model.Repository;
import com.iti.aurora.model.medicine.Dose;
import com.iti.aurora.utils.workmanager.DailyWorker;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment implements HomeFragmentViewInterface {
    CalendarView calendarView;
    RecyclerView medsRecyclerView;
    public static final String TAG = "MainActivity";
    FloatingActionButton fab;

    HomeFragmentPresenterInterface fragmentHomePresenterInterface;
    DosesAdapter adapter;

    String workMangerName = "DAILY_WORK_MANAGER";
    PeriodicWorkRequest periodic;
    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        fragmentHomePresenterInterface = new HomeFragmentPresenter(this,
                Repository.getInstance(ConcreteLocalSource.getInstance(context), context)
        );

        periodic = new PeriodicWorkRequest.Builder(DailyWorker.class,
                1,
                TimeUnit.MINUTES)
                .addTag(workMangerName)
                .build();

        WorkManager workManager = WorkManager.getInstance(getActivity());
        //workManager.cancelAllWork();
        workManager.enqueueUniquePeriodicWork(workMangerName, ExistingPeriodicWorkPolicy.KEEP, periodic);
        Log.d("WORK_MANAGER", "onCreate: creating work manager");
        DateTime startDate = new DateTime(System.currentTimeMillis());
        DateTime start2 = new DateTime(startDate.getYear(), startDate.getMonthOfYear(), startDate.getDayOfMonth(), 0, 0);
        DateTime endDate = new DateTime(start2.plusDays(1));
        fragmentHomePresenterInterface.getDosesByDay(start2.getMillis(), endDate.getMillis());

        calendarView = view.findViewById(R.id.mainCalendarView);
        medsRecyclerView = view.findViewById(R.id.medsRecyclerView);

        calendarView.setOnDateChangeListener((calendarView, year, month, day) -> {
            fragmentHomePresenterInterface.getDosesByDay(new DateTime(year, month + 1, day, 0, 0).getMillis(), new DateTime(year, month + 1, day, 0, 0).plusDays(1).getMillis());
        });

        List<Dose> list = new ArrayList<>();
        medsRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        adapter = new DosesAdapter(list, context, Repository.getInstance(ConcreteLocalSource.getInstance(context), context), new DosesRecyclerItemClick() {
            @Override
            public void showDoseDialog(Dose dose) {
                //TODO show does dialog
            }
        });

        medsRecyclerView.setAdapter(adapter);
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, AddMedicineActivity.class);
            startActivity(intent);
        });

        //FirebaseUser user = getIntent().getParcelableExtra("GOOGLE_ACCOUNT");
        //mainActivityPresenterInterface.getLocalDoses();

        return view;
    }

    @Override
    public void showLocalData(LiveData<List<Dose>> doses) {
        doses.observe(this, doseList -> {
            //TODO all doses in the database are shown in here
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}