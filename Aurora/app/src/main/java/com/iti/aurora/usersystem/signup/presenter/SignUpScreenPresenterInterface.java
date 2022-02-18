package com.iti.aurora.usersystem.signup.presenter;


import android.app.Activity;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.iti.aurora.model.User;

public interface SignUpScreenPresenterInterface {

    void createNewUser(User user);
    void signInWithGoogle();
    GoogleSignInClient getGoogleSignInClient();
}