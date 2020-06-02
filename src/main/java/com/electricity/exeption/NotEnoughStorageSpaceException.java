package com.electricity.exeption;

public class NotEnoughStorageSpaceException extends Exception {

    public NotEnoughStorageSpaceException(String message) {
        super(message);
    }
}