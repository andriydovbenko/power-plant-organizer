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
    private final double expectedEnergyWithoutResources = 0;
    private final int expectedResourceAmountAfterProducingEnergy = 0;

    @Test
    void produceOnEnergyOnCoalPowerPlant() {
        //Given
        int resourceAmount = 100;
        Coal coal = new Coal(resourceAmount);
        CoalStorage coalStorage = new CoalStorage();
        coalStorage.initializeResource(coal);
        CoalFiredPowerPlant powerPlant = new CoalFiredPowerPlant(coalStorage);

        //When
        double producedEnergy = service.produceEnergy(powerPlant);
        double producedEnergyWithZeroResources = service.produceEnergy(powerPlant);

        //Then
        double expectedEnergy = 30.20;
        assertEquals(expectedEnergy, producedEnergy);

        assertEquals(expectedResourceAmountAfterProducingEnergy, powerPlant.getStorage().getAmountOfResource());

        assertEquals(expectedEnergyWithoutResources, producedEnergyWithZeroResources);
    }

    @Test
    void produceOnEnergyOnNuclearPowerPlant() {
        //Given
        int resourceAmount = 20;
        Uranium uranium = new Uranium(resourceAmount);
        UraniumStorage uraniumStorage = new UraniumStorage();
        uraniumStorage.initializeResource(uranium);
        NuclearPowerPlan powerPlant = new NuclearPowerPlan(uraniumStorage);

        //When
        double producedEnergy = service.produceEnergy(powerPlant);
        double producedEnergyWithZeroResources = service.produceEnergy(powerPlant);

        //Then
        double expectedEnergy = 55.2;
        assertEquals(expectedEnergy, producedEnergy);

        assertEquals(expectedResourceAmountAfterProducingEnergy, powerPlant.getStorage().getAmountOfResource());

        assertEquals(expectedEnergyWithoutResources, producedEnergyWithZeroResources);
    }

    @Test
    void produceOnEnergyHydroPowerPlant() {
        //Given
        int resourceVolume = 50_000;
        Water water = new Water(resourceVolume);
        WaterReservoir waterReservoir = new WaterReservoir();
        waterReservoir.initializeResource(water);
        HydroPowerPlant powerPlant = new HydroPowerPlant(waterReservoir);

        //When
        double producedEnergy = service.produceEnergy(powerPlant);
        double producedEnergyWithZeroResources = service.produceEnergy(powerPlant);

        //Then
        double expectedEnergy = 15.5;
        assertEquals(expectedEnergy, producedEnergy);

        assertEquals(expectedResourceAmountAfterProducingEnergy, powerPlant.getStorage().getAmountOfResource());

        assertEquals(expectedEnergyWithoutResources, producedEnergyWithZeroResources);
    }

    @Test
    void produceOnEnergyOnSolarPowerPlant() {
        //Given
        int workingTimeLeft = 1;
        SolarEnergy solarEnergy = new SolarEnergy(workingTimeLeft);
        SolarPowerPlant powerPlant = new SolarPowerPlant(solarEnergy);

        //When
        double producedEnergy = service.produceEnergy(powerPlant);
        double producedEnergyWithZeroResources = service.produceEnergy(powerPlant);

        //Then
        double expectedEnergy = 5.05;
        assertEquals(expectedEnergy, producedEnergy);

        assertEquals(expectedResourceAmountAfterProducingEnergy, powerPlant.getResource().getWorkTimeLeft());

        assertEquals(expectedEnergyWithoutResources, producedEnergyWithZeroResources);
    }

    @Test
    void produceOnEnergyOnWindPowerPlant() {
        //Given
        int workingTimeLeft = 1;
        Wind wind = new Wind(workingTimeLeft);
        WindPowerPlant powerPlant = new WindPowerPlant(wind);

        //When
        double producedEnergy = service.produceEnergy(powerPlant);
        double producedEnergyWithZeroResources = service.produceEnergy(powerPlant);

        //Then
        double expectedEnergy = 7.02;
        assertEquals(expectedEnergy, producedEnergy);

        assertEquals(expectedResourceAmountAfterProducingEnergy, powerPlant.getResource().getWorkTimeLeft());

        assertEquals(expectedEnergyWithoutResources, producedEnergyWithZeroResources);
    }
}