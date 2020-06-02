package com.electricity.exeption;

public class UnknownPowerPlantTypeException extends RuntimeException {

    public UnknownPowerPlantTypeException(String message) {
        super(message);
    }
}