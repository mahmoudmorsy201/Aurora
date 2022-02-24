package com.iti.aurora.usersystem.signup.presenter;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.iti.aurora.model.User;
import com.iti.aurora.usersystem.signup.view.OnRegistrationListener;
import com.iti.aurora.usersystem.signup.view.SignUpContract;

/*public class SignUpInteractor implements SignUpContract.Intractor {

    private static final String TAG = SignUpInteractor.class.getSimpleName();
    private OnRegistrationListener mOnRegistrationListener;

    public SignUpInteractor(OnRegistrationListener onRegistrationListener){
        this.mOnRegistrationListener = onRegistrationListener;
    }
    @Override
    public void performFirebaseRegistration(Activity activity, User user) {
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            mOnRegistrationListener.onFailure(task.getException().getMessage());
                        }else{
                            mOnRegistrationListener.onSuccess(task.getResult().getUser());
                        }
                    }
                });
    }
}*/
