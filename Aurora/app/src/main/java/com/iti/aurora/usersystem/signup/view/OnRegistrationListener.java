package com.iti.aurora.usersystem.signup.view;

import com.google.firebase.auth.FirebaseUser;

public interface OnRegistrationListener {
    void onSuccess(FirebaseUser firebaseUser);
    void onFailure(String message);
}
