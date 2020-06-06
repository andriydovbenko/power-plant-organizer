package com.electricity.enumeration.attribute;

public enum ContextAttribute {
    AMOUNT("amount"),
    USER("user"),
    USER_REPOSITORY("userRepository"),
    USER_SESSION("userSession"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    POWER_PLANT("powerPlant"),
    POWER_PLANTS("powerPlants"),
    PLANT_ID("plantId"),
    PASSWORD("password"),
    COUNTRY("country"),
    CONFIRM_PASSWORD("confirmPassword"),
    CHOICE("choice"),
    NUMBER_OF_EMPLOYEES("numberOfEmployees"),
    START("start"),
    STOP("stop"),
    REGISTRATION_SERVICE("registrationService"),
    RESOURCE_TYPE("resourceType"),
    TYPE("type"),
    LOGIN("login"),
    LOGGED_IN("loggedIn");

    private final String attribute;

    ContextAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }
}