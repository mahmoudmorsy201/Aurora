package com.iti.aurora.medicinedetails.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iti.aurora.R;
import com.iti.aurora.database.ConcreteLocalSource;
import com.iti.aurora.editmeds.view.EditMedsActivity;
import com.iti.aurora.medicinedetails.presenter.MedicineDetailsPresenter;
import com.iti.aurora.medicinedetails.presenter.MedicineDetailsPresenterInterface;
import com.iti.aurora.model.Repository;
import com.iti.aurora.model.RepositoryInterface;
import com.iti.aurora.model.medicine.Medicine;
import com.iti.aurora.utils.Constants;
import com.iti.aurora.utils.dialogs.TwoButtonsDialog;
import com.iti.aurora.utils.dialogs.refillmedicine.RefillMedicineClickHandler;
import com.iti.aurora.utils.dialogs.refillmedicine.RefillMedicineDialog;

import java.text.MessageFormat;

public class MedicineDetailsActivity extends AppCompatActivity implements MedicineDetailsViewInterface {

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
    Button refillButton;

    ImageView deleteImageView;
    ImageView editMedicineImageView;

    RepositoryInterface repositoryInterface;

    MedicineDetailsPresenterInterface presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_details);
        repositoryInterface = Repository.getInstance(ConcreteLocalSource.getInstance(this), this);

        medicine = (Medicine) getIntent().getSerializableExtra(Constants.MEDICINE_PASSING_FLAG);

        presenter = new MedicineDetailsPresenter(MedicineDetailsActivity.this, Repository.getInstance(ConcreteLocalSource.getInstance(MedicineDetailsActivity.this), this));

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
        editMedicineImageView = findViewById(R.id.editImageView);
        refillButton = findViewById(R.id.refillButton);

        deleteImageView.setOnClickListener(view -> TwoButtonsDialog.TwoButtonsDialogBuilder(
                MedicineDetailsActivity.this,
                getResources().getString(R.string.deleteMedicine),
                getResources().getString(R.string.doYouWantToDeleteThisMedicine),
                getResources().getString(R.string.deleteMedicine),
                getResources().getString(R.string.cancel),
                (dialogInterface, i) -> {
                    presenter.deleteMedicine(medicine);
                    dialogInterface.dismiss();
                    MedicineDetailsActivity.this.finish();
                }, (dialogInterface, i) -> dialogInterface.dismiss()
        ).show());

        editMedicineImageView.setOnClickListener(view -> startActivity(new Intent(MedicineDetailsActivity.this, EditMedsActivity.class).putExtra(Constants.MEDICINE_PASSING_FLAG, medicine)));

        refillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RefillMedicineDialog dialog = new RefillMedicineDialog(MedicineDetailsActivity.this, medicine, new RefillMedicineClickHandler() {
                    @Override
                    public void refillMedicine(Medicine medicine, int noOfDosagesToAdd) {
                        presenter.addNumberOfDosagesToMedicine(medicine, noOfDosagesToAdd);
                    }
                });
                dialog.show();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        presenter.getMedicine(medicine.getMedId());
                    }
                });
            }
        });
        showMedicine(medicine);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getMedicine(medicine.getMedId());
    }

    @Override
    public void showMedicine(Medicine medicine) {
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
            remindMeWhenTextView.setText(MessageFormat.format("{0} {1} {2}", getResources().getString(R.string.remind_me_on), medicine.getRemindMeOn(), getResources().getString(R.string.dosageLeft)));
        }
    }
}