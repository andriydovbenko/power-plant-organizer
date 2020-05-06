package com.electricity.enumeration;

public enum PowerPlantColumnName {
    ID("id"),
    TYPE("type"),
    COUNTRY("country"),
    NUMBER_OF_EMPLOYEE("number_of_employee"),
    IS_WORKING("is_working"),
    MAX_POWER("max_power"),
    RESOURCE_CONSUMPTION("resource_consumption"),
    RESOURCE_AMOUNT("resource_amount");

    private final String name;

    PowerPlantColumnName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}