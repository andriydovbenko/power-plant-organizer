package com.electricity.enumeration.path;

public enum Driver {
    POSTGRES("org.postgresql.Driver"),
    MYSQL("com.mysql.cj.jdbc.Driver");

    private final String path;

    Driver(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}