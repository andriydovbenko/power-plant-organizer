package com.electricity.model.plant;

import com.electricity.enumeration.PowerPlantType;

import java.util.UUID;

public abstract class PowerPlant {

    private String id;
    private String country;
    private int numberOfEmployees;
    private boolean isWorking;

    protected PowerPlant() {
        this.id = UUID.randomUUID().toString();
    }

    public abstract PowerPlantType getType();

    public abstract double getMaxPower();

    public abstract int getResourceConsumption();

    public abstract void setResourceConsumption(int resourceConsumption);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    @Override
    public String toString() {
        return ", id='" + id + '\'' +
                ", country='" + country + '\'' +
                ", numberOfEmployees=" + numberOfEmployees +
                ", isWorking=" + isWorking;
    }
}