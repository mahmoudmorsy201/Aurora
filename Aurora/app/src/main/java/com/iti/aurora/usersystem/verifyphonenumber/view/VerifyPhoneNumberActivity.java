package com.iti.aurora.usersystem.verifyphonenumber.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.iti.aurora.R;
import com.iti.aurora.usersystem.signup.view.SignUpActivity;
import com.iti.aurora.usersystem.verifyphonenumber.presenter.VerifyPhoneNumberPresenter;
import com.iti.aurora.usersystem.verifyphonenumber.presenter.VerifyPhoneNumberPresenterInterface;
import com.iti.aurora.database.DAOUser;
import com.iti.aurora.model.User;

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
        DAOUser daoUser = new DAOUser(this);
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getCode() != null && checkForEditTexts()) {
                    verifyPhoneNumberPresenterInterface.verifyUser(getCode());
                    daoUser.checkForDuplicates(getUser());

                }
            }
        });

    }


    private boolean checkForEditTexts() {
        boolean result = false;
        String code = otpCode.getText().toString().trim();

        if(code.isEmpty()) {
            otpCode.setError("Code is required");
            otpCode.requestFocus();
        }else {
            result = true;
        }
        return result;
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