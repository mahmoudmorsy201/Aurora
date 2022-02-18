package com.iti.aurora.usersystem.signup.presenter;

import android.app.Activity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.iti.aurora.R;
import com.iti.aurora.usersystem.signup.view.SignUpViewInterface;
import com.iti.aurora.model.User;

public class SignUpScreenPresenter implements SignUpScreenPresenterInterface{

    SignUpViewInterface _view;
    Activity activity;

    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;

    public SignUpScreenPresenter(SignUpViewInterface _view,Activity activity) {
        this._view = _view;
        this.activity = activity;
    }

    @Override
    public void createNewUser(User user) {
        _view.sendUserData(user);
    }

    @Override
    public void signInWithGoogle() {
         gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
        _view.fireSignInWithGoogleIntent();
    }

    @Override
    public GoogleSignInClient getGoogleSignInClient() {
        return mGoogleSignInClient;
    }


}