package com.iti.aurora.usersystem.signup.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;
import com.iti.aurora.R;
import com.iti.aurora.model.User;
import com.iti.aurora.usersystem.signup.presenter.SignUpScreenPresenter;
import com.iti.aurora.usersystem.signup.presenter.SignUpScreenPresenterInterface;
import com.iti.aurora.usersystem.verifyphonenumber.view.VerifyPhoneNumberActivity;


public class SignUpActivity extends AppCompatActivity implements SignUpViewInterface{

    EditText userNameEditText,phoneNumberEditText;
    Button signUpButton;
    CountryCodePicker countryCodePicker;
    private SignUpScreenPresenterInterface signUpScreenPresenterInterface;
    public static final String USER_KEY = "USER";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUI();

    }

    private boolean checkForEditTexts() {
        String userName = userNameEditText.getText().toString().trim();
        String phoneNumber = phoneNumberEditText.getText().toString().trim();
        boolean result = false;

        if(userName.isEmpty()) {
            userNameEditText.setError("User name is required");
            userNameEditText.requestFocus();
        }
        if(phoneNumber.isEmpty()) {
            phoneNumberEditText.setError("Phone number is required");
            phoneNumberEditText.requestFocus();
        }else {
            result = true;
        }

        return result;
    }

    void initUI() {
        userNameEditText = findViewById(R.id.usernameEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        countryCodePicker = findViewById(R.id.countryCodePicker);
        signUpButton = findViewById(R.id.signUpButton);
        signUpScreenPresenterInterface = new SignUpScreenPresenter(SignUpActivity.this);
        countryCodePicker.registerCarrierNumberEditText(phoneNumberEditText);

        signUpButton.setOnClickListener(view -> {
            User user = createNewUser();
            Intent intent = new Intent(SignUpActivity.this,VerifyPhoneNumberActivity.class);
            intent.putExtra(USER_KEY,user);
            startActivity(intent);
        });
    }



    private User createNewUser() {
        User user = null;
        if(checkForEditTexts()) {
            user = new User(userNameEditText.getText().toString(),countryCodePicker.getFullNumberWithPlus());
            signUpScreenPresenterInterface.createNewUser(user);
        }
        return user;
    }



    @Override
    public void sendUserData(User user) {

    }

}