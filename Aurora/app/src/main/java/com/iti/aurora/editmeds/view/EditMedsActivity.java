package com.iti.aurora.editmeds.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.iti.aurora.R;

public class EditMedsActivity extends AppCompatActivity {

    ImageView medicationNameShow, reminderTimesShow, scheduleShow, medIconShow, conditionShow, strengthShow, instructionShow, prescriptionRefillShow;
    CardView medicationNameCardView, reminderTimesCardView, scheduleCardView, medIconCardView, conditionCardView, strengthCardView, instructionCardView, prescriptionRefillCardView;
    View medicationNameView, reminderTimesView, scheduleView, medIconView, conditionView, strengthView, instructionView, prescriptionRefillView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meds);
        initUI();
        handleClickOnCardViews();

    }

    private void initUI() {
        medicationNameShow = findViewById(R.id.medicationNameShow);
        reminderTimesShow = findViewById(R.id.reminderTimesShow);
        scheduleShow = findViewById(R.id.scheduleShow);
        medIconShow = findViewById(R.id.medIconShow);
        conditionShow = findViewById(R.id.conditionShow);
        strengthShow = findViewById(R.id.strengthShow);
        instructionShow = findViewById(R.id.instructionsShow);
        prescriptionRefillShow = findViewById(R.id.prescriptionRefillShow);

        medicationNameCardView = findViewById(R.id.medNameCardView);
        reminderTimesCardView = findViewById(R.id.reminderTimes);
        scheduleCardView = findViewById(R.id.scheduleCardView);
        medIconCardView = findViewById(R.id.medIconCardView);
        conditionCardView = findViewById(R.id.conditionCardView);
        strengthCardView = findViewById(R.id.strengthCardView);
        instructionCardView = findViewById(R.id.instructionsCardView);
        prescriptionRefillCardView = findViewById(R.id.prescriptionRefillCardView);

        medicationNameView = findViewById(R.id.medNameGroup);
        reminderTimesView = findViewById(R.id.reminderCardGroup);
        scheduleView = findViewById(R.id.scheduleGroup);
        medIconView = findViewById(R.id.medIconGroup);
        conditionView = findViewById(R.id.conditionGroup);
        strengthView = findViewById(R.id.strengthGroup);
        instructionView = findViewById(R.id.instructionsGroup);
        prescriptionRefillView = findViewById(R.id.prescriptionRefillGroup);
    }

    private void handleClickOnCardViews() {
        medicationNameCardView.setOnClickListener(view -> makeTransition(medicationNameView, medicationNameShow, medicationNameCardView));

        reminderTimesCardView.setOnClickListener(view -> makeTransition(reminderTimesView, reminderTimesShow, reminderTimesCardView));

        scheduleCardView.setOnClickListener(view -> makeTransition(scheduleView, scheduleShow, scheduleCardView));

        medIconCardView.setOnClickListener(view -> makeTransition(medIconView, medIconShow, medIconCardView));

        conditionCardView.setOnClickListener(view -> makeTransition(conditionView, conditionShow, conditionCardView));

        strengthCardView.setOnClickListener(view -> makeTransition(strengthView, strengthShow, strengthCardView));

        instructionCardView.setOnClickListener(view -> makeTransition(instructionView, instructionShow, instructionCardView));

        prescriptionRefillCardView.setOnClickListener(view -> makeTransition(prescriptionRefillView, prescriptionRefillShow, prescriptionRefillCardView));

    }

    private void makeTransition(View view, ImageView image, CardView cardView) {
        if (view.getVisibility() == View.VISIBLE) {
            TransitionManager.beginDelayedTransition(cardView,
                    new AutoTransition());
            view.setVisibility(View.GONE);
            image.setImageResource(com.hbb20.R.drawable.ccp_ic_arrow_drop_down);

        } else {
            TransitionManager.beginDelayedTransition(cardView,
                    new AutoTransition());
            view.setVisibility(View.VISIBLE);
            image.setImageResource(com.hbb20.R.drawable.ccp_ic_arrow_drop_down);
        }
    }
}