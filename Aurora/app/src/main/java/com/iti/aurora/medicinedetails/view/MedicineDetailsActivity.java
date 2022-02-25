package com.iti.aurora.medicinedetails.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iti.aurora.R;
import com.iti.aurora.database.ConcreteLocalSource;
import com.iti.aurora.model.Repository;
import com.iti.aurora.model.RepositoryInterface;
import com.iti.aurora.model.medicine.Medicine;
import com.iti.aurora.utils.Constants;
import com.iti.aurora.utils.dialogs.TwoButtonsDialog;

import java.text.MessageFormat;

public class MedicineDetailsActivity extends AppCompatActivity {

    Medicine medicine;

    ImageView medicineImageView;
    TextView medicineNameTextView;
    TextView medicineStrengthTextView;
    TextView numberOfBillsLeftTextView;
    TextView numberOfBillsPerPackTextView;
    TextView remindMeWhenTextView;

    TextView instructionTextView;
    TextView reasonOfTakingUserValueTextView;

    Button activeOrSuspendButton;

    ImageView deleteImageView;

    RepositoryInterface repositoryInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_details);
        repositoryInterface = Repository.getInstance(ConcreteLocalSource.getInstance(this), this);

        medicine = (Medicine) getIntent().getSerializableExtra(Constants.MEDICINE_PASSING_FLAG);

        medicineImageView = findViewById(R.id.medicineImageView);
        medicineNameTextView = findViewById(R.id.medicineNameTextView);
        medicineStrengthTextView = findViewById(R.id.medicineStrengthTextView);
        activeOrSuspendButton = findViewById(R.id.activeOrSuspendButton);
        numberOfBillsLeftTextView = findViewById(R.id.numberOfBillsLeftTextView);
        numberOfBillsPerPackTextView = findViewById(R.id.numberOfBillsPerPackTextView);
        remindMeWhenTextView = findViewById(R.id.remindMeWhenTextView);
        instructionTextView = findViewById(R.id.instructionTextView);
        reasonOfTakingUserValueTextView = findViewById(R.id.reasonOfTakingUserValueTextView);
        deleteImageView = findViewById(R.id.deleteImageView);

        deleteImageView.setOnClickListener(view -> TwoButtonsDialog.TwoButtonsDialogBuilder(
                MedicineDetailsActivity.this,
                getResources().getString(R.string.deleteMedicine),
                getResources().getString(R.string.doYouWantToDeleteThisMedicine),
                getResources().getString(R.string.deleteMedicine),
                getResources().getString(R.string.cancel),
                (dialogInterface, i) -> {
                    repositoryInterface.deleteMedicine(medicine);
                    dialogInterface.dismiss();
                    MedicineDetailsActivity.this.finish();
                }, (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                }
        ).show());


        if (medicine != null) {
            medicineNameTextView.setText(medicine.getName());
            medicineStrengthTextView.setText(MessageFormat.format("{0} {1}", medicine.getNumberOfUnits(), medicine.getStrengthUnit()));
            if (medicine.getActive()) {
                activeOrSuspendButton.setText(getResources().getString(R.string.suspended));
            } else {
                activeOrSuspendButton.setText(getResources().getString(R.string.active));
            }
            numberOfBillsLeftTextView.setText(MessageFormat.format("{0} {1}", medicine.getDosagesLeft(), getResources().getString(R.string.dosageLeft)));
            numberOfBillsPerPackTextView.setText(MessageFormat.format("{0} {1}", medicine.getDosagesPerPack(), getResources().getString(R.string.dosagesPerPack)));
            instructionTextView.setText(medicine.getInstruction());
            reasonOfTakingUserValueTextView.setText(medicine.getReasonOfTaking());
        }

    }
}