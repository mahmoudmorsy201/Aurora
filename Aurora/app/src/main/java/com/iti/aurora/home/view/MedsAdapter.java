package com.iti.aurora.home.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.aurora.R;
import com.iti.aurora.model.RepositoryInterface;
import com.iti.aurora.model.medicine.Dose;
import com.iti.aurora.model.medicine.Medicine;

import org.joda.time.DateTime;

import java.util.List;

public class MedsAdapter extends RecyclerView.Adapter<MedsAdapter.ViewHolder> {

    List<Dose> doseList;
    Context context;
    RepositoryInterface repositoryInterface;

    public void setDoseList(List<Dose> doseList) {
        this.doseList = doseList;
    }

    public MedsAdapter(List<Dose> medList, Context context, RepositoryInterface repositoryInterface) {
        this.doseList = medList;
        this.context = context;
        this.repositoryInterface = repositoryInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_med_main, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        Medicine medicine = repositoryInterface.getSpecificMedicine(doseList.get(position).getMedId());
//        holder.medicationNameTextView.setText(medicine.getName());
//        holder.medicationDosageTextView.setText(medicine.getNumberOfUnits() + " of " + medicine.getUnit());
//        holder.medicationTypeTextView.setText(medicine.getMedicineForm());
        holder.medicationTimeTextView.setText(new DateTime(doseList.get(position).getTimeToTake()).toString());
    }

    @Override
    public int getItemCount() {
        return doseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView notificationIconImageView;
        TextView medicationTimeTextView;
        TextView medicationNameTextView;
        TextView medicationDosageTextView;
        TextView medicationTypeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationIconImageView = itemView.findViewById(R.id.notificationIconImageView);
            medicationTimeTextView = itemView.findViewById(R.id.medicationTimeTextView);
            medicationNameTextView = itemView.findViewById(R.id.medicationNameTextView);
            medicationDosageTextView = itemView.findViewById(R.id.medicationDosageTextView);
            //medicationTypeTextView = itemView.findViewById(R.id.medicationTypeImageView);
        }
    }
}