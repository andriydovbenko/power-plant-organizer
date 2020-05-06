package com.electricity.enumeration;

public enum UserColumnName {
    ID("id"),
    LOGIN("login"),
    PASSWORD("password"),
    FIRST_NAME("firs_name"),
    LAST_NAME("last_name"),
    TABLE_NAME("table_name"),
    CURRENT_FUNDS_AMOUNT("current_funds_amount");

    private final String name;

    UserColumnName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}