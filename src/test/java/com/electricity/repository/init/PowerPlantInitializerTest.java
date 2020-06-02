package com.electricity.repository.init;

import com.electricity.model.plant.PowerPlant;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PowerPlantInitializerTest {

    @Test
    void should_get_initial_plants() {
        //Given
        int expectedNumberOfPlants = 5;

        //When
        List<PowerPlant> powerPlants = PowerPlantInitializer.getInitialPowerPlants();

        //Then
        assertNotNull(powerPlants);
        assertEquals(expectedNumberOfPlants, powerPlants.size());
    }
}