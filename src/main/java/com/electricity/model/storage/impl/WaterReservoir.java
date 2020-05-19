package com.electricity.model.storage.impl;

import com.electricity.model.storage.Storage;

public class WaterReservoir extends Storage {
    public static final int CAPACITY = 100_000_000;

    public WaterReservoir() {
        super(CAPACITY);
    }
}