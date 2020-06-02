package com.electricity.enumeration;

public enum ResourceConsumption {
    HYDRO(50_000),
    WIND(1),
    SOLAR(1),
    NUCLEAR(20),
    COAL(100);

    private final int consumption;

    ResourceConsumption(int consumption) {
        this.consumption = consumption;
    }

    public int getConsumption() {
        return consumption;
    }
}