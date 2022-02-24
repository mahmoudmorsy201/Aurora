package com.iti.aurora.usersystem.signup.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.hbb20.CountryCodePicker;
import com.iti.aurora.MainHome;
import com.iti.aurora.R;
import com.iti.aurora.database.ConcreteLocalSource;
import com.iti.aurora.database.dao.DAOUser;
import com.iti.aurora.model.User;
import com.iti.aurora.model.medicine.Dose;
import com.iti.aurora.model.medicine.Medicine;
import com.iti.aurora.model.medicine.Treatment;
import com.iti.aurora.usersystem.signup.presenter.SignUpScreenPresenter;
import com.iti.aurora.usersystem.signup.presenter.SignUpScreenPresenterInterface;
import com.iti.aurora.usersystem.verifyphonenumber.view.VerifyPhoneNumberActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


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
    ConcreteLocalSource concreteLocalSource;
    Medicine medicine;
    Date dateNow;
    Date dateTaken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUI();
        mAuth = FirebaseAuth.getInstance();
        concreteLocalSource = ConcreteLocalSource.getInstance(this);
        medicine = new Medicine();
        dateNow = new Date();
        dateTaken = new Date();

        insertMedicine(medicine);


    }

    private void insertMedicine(Medicine medicine) {
        concreteLocalSource.insertMedicine(medicine)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Long aLong) {
                        medicine.setMedId(aLong);
                        Treatment treatment = new Treatment(medicine.getMedId(), dateNow, dateTaken);
                        insertTreatment(treatment);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    private void insertTreatment(Treatment treatment) {
        concreteLocalSource.insetTreatment(treatment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Long aLong) {
                        treatment.setTreatmentId(aLong);
                        Dose dose = new Dose(dateNow, true, dateTaken, medicine.getMedId(), treatment.getTreatmentId());
                        List<Dose> doses = new ArrayList<>();
                        for(int i=0 ; i<10 ; i++) {
                            doses.add(dose);

                        }
                        concreteLocalSource.insertDoses(doses);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
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
        signUpScreenPresenterInterface = new SignUpScreenPresenter(SignUpActivity.this, SignUpActivity.this);
        countryCodePicker.registerCarrierNumberEditText(phoneNumberEditText);
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        daoUser = new DAOUser(this);

        signUpButton.setOnClickListener(view -> {
            if (checkForEditTexts()) {
                User user = createNewUser();
                Intent intent = new Intent(SignUpActivity.this, VerifyPhoneNumberActivity.class);
                intent.putExtra(USER_KEY, user);
                startActivity(intent);
            }

        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkForEditTexts()) {
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
        if (firebaseUser != null) {
            Intent intent = new Intent(SignUpActivity.this, MainHome.class);
            intent.putExtra("GOOGLE_ACCOUNT", firebaseUser);
            startActivity(intent);
        } else {
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
                        if (Objects.requireNonNull(authResult.getAdditionalUserInfo()).isNewUser()) {
                            Log.i(TAG, "Account created");
                            User newUser = new User(userNameEditText.getText().toString(), phoneNumberEditText.getText().toString());
                            daoUser.checkForDuplicates(newUser);
                        } else {
                            Log.i(TAG, "Already Existed");
                        }
                        gotToHomePage(firebaseUser);
                    }
                });

    }

}