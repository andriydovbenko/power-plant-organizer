package com.electricity.service.plant.impl;

import com.electricity.enumeration.MaxPower;
import com.electricity.enumeration.ResourceConsumption;
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
        int resourceAmountForOneCircle = ResourceConsumption.COAL.getConsumption();
        Coal coal = new Coal(resourceAmountForOneCircle);
        CoalStorage coalStorage = new CoalStorage();
        coalStorage.initializeResource(coal);
        CoalFiredPowerPlant powerPlant = new CoalFiredPowerPlant(coalStorage);

        //When
        double producedEnergy = service.produceEnergy(powerPlant);
        double producedEnergyWithZeroResources = service.produceEnergy(powerPlant);

        //Then
        double expectedEnergy = MaxPower.COAL.getPower();
        assertEquals(expectedEnergy, producedEnergy);

        assertEquals(expectedResourceAmountAfterProducingEnergy, powerPlant.getStorage().getAmountOfResource());

        assertEquals(expectedEnergyWithoutResources, producedEnergyWithZeroResources);
    }

    @Test
    void produceOnEnergyOnNuclearPowerPlant() {
        //Given
        int resourceAmountForOneCircle = ResourceConsumption.NUCLEAR.getConsumption();
        Uranium uranium = new Uranium(resourceAmountForOneCircle);
        UraniumStorage uraniumStorage = new UraniumStorage();
        uraniumStorage.initializeResource(uranium);
        NuclearPowerPlan powerPlant = new NuclearPowerPlan(uraniumStorage);

        //When
        double producedEnergy = service.produceEnergy(powerPlant);
        double producedEnergyWithZeroResources = service.produceEnergy(powerPlant);

        //Then
        double expectedEnergy = MaxPower.NUCLEAR.getPower();
        assertEquals(expectedEnergy, producedEnergy);

        assertEquals(expectedResourceAmountAfterProducingEnergy, powerPlant.getStorage().getAmountOfResource());

        assertEquals(expectedEnergyWithoutResources, producedEnergyWithZeroResources);
    }

    @Test
    void produceOnEnergyHydroPowerPlant() {
        //Given
        int resourceAmountForOneCircle = ResourceConsumption.HYDRO.getConsumption();
        Water water = new Water(resourceAmountForOneCircle);
        WaterReservoir waterReservoir = new WaterReservoir();
        waterReservoir.initializeResource(water);
        HydroPowerPlant powerPlant = new HydroPowerPlant(waterReservoir);

        //When
        double producedEnergy = service.produceEnergy(powerPlant);
        double producedEnergyWithZeroResources = service.produceEnergy(powerPlant);

        //Then
        double expectedEnergy = MaxPower.HYDRO.getPower();
        assertEquals(expectedEnergy, producedEnergy);

        assertEquals(expectedResourceAmountAfterProducingEnergy, powerPlant.getStorage().getAmountOfResource());

        assertEquals(expectedEnergyWithoutResources, producedEnergyWithZeroResources);
    }

    @Test
    void produceOnEnergyOnSolarPowerPlant() {
        //Given
        int resourceAmountForOneCircle = ResourceConsumption.SOLAR.getConsumption();
        SolarEnergy solarEnergy = new SolarEnergy(resourceAmountForOneCircle);
        SolarPowerPlant powerPlant = new SolarPowerPlant(solarEnergy);

        //When
        double producedEnergy = service.produceEnergy(powerPlant);
        double producedEnergyWithZeroResources = service.produceEnergy(powerPlant);

        //Then
        double expectedEnergy = MaxPower.SOLAR.getPower();
        assertEquals(expectedEnergy, producedEnergy);

        assertEquals(expectedResourceAmountAfterProducingEnergy, powerPlant.getResource().getWorkTimeLeft());

        assertEquals(expectedEnergyWithoutResources, producedEnergyWithZeroResources);
    }

    @Test
    void produceOnEnergyOnWindPowerPlant() {
        //Given
        int resourceAmountForOneCircle = ResourceConsumption.WIND.getConsumption();
        Wind wind = new Wind(resourceAmountForOneCircle);
        WindPowerPlant powerPlant = new WindPowerPlant(wind);

        //When
        double producedEnergy = service.produceEnergy(powerPlant);
        double producedEnergyWithZeroResources = service.produceEnergy(powerPlant);

        //Then
        double expectedEnergy = MaxPower.WIND.getPower();
        assertEquals(expectedEnergy, producedEnergy);

        assertEquals(expectedResourceAmountAfterProducingEnergy, powerPlant.getResource().getWorkTimeLeft());

        assertEquals(expectedEnergyWithoutResources, producedEnergyWithZeroResources);
    }
}