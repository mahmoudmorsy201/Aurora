package com.iti.aurora.addmedicine;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.iti.aurora.R;
import com.iti.aurora.database.ConcreteLocalSource;
import com.iti.aurora.model.medicine.Medicine;
import com.iti.aurora.model.medicine.StrengthUnit;
import com.iti.aurora.model.medicine.Treatment;
import com.iti.aurora.utils.selectdays.IUpdateText;
import com.iti.aurora.utils.selectdays.SelectDaysAlertDialog;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddMedicineActivity extends AppCompatActivity {
    TextInputEditText nameAddMedication_inputEditText,
            strengthAddMedication_inputEditText,
            reason_inputEditText;
    TextInputLayout textInputLayout_addMedication,
            strengthMedication_TextInputEditText,
            reason_TextInputEditText;
    Spinner formAddMedication_spinner,
            recurrencyAddMedication_spinner,
            instructionsAddMedication_spinner,
            strenghtAddMedication_spinner;
    CardView datesAddMedication_cardview;
    TextView startDatepickerAddmedication_Textview, endDatePicker_textview, timePicker_textview;
    TextView selectedDaysTextView;
    Button addMedication_button;
    static String nameMedication,
            strenghtNameMedication,
            formTypeMedication,
            recurrencyMedication,
            instructionMedication,
            strenghtMedicationValue,
            resonMedication,
            startDate,
            endDate, time;
    //todo days cardview;
    final static String[] formType = {"Select Medication Type", "Pills", "Solution", "Injection", "Powder",
            "Drops", "Inhaler", "Others"};
    final static String[] strength = {"Strength ", "g", "mg", "IU", "mcg", "mcg_ml", "mEq", "mL", "percentage", "mg_g", "mg_cm2", "mg_ml", "mcg_hr"};
    final static String[] instructions = {"Taken with food?", "Before eating", "While eating", "After eating", "Doesn’t matter"};
    final static String[] recurrency = {"Select Doses", "Once a week", "Every day", "Every 2 days", "Every 3 days", "2 days a week", "3 days a week", "5 days a week", "Every 28 days"};
    int recurrencyDaysNumber;
    Calendar myCalendar = Calendar.getInstance();
    String myFormat = "MM/dd/yy";
    int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
    int minute = myCalendar.get(Calendar.MINUTE);

    SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
    SelectDaysAlertDialog selectDaysAlertDialog = new SelectDaysAlertDialog(this, new IUpdateText() {
        @Override
        public void updateText(String text) {
            selectedDaysTextView.setText(text);
        }
    });

    private DateTime selectedStartDate;
    private DateTime selectedEndDate;
    private ConcreteLocalSource concreteLocalSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_add_medication);
        concreteLocalSource = ConcreteLocalSource.getInstance(AddMedicineActivity.this);
        selectedStartDate = new DateTime();
        selectedEndDate = new DateTime();
        setInitialUI();
    }

    private void setInitialUI() {
        nameAddMedication_inputEditText = findViewById(R.id.nameAddMedication_inputEditText);
        textInputLayout_addMedication = findViewById(R.id.textInputLayout_addMedication);
        strengthAddMedication_inputEditText = findViewById(R.id.strengthAddMedication_inputEditText);
        strengthMedication_TextInputEditText = findViewById(R.id.strengthMedication_TextInputEditText);
        reason_inputEditText = findViewById(R.id.reason_inputEditText);
        reason_TextInputEditText = findViewById(R.id.reasonOfTakingTextView);
        formAddMedication_spinner = findViewById(R.id.formAddMedication_spinner);
        recurrencyAddMedication_spinner = findViewById(R.id.recurrencyAddMedication_spinner);
        instructionsAddMedication_spinner = findViewById(R.id.instructionsAddMedication_spinner);
        strenghtAddMedication_spinner = findViewById(R.id.strenghtAddMedication_spinner);
        startDatepickerAddmedication_Textview = findViewById(R.id.startDatepickerAddmedication_Textview);
        endDatePicker_textview = findViewById(R.id.endDatePicker_textview);
        timePicker_textview = findViewById(R.id.timePicker_textview);
        addMedication_button = findViewById(R.id.addMedication_button);
        datesAddMedication_cardview = findViewById(R.id.datesAddMedication_cardview);

        selectedDaysTextView = findViewById(R.id.dayTextView);
        selectedDaysTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDaysAlertDialog = new SelectDaysAlertDialog(AddMedicineActivity.this, new IUpdateText() {
                    @Override
                    public void updateText(String text) {
                        selectedDaysTextView.setText(text);
                    }
                }, recurrencyDaysNumber);
                selectDaysAlertDialog.showSelectDaysDialog();
            }
        });

        setSpinnerAdapter(formAddMedication_spinner, formType);
        setSpinnerAdapter(instructionsAddMedication_spinner, instructions);
        setSpinnerAdapter(recurrencyAddMedication_spinner, recurrency);
        setSpinnerAdapter(strenghtAddMedication_spinner, strength);
        DatePickerDialog.OnDateSetListener startDate = (view, year, month, day) -> {
            setMyCalendar(year, month, day);
            selectedStartDate = new DateTime(year, month + 1, day, selectedStartDate.getHourOfDay(), selectedStartDate.getMinuteOfHour());
            setStartDateTimeField();
        };
        DatePickerDialog.OnDateSetListener endDate = (view, year, month, day) -> {
            setMyCalendar(year, month, day);
            selectedEndDate = new DateTime(year, month + 1, day, selectedStartDate.getHourOfDay(), selectedStartDate.getMinuteOfHour());
            setEndDatePickerTimeField();
        };

        startDatepickerAddmedication_Textview.setOnClickListener(view -> {

            new DatePickerDialog(this, startDate, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        });
        endDatePicker_textview.setOnClickListener(view -> {
            new DatePickerDialog(this, endDate, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        });

        timePicker_textview.setOnClickListener(view -> {
            new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            selectedStartDate = new DateTime(selectedStartDate.getYear(), selectedStartDate.getMonthOfYear(), selectedStartDate.getDayOfMonth(), hourOfDay, minute);
                            String AM_PM;
                            int hours = hourOfDay;
                            if (hourOfDay < 12) {
                                AM_PM = "AM";

                            } else {
                                AM_PM = "PM";
                                hours -= 12;
                            }
                            timePicker_textview.setText(hours + ":" + minute + " " + AM_PM);
                        }
                    }, hour, minute, false).show();
        });

        addMedication_button.setOnClickListener(view -> {
            if (checkInputMedication()) {
                getMedicationFormValue();
            }
        });
        recurrencyAddMedication_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String itemSelected = adapterView.getSelectedItem().toString();
                if ((itemSelected.equals("Select Doses") |
                        itemSelected.equals("Every day") |
                        itemSelected.equals("Every 2 days") |
                        itemSelected.equals("Every 3 days") |
                        itemSelected.equals("Every 28 days"))) {
                    datesAddMedication_cardview.setVisibility(View.INVISIBLE);
                } else {
                    datesAddMedication_cardview.setVisibility(View.VISIBLE);
                    recurrencyDaysNumber = mapDosesNameWithNum(recurrencyAddMedication_spinner.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getMedicationFormValue() {
        //todo get values
        nameMedication = String.valueOf(nameAddMedication_inputEditText.getText());
        formTypeMedication = formAddMedication_spinner.getSelectedItem().toString();
        strenghtNameMedication = String.valueOf(strengthAddMedication_inputEditText.getText());
        strenghtMedicationValue = strenghtAddMedication_spinner.getSelectedItem().toString();
        recurrencyMedication = recurrencyAddMedication_spinner.getSelectedItem().toString();
        instructionMedication = instructionsAddMedication_spinner.getSelectedItem().toString();
        resonMedication = String.valueOf(reason_inputEditText.getText());

        startDate = startDatepickerAddmedication_Textview.getText().toString();
        endDate = endDatePicker_textview.getText().toString();
        time = timePicker_textview.getText().toString();

        Medicine medicine = new Medicine();
        medicine.setName(nameMedication);
        medicine.setActive(true);
        medicine.setStrengthUnit(StrengthUnit.valueOf(strenghtMedicationValue));
        medicine.setNumberOfUnits(Integer.parseInt(strenghtNameMedication));
        medicine.setInstruction(instructionMedication);
        medicine.setMedicineForm(formTypeMedication);
        medicine.setReasonOfTaking(resonMedication);

        insertMedicine(medicine, selectedStartDate, selectedEndDate);
    }

    private void insertMedicine(Medicine medicine, DateTime startDate, DateTime endDate) {

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
                        Treatment treatment = new Treatment(medicine.getMedId(), startDate.toDate(), endDate.toDate());
                        //insertTreatment(treatment, aLong);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    /*
    private void insertTreatment(Treatment treatment, long medicineId) {
        concreteLocalSource.insetTreatment(treatment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Long aLong) {
                        long treatmentId = aLong;
                        treatment.setTreatmentId(aLong);
                        //TODO
                        Dose dose = new Dose(dateNow, true, dateTaken, medicine.getMedId(), treatment.getTreatmentId());
                        List<Dose> doses = new ArrayList<>();
                        for (int i = 0; i < 10; i++) {
                            doses.add(dose);

                        }
                        concreteLocalSource.insertDoses(doses);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
*/

    private void setSpinnerAdapter(Spinner spinner, String[] formArray) {
        ArrayAdapter<String> formArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, formArray);
        formArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(formArrayAdapter);
    }

    private void setMyTime(int hour, int minutes) {

        timePicker_textview.setText("");
    }

    private void setMyCalendar(int year, int month, int day) {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, month);
        myCalendar.set(Calendar.DAY_OF_MONTH, day);
    }

    private void setStartDateTimeField() {
        startDatepickerAddmedication_Textview.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void setEndDatePickerTimeField() {
        endDatePicker_textview.setText(dateFormat.format(myCalendar.getTime()));
    }

    private boolean checkInputMedication() {
        boolean isValid = false;
        if ((Objects.requireNonNull(nameAddMedication_inputEditText.getText()).length() > 0)) {
            isValid = true;
            String s = nameAddMedication_inputEditText.getText().toString();
            s.length();
        } else {
            textInputLayout_addMedication.setErrorEnabled(true);
            textInputLayout_addMedication.setError("enter name");
            isValid = false;
        }
        if (Objects.requireNonNull(strengthAddMedication_inputEditText.getText().toString()).length() > 0)
            isValid = true;
        else {
            strengthMedication_TextInputEditText.setErrorEnabled(true);
            strengthMedication_TextInputEditText.setError("Enter Strength");
            isValid = false;
        }
        if (Objects.requireNonNull(reason_inputEditText.getText()).length() > 0)
            isValid = true;
        else {
            reason_TextInputEditText.setErrorEnabled(true);
            reason_TextInputEditText.setError("Please specify reason");
            isValid = false;
        }
        if (strenghtAddMedication_spinner.getSelectedItemPosition() != strenghtAddMedication_spinner.getItemIdAtPosition(0)) {
            isValid = true;
        } else {

            strenghtAddMedication_spinner.getChildAt(0).setBackgroundColor(getResources().getColor(R.color.warning));
            isValid = false;
        }
        if (formAddMedication_spinner.getSelectedItemPosition() != formAddMedication_spinner.getItemIdAtPosition(0)) {
            isValid = true;
        } else {
            formAddMedication_spinner.getChildAt(0).setBackgroundColor(getResources().getColor(R.color.warning));
            isValid = false;
        }
        if (recurrencyAddMedication_spinner.getSelectedItemPosition() != recurrencyAddMedication_spinner.getItemIdAtPosition(0)) {
            isValid = true;
        } else {
            recurrencyAddMedication_spinner.getChildAt(0).setBackgroundColor(getResources().getColor(R.color.warning));
            isValid = false;
        }
        if (instructionsAddMedication_spinner.getSelectedItemPosition() != instructionsAddMedication_spinner.getItemIdAtPosition(0)) {
            isValid = true;
        } else {
            instructionsAddMedication_spinner.getChildAt(0).setBackgroundColor(getResources().getColor(R.color.warning));
            isValid = false;
        }

        if (!startDatepickerAddmedication_Textview.getText().toString().equals(""))
            isValid = true;
        else {
            startDatepickerAddmedication_Textview.setHintTextColor(getResources().getColor(R.color.warning));
            isValid = false;
        }
        if (!endDatePicker_textview.getText().toString().equals(""))

            isValid = true;
        else {
            endDatePicker_textview.setHintTextColor(getResources().getColor(R.color.warning));
            isValid = false;
        }
        if (!timePicker_textview.getText().toString().equals(""))

            isValid = true;
        else {
            timePicker_textview.setHintTextColor(getResources().getColor(R.color.warning));
            isValid = false;
        }


        return isValid;
    }

    private int mapDosesNameWithNum(String name) {
        int numberOfDays = 0;
        switch (name) {
            case "Once a week":
                numberOfDays = 1;
                break;
            case "2 days a week":
                numberOfDays = 2;
                break;
            case "3 days a week":
                numberOfDays = 3;
                break;
            case "5 days a week":
                numberOfDays = 5;
                break;
        }


        return numberOfDays;
    }
}