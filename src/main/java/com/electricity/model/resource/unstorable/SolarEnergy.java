package com.electricity.model.resource.unstorable;

import com.electricity.model.resource.UnstorableResource;

public class SolarEnergy implements UnstorableResource {
    private int workTimeLeft;

    public SolarEnergy() {
        this.workTimeLeft = 0;
    }

    public SolarEnergy(int workTimeLeft) {
        this.workTimeLeft = workTimeLeft;
    }

    @Override
    public int getWorkTimeLeft() {
        return workTimeLeft;
    }

    @Override
    public void setWorkTimeLeft(int workTimeLeft) {
        this.workTimeLeft = workTimeLeft;
    }

    @Override
    public String toString() {
        return "SolarEnergy{" +
                ", amountOfCirclesCapableToWork=" + workTimeLeft +
                '}';
    }
}