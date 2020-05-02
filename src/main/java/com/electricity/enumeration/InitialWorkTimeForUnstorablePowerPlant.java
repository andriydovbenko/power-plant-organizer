package com.electricity.enumeration;

public enum InitialWorkTimeForUnstorablePowerPlant {
    SOLAR(120),
    WIND(100);

    private final int workTimeLeft;

    InitialWorkTimeForUnstorablePowerPlant(int workTimeLeft) {
        this.workTimeLeft = workTimeLeft;
    }

    public int getWorkTimeLeft() {
        return workTimeLeft;
    }
}