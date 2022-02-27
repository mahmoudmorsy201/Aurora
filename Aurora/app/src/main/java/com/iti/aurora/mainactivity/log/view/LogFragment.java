package com.iti.aurora.mainactivity.log.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.iti.aurora.R;
import com.iti.aurora.usersystem.signup.view.SignUpActivity;
import com.iti.aurora.utils.Constants;
import com.iti.aurora.utils.spfactory.SharedPreferencesFactory;

public class LogFragment extends Fragment {
    Button logOutBtn;
    private FirebaseAuth firebaseAuth;
    private SharedPreferencesFactory sharedPreferencesFactory;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log,container,false);
        firebaseAuth = FirebaseAuth.getInstance();
        sharedPreferencesFactory = SharedPreferencesFactory.getInstance(getContext());
        logOutBtn = view.findViewById(R.id.logOutBtn);
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferencesFactory.putBoolean(false);
                Intent intent = new Intent(getContext(), SignUpActivity.class);
                getContext().startActivity(intent);
                firebaseAuth.signOut();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}