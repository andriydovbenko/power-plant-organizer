package com.electricity.enumeration.attribute;

public enum AESData {
    SECRET("superSecretKey"),
    PADDING_SCHEMA("AES/ECB/PKCS5Padding"),
    ALGORITHM_SHA("SHA-1"),
    ALGORITHM_AES("AES");

    private final String information;

    AESData(String information) {
        this.information = information;
    }

    public String getInformation() {
        return information;
    }
}