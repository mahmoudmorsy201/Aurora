package com.iti.database;


import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iti.model.User;

public class DAOUser {
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private Activity activity;


    public DAOUser(Activity activity) {
         database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("users");
        this.activity = activity;
    }

    private Task<Void> addUser(User user) {
        return databaseReference.push().setValue(user);
    }


    public void checkForDuplicates(User user) {

        databaseReference.orderByChild("phoneNumber").equalTo(user.getPhoneNumber()).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            makeToast("User is already exists");
                        }else {
                            addUser(user);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
    }

    private void makeToast(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
        //TODO: Add alert view to notify the user that he is already exists.
    }



}
