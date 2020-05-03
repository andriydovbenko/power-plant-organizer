package com.electricity.model.storage.impl;

import com.electricity.exeption.NotEnoughStorageSpaceException;
import com.electricity.model.resource.storable.Uranium;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UraniumStorageTest {
    private final int initialAmount = 500;
    private final UraniumStorage uraniumStorage;

    UraniumStorageTest() {
        Uranium uranium = new Uranium(initialAmount);
        this.uraniumStorage = new UraniumStorage();
        this.uraniumStorage.initializeResource(uranium);
    }

    @Test
    void addResourceToContainer() {
        assertEquals(initialAmount, uraniumStorage.getResource().getAmount());

        Uranium additionalUranium = new Uranium(600);
        assertThrows(NotEnoughStorageSpaceException.class, () -> {
            uraniumStorage.addResourceToContainer(additionalUranium);
        });

        int expectedAmountAtTheEnd = 1000;
        assertEquals(expectedAmountAtTheEnd, uraniumStorage.getResource().getAmount());
    }

    @Test
    void getCapacity() {
        int expectedCapacity = 1000;
        assertEquals(expectedCapacity, uraniumStorage.getCapacity());
    }
}