package com.iti.aurora.usersystem.signup.presenter;

import com.iti.aurora.usersystem.signup.view.SignUpViewInterface;
import com.iti.aurora.model.User;

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