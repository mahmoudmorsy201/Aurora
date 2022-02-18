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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.hbb20.CountryCodePicker;
import com.iti.aurora.R;
import com.iti.aurora.database.DAOUser;
import com.iti.aurora.home.view.MainActivity;
import com.iti.aurora.model.User;
import com.iti.aurora.usersystem.signup.presenter.SignUpScreenPresenter;
import com.iti.aurora.usersystem.signup.presenter.SignUpScreenPresenterInterface;
import com.iti.aurora.usersystem.verifyphonenumber.view.VerifyPhoneNumberActivity;

import java.util.Objects;


public class SignUpActivity extends AppCompatActivity implements SignUpViewInterface {

    private static final int RC_SIGN_IN = 10;
    EditText userNameEditText, phoneNumberEditText;
    Button signUpButton;
    CountryCodePicker countryCodePicker;
    SignInButton signInButton;
    private SignUpScreenPresenterInterface signUpScreenPresenterInterface;
    public static final String USER_KEY = "USER";
    public static final String TAG = "TAG";
    DAOUser daoUser;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUI();
        mAuth = FirebaseAuth.getInstance();

    }

    private boolean checkForEditTexts() {
        String userName = userNameEditText.getText().toString().trim();
        String phoneNumber = phoneNumberEditText.getText().toString().trim();
        boolean result = false;

        if (userName.isEmpty()) {
            userNameEditText.setError("User name is required");
            userNameEditText.requestFocus();
        }
        if (phoneNumber.isEmpty()) {
            phoneNumberEditText.setError("Phone number is required");
            phoneNumberEditText.requestFocus();
        } else {
            result = true;
        }

        return result;
    }

    void initUI() {
        userNameEditText = findViewById(R.id.usernameEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        countryCodePicker = findViewById(R.id.countryCodePicker);
        signUpButton = findViewById(R.id.signUpButton);
        signUpScreenPresenterInterface = new SignUpScreenPresenter(SignUpActivity.this,SignUpActivity.this);
        countryCodePicker.registerCarrierNumberEditText(phoneNumberEditText);
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        daoUser = new DAOUser(this);

        signUpButton.setOnClickListener(view -> {
            if(checkForEditTexts()) {
                User user = createNewUser();
                Intent intent = new Intent(SignUpActivity.this, VerifyPhoneNumberActivity.class);
                intent.putExtra(USER_KEY, user);
                startActivity(intent);
            }

        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkForEditTexts()) {
                    signInWithGoogle();
                }

            }
        });
    }


    private User createNewUser() {
        User user = null;
        if (checkForEditTexts()) {
            user = new User(userNameEditText.getText().toString(), countryCodePicker.getFullNumberWithPlus());
            signUpScreenPresenterInterface.createNewUser(user);
        }
        return user;
    }


    @Override
    public void sendUserData(User user) {
    }

    @Override
    public void signInWithGoogle() {
        signUpScreenPresenterInterface.signInWithGoogle();
    }

    @Override
    public void gotToHomePage(FirebaseUser firebaseUser) {
        if(firebaseUser != null) {
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            intent.putExtra("GOOGLE_ACCOUNT",firebaseUser);
            startActivity(intent);  
        }else {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void fireSignInWithGoogleIntent() {
        Intent signInIntent = signUpScreenPresenterInterface.getGoogleSignInClient().getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }


//    private void firebaseAuthWithGoogle(String idToken) {
//        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            User newUser = new User(user.getDisplayName(),user.getPhoneNumber());
//
//                            Log.i(TAG, "User Phone" + user.getPhoneNumber());
//                            daoUser.checkForDuplicates(newUser);
//                            gotToHomePage(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            //updateUI(null);
//                        }
//                    }
//                });
//    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        if(Objects.requireNonNull(authResult.getAdditionalUserInfo()).isNewUser()) {
                            Log.i(TAG, "Account created");
                            User newUser = new User(userNameEditText.getText().toString(),phoneNumberEditText.getText().toString());
                            daoUser.checkForDuplicates(newUser);
                        }else {
                            Log.i(TAG, "Already Existed");
                        }
                        gotToHomePage(firebaseUser);
                    }
                });

    }

}