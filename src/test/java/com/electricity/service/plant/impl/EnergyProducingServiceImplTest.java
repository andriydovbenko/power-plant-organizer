package com.electricity.service.plant.impl;

import com.electricity.model.plant.impl.*;
import com.electricity.model.resource.storable.Coal;
import com.electricity.model.resource.storable.Uranium;
import com.electricity.model.resource.storable.Water;
import com.electricity.model.resource.unstorable.SolarEnergy;
import com.electricity.model.resource.unstorable.Wind;
import com.electricity.model.storage.impl.CoalStorage;
import com.electricity.model.storage.impl.UraniumStorage;
import com.electricity.model.storage.impl.WaterReservoir;
import com.electricity.service.plant.EnergyProducingService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnergyProducingServiceImplTest {
    private final EnergyProducingService service = new EnergyProducingServiceImpl();

    @Test
    void produceOnEnergyOnCoalPowerPlant() {
        //Given
        int resourceAmount = 200;
        Coal coal = new Coal(resourceAmount);
        CoalStorage coalStorage = new CoalStorage();
        coalStorage.initializeResource(coal);
        CoalFiredPowerPlant powerPlant = new CoalFiredPowerPlant(coalStorage);
        int resourceConsumption = 100;

        //When
        double producedEnergy = service.produceEnergy(powerPlant);

        //Then
        double expectedEnergy = 30.20;
        assertEquals(expectedEnergy, producedEnergy);

        int expectedResourceAmount = resourceAmount - resourceConsumption;
        assertEquals(expectedResourceAmount, powerPlant.getStorage().getAmountOfResource());
    }

    @Test
    void produceOnEnergyOnNuclearPowerPlant() {
        //Given
        int resourceAmount = 100;
        Uranium uranium = new Uranium(resourceAmount);
        UraniumStorage uraniumStorage = new UraniumStorage();
        uraniumStorage.initializeResource(uranium);
        NuclearPowerPlan powerPlant = new NuclearPowerPlan(uraniumStorage);
        int resourceConsumption = 20;

        //When
        double producedEnergy = service.produceEnergy(powerPlant);

        //Then
        double expectedEnergy = 55.2;
        assertEquals(expectedEnergy, producedEnergy);

        int expectedResourceAmount = resourceAmount - resourceConsumption;
        assertEquals(expectedResourceAmount, powerPlant.getStorage().getAmountOfResource());
    }

    @Test
    void produceOnEnergyHydroPowerPlant() {
        //Given
        int resourceVolume = 1000_000;
        Water water = new Water(resourceVolume);
        WaterReservoir waterReservoir = new WaterReservoir();
        waterReservoir.initializeResource(water);
        HydroPowerPlant powerPlant = new HydroPowerPlant(waterReservoir);
        int resourceConsumption = 50_000;

        //When
        double producedEnergy = service.produceEnergy(powerPlant);

        //Then
        double expectedEnergy = 15.5;
        assertEquals(expectedEnergy, producedEnergy);

        int expectedResourceAmount = resourceVolume - resourceConsumption;
        assertEquals(expectedResourceAmount, powerPlant.getStorage().getAmountOfResource());
    }

    @Test
    void produceOnEnergyOnSolarPowerPlant() {
        //Given
        int workingTimeLeft = 5;
        SolarEnergy solarEnergy = new SolarEnergy(workingTimeLeft);
        SolarPowerPlant powerPlant = new SolarPowerPlant(solarEnergy);
        int oneWorkingCircle = 1;

        //When
        double producedEnergy = service.produceEnergy(powerPlant);

        //Then
        double expectedEnergy = 5.05;
        assertEquals(expectedEnergy, producedEnergy);

        int expectedWorkingTimeLeft = workingTimeLeft - oneWorkingCircle;
        assertEquals(expectedWorkingTimeLeft, powerPlant.getResource().getWorkTimeLeft());
    }

    @Test
    void produceOnEnergyOnWindPowerPlant() {
        //Given
        int workingTimeLeft = 5;
        Wind wind = new Wind(workingTimeLeft);
        WindPowerPlant powerPlant = new WindPowerPlant(wind);
        int oneWorkingCircle = 1;

        //When
        double producedEnergy = service.produceEnergy(powerPlant);

        //Then
        double expectedEnergy = 7.02;
        assertEquals(expectedEnergy, producedEnergy);

        int expectedWorkingTimeLeft = workingTimeLeft - oneWorkingCircle;
        assertEquals(expectedWorkingTimeLeft, powerPlant.getResource().getWorkTimeLeft());
    }
}