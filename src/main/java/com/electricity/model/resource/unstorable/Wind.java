package com.electricity.model.resource.unstorable;

import com.electricity.model.resource.UnstorableResource;

public class Wind implements UnstorableResource {
    private int workTimeLeft;

    public Wind() {
        this.workTimeLeft = 0;
    }

    public Wind(int workTimeLeft) {
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
        return "Wind{" +
                ", circlesCapableToWork=" + workTimeLeft +
                '}';
    }
}