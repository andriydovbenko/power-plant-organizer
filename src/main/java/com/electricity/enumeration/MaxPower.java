package com.electricity.enumeration;

public enum MaxPower {
    HYDRO(15.5),
    WIND(7.02),
    SOLAR(5.05),
    NUCLEAR(55.2),
    COAL(30.20);

    private final double power;

    MaxPower(double power) {
        this.power = power;
    }

    public double getPower() {
        return power;
    }
}