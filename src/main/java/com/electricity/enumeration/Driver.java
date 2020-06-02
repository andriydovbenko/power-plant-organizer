package com.electricity.enumeration;

public enum Driver {
    POSTGRES();

    private final String path;

    Driver() {
        this.path = "org.postgresql.Driver";
    }

    public String getPath() {
        return path;
    }
}