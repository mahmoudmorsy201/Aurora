package com.iti.aurora.addmedicine;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.iti.aurora.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

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

    TextView startDatepickerAddmedication_Textview, endDatePicker_textview, timePicker_textview;
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
    final static String[] formType = {"Select Medication Type", "Pills", "solution", "Injection", "Powder",
            "Drops", "Inhaler", "Others"};
    final static String[] strength = {"Strength ", "g", "mg", "UI", "mcg", "mcg/ml", "mEq", "mL", "%", "mg/g", "mg/cm2", "mg/ml", "mcg/hr"};
    final static String[] instructions = {"Taken with food?", "Before eating", "While eating", "After eating", "Doesn’t matter"};
    final static String[] recurrency = {"Select Doses", "Once a week", "Every day", "Every 2 days", "Every 3 days", "2 days a week", "3 days a week", "5 days a week", "Every 28 days"};

    Calendar myCalendar = Calendar.getInstance();
    String myFormat = "MM/dd/yy";
    int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
    int minute = myCalendar.get(Calendar.MINUTE);

    SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_add_medication);
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


        setSpinnerAdapter(formAddMedication_spinner, formType);
        setSpinnerAdapter(instructionsAddMedication_spinner, instructions);
        setSpinnerAdapter(recurrencyAddMedication_spinner, recurrency);
        setSpinnerAdapter(strenghtAddMedication_spinner, strength);
        DatePickerDialog.OnDateSetListener startDate = (view, year, month, day) -> {
            setMyCalendar(year, month, day);
            setStartDateTimeField();
        };
        DatePickerDialog.OnDateSetListener endtDate = (view, year, month, day) -> {
            setMyCalendar(year, month, day);
            setEndDatePickerTimeField();
        };

        startDatepickerAddmedication_Textview.setOnClickListener(view -> {

            new DatePickerDialog(this, startDate, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        });
        endDatePicker_textview.setOnClickListener(view -> {
            new DatePickerDialog(this, endtDate, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        });

        timePicker_textview.setOnClickListener(view -> {
            new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {


                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
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
            textInputLayout_addMedication.setErrorEnabled(false);
            strengthMedication_TextInputEditText.setErrorEnabled(false);
            reason_TextInputEditText.setErrorEnabled(false);
            if (checkInputMedication()) {
                getMedicationFormValue();
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
        resonMedication = reason_inputEditText.getText().toString();
        startDate = startDatepickerAddmedication_Textview.getText().toString();
        endDate = endDatePicker_textview.getText().toString();
        time = timePicker_textview.getText().toString();
    }


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
        if (Objects.requireNonNull(strengthAddMedication_inputEditText.getText()).length() > 0)
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
}