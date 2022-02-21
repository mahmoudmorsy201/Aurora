package com.iti.aurora.home.view;

import androidx.lifecycle.LiveData;

import com.iti.aurora.model.medicine.Dose;

import java.util.List;

public interface MainActivityViewInterface {
    void showLocalData(LiveData<List<Dose>> doses);
}
