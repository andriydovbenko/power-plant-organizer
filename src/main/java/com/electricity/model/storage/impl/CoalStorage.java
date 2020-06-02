package com.electricity.model.storage.impl;

import com.electricity.model.storage.Storage;

public class CoalStorage extends Storage {
    public static final int CAPACITY = 100_000;

    public CoalStorage() {
        super(CAPACITY);
    }
}