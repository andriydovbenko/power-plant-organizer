package com.electricity.model.storage.impl;

import com.electricity.exeption.NotEnoughStorageSpaceException;
import com.electricity.model.resource.storable.Coal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoalStorageTest {
    private final int initialAmount = 5000;
    private final Coal coal;
    private final CoalStorage coalStorage;

    CoalStorageTest() {
        this.coal = new Coal(initialAmount);
        this.coalStorage = new CoalStorage();
        this.coalStorage.initializeResource(coal);
    }

    @Test
    void addResourceToContainer() {
        assertEquals(initialAmount, coalStorage.getResource().getAmount());

        Coal additionalCoal = new Coal(6000);
        assertThrows(NotEnoughStorageSpaceException.class, () -> {
            coalStorage.addResourceToContainer(additionalCoal);
        });

        int expectedAmountAtTheEnd = 10000;
        assertEquals(expectedAmountAtTheEnd, coalStorage.getResource().getAmount());
    }

    @Test
    void getCapacity() {
        int expectedCapacity = 10000;
        assertEquals(expectedCapacity, coalStorage.getCapacity());
    }

    @Test
    void getId() {
        assertNotEquals(null, coal.getId());

        int expectedIdLength = 36;
        assertEquals(expectedIdLength, coal.getId().length());
    }
}