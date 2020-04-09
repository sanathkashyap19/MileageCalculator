package com.sanath.mileage.model;

//Data model for mileage
public class MileageModel {

    private float mileage, fuelFilled, disctancetravelled;
    private String dateFilled;

    public MileageModel(float mileage, float fuelFilled, float disctancetravelled, String dateFilled) {
        this.mileage = mileage;
        this.fuelFilled = fuelFilled;
        this.disctancetravelled = disctancetravelled;
        this.dateFilled = dateFilled;
    }

    public float getMileage() {
        return mileage;
    }

    public void setMileage(float mileage) {
        this.mileage = mileage;
    }

    public float getFuelFilled() {
        return fuelFilled;
    }

    public void setFuelFilled(float fuelFilled) {
        this.fuelFilled = fuelFilled;
    }

    public float getDisctancetravelled() {
        return disctancetravelled;
    }

    public void setDisctancetravelled(float disctancetravelled) {
        this.disctancetravelled = disctancetravelled;
    }

    public String getDateFilled() {
        return dateFilled;
    }

    public void setDateFilled(String dateFilled) {
        this.dateFilled = dateFilled;
    }
}
