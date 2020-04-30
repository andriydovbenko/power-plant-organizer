package com.electricity.exeption;

public class UnknownResourceTypeException extends RuntimeException {

    public UnknownResourceTypeException(String message) {
        super(message);
    }
}