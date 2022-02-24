package com.iti.aurora.utils;

import android.media.RingtoneManager;
import android.net.Uri;

public class Constants {
    public class FirebaseConstants {
        public static final String USERS = "users";
        public static final String PHONE_NUMBER = "phoneNumber";
        public static final String USER_NAME = "userName";
    }

    public static class AddMedicineConstants {
        public static final String[] formType = {"Select Medication Type", "Pills", "Solution", "Injection", "Powder",
                "Drops", "Inhaler", "Others"};
        public static final String[] strength = {"Strength ", "g", "mg", "IU", "mcg", "mcg_ml", "mEq", "mL", "percentage", "mg_g", "mg_cm2", "mg_ml", "mcg_hr"};
        public static final String[] instructions = {"Taken with food?", "Before eating", "While eating", "After eating", "Doesn’t matter"};
        public static final String[] recurrency = {"Select Doses", "Once a week", "Every day", "Every 2 days", "Every 3 days", "Two days a week", "Three days a week", "Five days a week", "Every 28 days"};
    }

    public static class NotificationUtil {
        public static final Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    }

    public static final String MEDICINE_PASSING_FLAG = "MEDICINE_PASSING_FLAG";
}