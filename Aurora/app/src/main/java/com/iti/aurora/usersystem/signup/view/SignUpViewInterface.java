package com.iti.aurora.usersystem.signup.view;

import com.google.firebase.auth.FirebaseUser;
import com.iti.aurora.model.User;

public interface SignUpViewInterface {
    void sendUserData(User user);
    void signInWithGoogle();
    void gotToHomePage(FirebaseUser firebaseUser);
    void fireSignInWithGoogleIntent();
}