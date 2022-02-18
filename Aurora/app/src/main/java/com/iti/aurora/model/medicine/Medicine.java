package com.iti.aurora.model.medicine;

import java.util.Date;
import java.util.List;

public class Medicine {

    private Date startDate;
    private Date endDate;

    private String name;
    private String iconPath;
    private String instruction;
    private String reasonOfTaking;
    private List<Dose> doseList;
    private Boolean isActive;
    private int dosagesPerPack;
    private int dosagesLeft;

    private MedicineStrength strength;
    private MedicineForm form;

}