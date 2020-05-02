package com.electricity.enumeration;

public enum InitialWorkTimeForUnstorableResource {
    SOLAR(120),
    WIND(100);

    private final int workTimeLeft;

    InitialWorkTimeForUnstorableResource(int workTimeLeft) {
        this.workTimeLeft = workTimeLeft;
    }

    public int getWorkTimeLeft() {
        return workTimeLeft;
    }
}