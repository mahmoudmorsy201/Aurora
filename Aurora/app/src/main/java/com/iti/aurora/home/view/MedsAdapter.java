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
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

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

        //Medicine medicine = repositoryInterface.getSpecificMedicine(doseList.get(position).getMedId());
        repositoryInterface.getSpecificMedicine(doseList.get(position).getMedId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<Medicine>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Medicine medicine) {
                        holder.medicationNameTextView.setText(medicine.getName());
                        holder.medicationDosageTextView.setText(medicine.getNumberOfUnits() + " " + medicine.getMedicineForm() + " of " + medicine.getStrengthUnit());

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

//        holder.medicationTypeTextView.setText(medicine.getMedicineForm());

        DateTime date = new DateTime(doseList.get(position).getTimeToTake());
        DateTimeFormatter fmt = DateTimeFormat.forPattern("d MMMM");
        DateTimeFormatter fmt2 = DateTimeFormat.forPattern("hh:mm");
        String dateString = date.toString(fmt);
        String timeString = date.toString(fmt2);
        //holder.medicationNameTextView.setText(String.valueOf(doseList.get(position).getMedId()));
        holder.medicationTimeTextView.setText(timeString + " " + dateString);
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