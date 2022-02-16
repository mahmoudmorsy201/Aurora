package com.iti.aurora.database;


import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iti.aurora.model.User;

public class DAOUser {
    DatabaseReference databaseReference;

    public DAOUser() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("users");
    }

    public Task<Void> addUser(User user) {
        return databaseReference.push().setValue(user);
    }


}