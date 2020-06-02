package com.electricity.model.storage.impl;

import com.electricity.exeption.NotEnoughStorageSpaceException;
import com.electricity.model.resource.storable.Uranium;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UraniumStorageTest {
    private final int initialAmount = 5000;
    private final UraniumStorage uraniumStorage;

    UraniumStorageTest() {
        Uranium uranium = new Uranium(initialAmount);
        this.uraniumStorage = new UraniumStorage();
        this.uraniumStorage.initializeResource(uranium);
    }

    @Test
    void should_add_resource_to_container() {
        assertEquals(initialAmount, uraniumStorage.getResource().getAmount());

        Uranium additionalUranium = new Uranium(6000);
        assertThrows(NotEnoughStorageSpaceException.class, () -> uraniumStorage.addResourceToContainer(additionalUranium));

        int expectedAmountAtTheEnd = 10000;
        assertEquals(expectedAmountAtTheEnd, uraniumStorage.getResource().getAmount());
    }

    @Test
    void should_get_capacity() {
        int expectedCapacity = 10000;
        assertEquals(expectedCapacity, uraniumStorage.getCapacity());
    }
}