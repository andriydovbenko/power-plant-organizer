package com.electricity.enumeration;

public enum InitialWorkTimeForUnstorablePowerPlant {
    SOLAR(1200),
    WIND(1000);

    private final int workTimeLeft;

    InitialWorkTimeForUnstorablePowerPlant(int workTimeLeft) {
        this.workTimeLeft = workTimeLeft;
    }

    public int getWorkTimeLeft() {
        return workTimeLeft;
    }
}