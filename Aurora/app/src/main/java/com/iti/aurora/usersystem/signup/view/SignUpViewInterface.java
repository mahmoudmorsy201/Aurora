package com.iti.aurora.usersystem.signup.view;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.iti.aurora.model.User;

public interface SignUpViewInterface {
    void sendUserData(User user);
    void signInWithGoogle();
    void gotToHomePage(FirebaseUser firebaseUser);
    void fireSignInWithGoogleIntent();
}