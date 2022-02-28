package com.iti.aurora.utils.dialogs.refillask;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.iti.aurora.R;

public class RefillReminderDialog extends Dialog {

    Group refillGroup;

    Button dismissDialogButton;

    Button remindMeButton;

    long medicineId;

    TextInputLayout numberOfDosageTextInputLayout;
    TextInputLayout numberOfDosagePerPackTextInputLayout;

    TextInputEditText numberOfDosageTextInputEditText;
    TextInputEditText numberOfDosagePerPackTextInputEditText;

    RadioButton remindMeRadioButton;
    RadioButton noThanksRadioButton;
    RefillDialogRemindMeClickHandler refillDialogRemindMeClickHandler;

    public RefillReminderDialog(@NonNull Context context, long medicineId, RefillDialogRemindMeClickHandler refillDialogRemindMeClickHandler) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_refill_reminder);

        this.refillDialogRemindMeClickHandler = refillDialogRemindMeClickHandler;

        this.medicineId = medicineId;

        refillGroup = findViewById(R.id.remindMeGroup);
        dismissDialogButton = findViewById(R.id.dismissDialogButton);

        remindMeButton = findViewById(R.id.remindMeButton);

        remindMeRadioButton = findViewById(R.id.remindMeRadioButton);
        noThanksRadioButton = findViewById(R.id.noThanksRadioButton);

        numberOfDosageTextInputLayout = findViewById(R.id.dosageUserHasTextInputLayout);
        numberOfDosagePerPackTextInputLayout = findViewById(R.id.noOfDosagesPerPackTextInputLayout);

        numberOfDosageTextInputEditText = findViewById(R.id.dosageUserHasTextInputEditText);
        numberOfDosagePerPackTextInputEditText = findViewById(R.id.noOfDosagesPerPackTextInputEditText);

        dismissDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RefillReminderDialog.this.dismiss();
                refillDialogRemindMeClickHandler.addMedicineWithoutReminder();
            }
        });

        remindMeRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    refillGroup.setVisibility(View.VISIBLE);
                    dismissDialogButton.setVisibility(View.GONE);
                } else {
                    refillGroup.setVisibility(View.GONE);
                    dismissDialogButton.setVisibility(View.VISIBLE);
                }
            }
        });

        noThanksRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    refillGroup.setVisibility(View.GONE);
                    dismissDialogButton.setVisibility(View.VISIBLE);
                } else {
                    refillGroup.setVisibility(View.VISIBLE);
                    dismissDialogButton.setVisibility(View.GONE);
                }
            }
        });

        remindMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!numberOfDosageTextInputEditText.getText().toString().isEmpty()) {
                    numberOfDosageTextInputLayout.setError("");
                    if (!numberOfDosagePerPackTextInputEditText.getText().toString().isEmpty()) {

                        numberOfDosagePerPackTextInputLayout.setError("");
                        int numberOfCurrentDosages = Integer.parseInt(numberOfDosageTextInputEditText.getText().toString());
                        int numberOfDosagesPerPack = Integer.parseInt(numberOfDosagePerPackTextInputEditText.getText().toString());

                        RefillReminderDialog.this.dismiss();
                        refillDialogRemindMeClickHandler.addRefillReminderToMedicine(medicineId, numberOfCurrentDosages, numberOfDosagesPerPack);

                    } else {
                        numberOfDosagePerPackTextInputLayout.setError(context.getResources().getString(R.string.pleaseEnterNoOfDosagesPerPack));
                    }
                } else {
                    numberOfDosageTextInputLayout.setError(context.getResources().getString(R.string.pleaseEnterNoOfCurrentDosages));
                }
            }
        });
    }
}