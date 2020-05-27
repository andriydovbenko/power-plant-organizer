package com.electricity.model.storage.impl;

import com.electricity.exeption.NotEnoughStorageSpaceException;
import com.electricity.model.resource.storable.Coal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoalStorageTest {
    private final int initialAmount = 50000;
    private final CoalStorage coalStorage;

    CoalStorageTest() {
        Coal coal = new Coal(initialAmount);
        this.coalStorage = new CoalStorage();
        this.coalStorage.initializeResource(coal);
    }

    @Test
    void should_add_resource_to_container() {
        assertEquals(initialAmount, coalStorage.getResource().getAmount());

        Coal additionalCoal = new Coal(60000);
        assertThrows(NotEnoughStorageSpaceException.class, () -> coalStorage.addResourceToContainer(additionalCoal));

        int expectedAmountAtTheEnd = 100000;
        assertEquals(expectedAmountAtTheEnd, coalStorage.getResource().getAmount());
    }

    @Test
    void should_get_capacity() {
        int expectedCapacity = 100000;
        assertEquals(expectedCapacity, coalStorage.getCapacity());
    }
}