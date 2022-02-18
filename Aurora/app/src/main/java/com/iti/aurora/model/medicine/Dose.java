package com.iti.aurora.model.medicine;

import java.util.Date;

public class Dose {

    private Date timeToTake;
    private Boolean isTaken;
    private Date timeTaken;



    public Dose() {
    }

    public Dose(Date timeToTake, Boolean isTaken, Date timeTaken) {
        this.timeToTake = timeToTake;
        this.isTaken = isTaken;
        this.timeTaken = timeTaken;
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
}