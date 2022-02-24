package com.iti.aurora.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.iti.aurora.R;

public class Medication extends Fragment {
    Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medication, container, false);
        context = getContext();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}