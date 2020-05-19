package com.electricity.model.storage.impl;

import com.electricity.model.storage.Storage;

public class UraniumStorage extends Storage {
    public static final int CAPACITY = 10_000;

    public UraniumStorage() {
        super(CAPACITY);
    }
}