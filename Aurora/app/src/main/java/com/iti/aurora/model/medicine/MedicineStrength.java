package com.iti.aurora.model.medicine;

public class MedicineStrength {

    private int numberOfUnits;
    private StrengthUnit unit;

    public MedicineStrength(int numberOfUnits, StrengthUnit unit) {
        this.numberOfUnits = numberOfUnits;
        this.unit = unit;
    }

    public int getNumberOfUnits() {
        return numberOfUnits;
    }

    public void setNumberOfUnits(int numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
    }

    public StrengthUnit getUnit() {
        return unit;
    }

    public void setUnit(StrengthUnit unit) {
        this.unit = unit;
    }
}