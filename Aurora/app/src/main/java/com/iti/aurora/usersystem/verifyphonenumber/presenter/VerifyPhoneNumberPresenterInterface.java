package com.iti.aurora.usersystem.verifyphonenumber.presenter;

import com.iti.aurora.model.User;

public interface VerifyPhoneNumberPresenterInterface {
    void verifyUser(String code);
    void sendCodeToUser(User user);
}