package com.iti.aurora.usersystem.signup.presenter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.iti.aurora.usersystem.signup.view.SignUpViewInterface;
import com.iti.model.User;

import java.util.concurrent.TimeUnit;

public class SignUpScreenPresenter implements SignUpScreenPresenterInterface{

    private SignUpViewInterface _view;
    private static final String TAG ="TAG";

    public SignUpScreenPresenter(SignUpViewInterface _view) {
        this._view = _view;
    }

    @Override
    public void createNewUser(User user) {
        _view.sendUserData(user);
    }



}
