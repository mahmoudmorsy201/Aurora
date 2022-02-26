package com.iti.aurora.model.medicine;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.iti.aurora.database.DateConverter;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "dose",
        foreignKeys = {
                @ForeignKey(entity = Medicine.class,
                        parentColumns = "medId",
                        childColumns = "medId",
                        onDelete = ForeignKey.CASCADE
                ),

                @ForeignKey(entity = Treatment.class,
                        parentColumns = {"treatmentId"},
                        childColumns = {"treatmentId"},
                        onDelete = ForeignKey.CASCADE)

        })
public class Dose implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "doseId")
    @NonNull
    private int doseId;

    @ColumnInfo(name = "medId")
    @NonNull
    private long medId;

    @ColumnInfo(name = "treatmentId")
    @NonNull
    private long treatmentId;

    @TypeConverters({DateConverter.class})
    private Date timeToTake;

    @ColumnInfo(name = "isTaken")
    private Boolean isTaken;

    @TypeConverters({DateConverter.class})
    private Date timeTaken;

    public Dose() {
    }

    public Dose(long medId, long treatmentId, Date timeToTake) {
        this.medId = medId;
        this.treatmentId = treatmentId;
        this.timeToTake = timeToTake;
    }

    public Dose(Date timeToTake, Boolean isTaken, Date timeTaken, long medId, long treatmentId) {
        this.timeToTake = timeToTake;
        this.isTaken = isTaken;
        this.timeTaken = timeTaken;
        this.medId = medId;
        this.treatmentId = treatmentId;
    }


    public long getMedId() {
        return medId;
    }

    public void setMedId(long medId) {
        this.medId = medId;
    }

    public long getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(long treatmentId) {
        this.treatmentId = treatmentId;
    }

    public int getDoseId() {
        return doseId;
    }

    public void setDoseId(int doseId) {
        this.doseId = doseId;
    }

    public Date getTimeToTake() {
        return timeToTake;
    }

    public void setTimeToTake(Date timeToTake) {
        this.timeToTake = timeToTake;
    }

    public Boolean getTaken() {
        return isTaken;
    }

    public void setTaken(Boolean taken) {
        isTaken = taken;
    }

    public Date getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Date timeTaken) {
        this.timeTaken = timeTaken;
    }

    @Override
    public String toString() {
        return "Dose{" +
                "doseId=" + doseId +
                ", medId=" + medId +
                ", treatmentId=" + treatmentId +
                ", timeToTake=" + timeToTake +
                ", isTaken=" + isTaken +
                ", timeTaken=" + timeTaken +
                '}';
    }
}