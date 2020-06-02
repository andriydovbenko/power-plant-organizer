package com.electricity.enumeration;

public enum TableName {
    POWER_PLANT("power_plant"),
    USER("usr");

    private final String name;

    TableName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}