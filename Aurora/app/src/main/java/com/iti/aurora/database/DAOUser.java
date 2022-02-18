package com.iti.aurora.database;


import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iti.aurora.model.User;
import com.iti.aurora.utils.Constants;

public class DAOUser {
     DatabaseReference databaseReference;
     FirebaseDatabase database;
     Activity activity;


    public DAOUser(Activity activity) {
         database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(Constants.FirebaseConstants.USERS);
        this.activity = activity;
    }

    private void addUser(User user) {
         databaseReference.push().setValue(user);
    }




    public void checkForDuplicates(User user) {
        databaseReference.orderByChild(Constants.FirebaseConstants.PHONE_NUMBER).equalTo(user.getPhoneNumber()).addValueEventListener(
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
