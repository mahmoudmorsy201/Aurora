package com.iti.aurora.mainactivity.healthtaker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.iti.aurora.R;
import com.iti.aurora.mainactivity.log.view.LogDosesAdapter;
import com.iti.aurora.model.RepositoryInterface;
import com.iti.aurora.model.medicine.Dose;
import com.iti.aurora.model.medicine.Medicine;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.MessageFormat;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserMedsAdapter extends RecyclerView.Adapter<UserMedsAdapter.ViewHolder> {
    Context context;
    List<Dose> doseList;
    FirebaseFirestore firebaseFirestore;


    public UserMedsAdapter(Context context, List<Dose> doseList) {
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.context = context;
        this.doseList = doseList;
    }

    public void setDoseList(List<Dose> doseList) {
        this.doseList = doseList;
    }

    @NonNull
    @Override
    public UserMedsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserMedsAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dose_log_fragment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserMedsAdapter.ViewHolder holder, int position) {
        holder.dateTimeTitleTextView.setText(new DateTime(doseList.get(position).getTimeToTake()).toString(DateTimeFormat.forPattern("d MMMM hh:mm a")));
        if (doseList.get(position).getTaken() == null || !doseList.get(position).getTaken()) {
            holder.isTakenTextView.setText(context.getResources().getString(R.string.missed));
            holder.isTakenImageView.setImageResource(R.drawable.missed_icon);
        } else {
            holder.isTakenTextView.setText(context.getResources().getString(R.string.taken));
            holder.isTakenImageView.setImageResource(R.drawable.ic_taken_done);
        }
        DocumentReference documentReference = doseList.get(position).getMedicine();
//        firebaseFirestore.document(documentReference.toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    Medicine medicine = task.getResult().toObject(Medicine.class);
//                    holder.nameMedicineTextView.setText(medicine.getName());
//                }
//            }
//        });
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Medicine medicine = task.getResult().toObject(Medicine.class);
                    holder.nameMedicineTextView.setText(medicine.getName());
                    holder.strenghtTextView.setText(MessageFormat.format("{0} {1}", medicine.getNumberOfUnits(), medicine.getStrengthUnit()));
                    holder.reasonMedicnieTextView.setText(medicine.getReasonOfTaking());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return doseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTimeTitleTextView;
        TextView nameMedicineTextView;
        TextView isTakenTextView;
        ImageView isTakenImageView;
        TextView strenghtTextView;
        TextView reasonMedicnieTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTimeTitleTextView = itemView.findViewById(R.id.dateTimeTitleTextView);
            nameMedicineTextView = itemView.findViewById(R.id.nameMedicineTextView);
            isTakenTextView = itemView.findViewById(R.id.isTakenTextView);
            isTakenImageView = itemView.findViewById(R.id.isTakenImageView);
            strenghtTextView = itemView.findViewById(R.id.strenghtTextView);
            reasonMedicnieTextView = itemView.findViewById(R.id.reasonMedicnieTextView);

        }
    }
}
