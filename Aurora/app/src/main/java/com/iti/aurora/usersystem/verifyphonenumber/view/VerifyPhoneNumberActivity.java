package com.iti.aurora.usersystem.verifyphonenumber.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.iti.aurora.R;
import com.iti.aurora.usersystem.signup.view.SignUpActivity;
import com.iti.aurora.usersystem.verifyphonenumber.presenter.VerifyPhoneNumberPresenter;
import com.iti.aurora.usersystem.verifyphonenumber.presenter.VerifyPhoneNumberPresenterInterface;
import com.iti.database.DAOUser;
import com.iti.model.User;

public class VerifyPhoneNumberActivity extends AppCompatActivity implements VerifyPhoneNumberViewInterface{

    EditText otpCode;
    Button verifyButton;
    private VerifyPhoneNumberPresenterInterface verifyPhoneNumberPresenterInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);
        initUI();

    }

    private void initUI(){
        otpCode = findViewById(R.id.codeEditText);
        verifyButton = findViewById(R.id.verifyButton);
        verifyPhoneNumberPresenterInterface = new VerifyPhoneNumberPresenter(VerifyPhoneNumberActivity.this,VerifyPhoneNumberActivity.this);
        verifyPhoneNumberPresenterInterface.sendCodeToUser(getUser());
        DAOUser daoUser = new DAOUser();
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForEditTexts();
                if(getCode() != null) {
                    verifyPhoneNumberPresenterInterface.verifyUser(getCode());

                    daoUser.addUser(getUser());

                }
            }
        });

    }


    private void checkForEditTexts() {
        String code = otpCode.getText().toString().trim();

        if(code.isEmpty()) {
            otpCode.setError("Code is required");
            otpCode.requestFocus();
        }
    }




    @Override
    public String getCode() {
        return otpCode.getText().toString();
    }

    @Override
    public User getUser() {
        User user =(User) getIntent().getExtras().getSerializable(SignUpActivity.USER_KEY);
        return user;
    }


}