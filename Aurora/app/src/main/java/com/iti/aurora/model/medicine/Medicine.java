package com.iti.aurora.model.medicine;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.iti.aurora.database.DateConverter;
import com.iti.aurora.database.DoseListConverter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(tableName = "medicine")
public class Medicine implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "medId")
    @NonNull
    long medId;

    @ColumnInfo(name = "medName")
    private String name;

    @ColumnInfo(name = "iconPath")
    private String iconPath;

    @ColumnInfo(name = "instruction")
    private String instruction;

    @ColumnInfo(name = "reasonOfTaking")
    private String reasonOfTaking;

//    @TypeConverters(DoseListConverter.class)
//    private List<Dose> doseList;

    @ColumnInfo(name = "isActive")
    private Boolean isActive;

    @ColumnInfo(name = "dosagesPerPack")
    private int dosagesPerPack;

    @ColumnInfo(name = "dosagesLeft")
    private int dosagesLeft;

    @ColumnInfo(name = "medicineForm")
    private String medicineForm;

    /*
    public MedicineForm getMedicineForm() {
        MedicineForm medicineForm;
        switch (this.medicineForm) {
            case 0:
                medicineForm = MedicineForm.Pill;
                break;
            case 1:
                medicineForm = MedicineForm.Solution;
                break;
            case 2:
                medicineForm = MedicineForm.Injection;
                break;
            case 3:
                medicineForm = MedicineForm.Powder;
                break;
            case 4:
                medicineForm = MedicineForm.Drops;
                break;
            case 5:
                medicineForm = MedicineForm.Inhaler;
                break;
            default:
                medicineForm = MedicineForm.Other;
        }
        return medicineForm;
    }

    public void setMedicineForm(MedicineForm medicineForm) {
        if (medicineForm == MedicineForm.Pill)
            this.medicineForm = 0;
        else if (medicineForm == MedicineForm.Solution)
            this.medicineForm = 1;
        else if (medicineForm == MedicineForm.Injection)
            this.medicineForm = 2;
        else if (medicineForm == MedicineForm.Powder)
            this.medicineForm = 3;
        else if (medicineForm == MedicineForm.Drops)
            this.medicineForm = 4;
        else if (medicineForm == MedicineForm.Inhaler)
            this.medicineForm = 5;
        else if (medicineForm == MedicineForm.Other) {
            this.medicineForm = 6;
        }
    }

     */

    @ColumnInfo(name = "numberOfUnits")
    private int numberOfUnits;

    @ColumnInfo(name = "StrengthUnits")
    private int unit;

    public StrengthUnit getStrengthUnit() {
        StrengthUnit strengthUnit;
        switch (unit) {
            case 0:
                strengthUnit = StrengthUnit.g;
                break;
            case 1:
                strengthUnit = StrengthUnit.mg;
                break;
            case 2:
                strengthUnit = StrengthUnit.IU;
                break;
            case 3:
                strengthUnit = StrengthUnit.mcg;
                break;
            case 4:
                strengthUnit = StrengthUnit.mcg_ml;
                break;
            case 5:
                strengthUnit = StrengthUnit.mEq;
                break;
            case 6:
                strengthUnit = StrengthUnit.mL;
                break;
            case 7:
                strengthUnit = StrengthUnit.percentage;
                break;
            case 8:
                strengthUnit = StrengthUnit.mg_g;
                break;
            case 9:
                strengthUnit = StrengthUnit.mg_cm2;
                break;
            case 10:
                strengthUnit = StrengthUnit.mg_ml;
                break;
            case 11:
                strengthUnit = StrengthUnit.mcg_hr;
                break;
            default:
                strengthUnit = StrengthUnit.g;
        }
        return strengthUnit;
    }

    public void setStrengthUnit(StrengthUnit strengthUnit) {
        if (strengthUnit == StrengthUnit.g)
            this.unit = 0;
        else if (strengthUnit == StrengthUnit.mg)
            this.unit = 1;
        else if (strengthUnit == StrengthUnit.IU)
            this.unit = 2;
        else if (strengthUnit == StrengthUnit.mcg)
            this.unit = 3;
        else if (strengthUnit == StrengthUnit.mcg_ml)
            this.unit = 4;
        else if (strengthUnit == StrengthUnit.mEq)
            this.unit = 5;
        else if (strengthUnit == StrengthUnit.mL) {
            this.unit = 6;
        } else if (strengthUnit == StrengthUnit.percentage) {
            this.unit = 7;
        } else if (strengthUnit == StrengthUnit.mg_g) {
            this.unit = 8;
        } else if (strengthUnit == StrengthUnit.mg_cm2) {
            this.unit = 9;
        } else if (strengthUnit == StrengthUnit.mg_ml) {
            this.unit = 10;
        } else if (strengthUnit == StrengthUnit.mcg_hr) {
            this.unit = 11;
        }

    }

    public long getMedId() {
        return medId;
    }

    public void setMedId(long medId) {
        this.medId = medId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getReasonOfTaking() {
        return reasonOfTaking;
    }

    public void setReasonOfTaking(String reasonOfTaking) {
        this.reasonOfTaking = reasonOfTaking;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public int getDosagesPerPack() {
        return dosagesPerPack;
    }

    public void setDosagesPerPack(int dosagesPerPack) {
        this.dosagesPerPack = dosagesPerPack;
    }

    public int getDosagesLeft() {
        return dosagesLeft;
    }

    public void setDosagesLeft(int dosagesLeft) {
        this.dosagesLeft = dosagesLeft;
    }

    public void setMedicineForm(String medicineForm) {
        this.medicineForm = medicineForm;
    }

    public int getNumberOfUnits() {
        return numberOfUnits;
    }

    public void setNumberOfUnits(int numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public String getMedicineForm() {
        return medicineForm;
    }
}