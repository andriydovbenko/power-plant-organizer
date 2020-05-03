package com.electricity.model.storage.impl;

import com.electricity.exeption.NotEnoughStorageSpaceException;
import com.electricity.model.resource.storable.Water;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WaterReservoirTest {
    private final int initialAmount = 6_000_000;
    private final WaterReservoir waterReservoir;

    WaterReservoirTest() {
        Water water = new Water(initialAmount);
        this.waterReservoir = new WaterReservoir();
        this.waterReservoir.initializeResource(water);
    }

    @Test
    void addResourceToContainer() {
        assertEquals(initialAmount, waterReservoir.getResource().getAmount());

        Water additionalWater = new Water(5_000_000);
        assertThrows(NotEnoughStorageSpaceException.class, () -> {
            waterReservoir.addResourceToContainer(additionalWater);
        });

        int expectedAmountAtTheEnd = 10_000_000;
        assertEquals(expectedAmountAtTheEnd, waterReservoir.getResource().getAmount());
    }

    @Test
    void getCapacity() {
        int expectedCapacity = 10_000_000;
        assertEquals(expectedCapacity, waterReservoir.getCapacity());
    }
}