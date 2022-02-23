package com.iti.aurora.utils.spfactory;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesFactory {

    private static final String SHARED_PREFERENCES_FILE_NAME = "SHARED_PREFERENCES_FILE_NAME";
    SharedPreferencesFactory instance = null;
    SharedPreferences sharedPreferences;

    private SharedPreferencesFactory(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
    }

    public SharedPreferencesFactory getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesFactory(context);
        }
        return instance;
    }

    //TODO setter and getter for every function
}
