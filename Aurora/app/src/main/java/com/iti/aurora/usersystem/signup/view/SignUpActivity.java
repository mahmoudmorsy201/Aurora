package com.iti.aurora.usersystem.signup.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;
import com.iti.aurora.R;
import com.iti.aurora.usersystem.signup.presenter.SignUpScreenPresenter;
import com.iti.aurora.usersystem.signup.presenter.SignUpScreenPresenterInterface;
import com.iti.aurora.usersystem.verifyphonenumber.view.VerifyPhoneNumberActivity;
import com.iti.model.User;

import java.util.concurrent.TimeUnit;


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

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = createNewUser();
                Intent intent = new Intent(SignUpActivity.this,VerifyPhoneNumberActivity.class);
                intent.putExtra(USER_KEY,user);
                startActivity(intent);
            }
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