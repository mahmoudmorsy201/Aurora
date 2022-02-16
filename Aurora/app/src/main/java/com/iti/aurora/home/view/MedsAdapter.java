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

import java.util.List;

public class MedsAdapter extends RecyclerView.Adapter<MedsAdapter.ViewHolder> {

    List<String> medList;
    Context context;

    public MedsAdapter(List<String> medList, Context context) {
        this.medList = medList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_med_main, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //TODO bind data from list to the holder
    }

    @Override
    public int getItemCount() {
        return medList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView notificationIconImageView;
        TextView medicationTimeTextView;
        TextView medicationNameTextView;
        TextView medicationDosageTextView;
        TextView medicationTypeImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationIconImageView = itemView.findViewById(R.id.notificationIconImageView);
            medicationTimeTextView = itemView.findViewById(R.id.medicationTimeTextView);
            medicationNameTextView = itemView.findViewById(R.id.medicationNameTextView);
            medicationDosageTextView = itemView.findViewById(R.id.medicationDosageTextView);
            medicationTypeImageView = itemView.findViewById(R.id.medicationTypeImageView);
        }
    }
}