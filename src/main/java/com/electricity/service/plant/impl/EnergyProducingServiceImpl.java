package com.electricity.service.plant.impl;

import com.electricity.enumeration.plant.PowerPlantType;
import com.electricity.model.plant.PowerPlant;
import com.electricity.model.plant.StorageCapableAbstractPlant;
import com.electricity.model.plant.StorageIncapableAbstractPlant;
import com.electricity.service.plant.EnergyProducingService;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import static com.electricity.enumeration.plant.PowerPlantType.*;

public class EnergyProducingServiceImpl implements EnergyProducingService {
    private final Map<PowerPlantType, EnergyProducingService> mappedMethods;
    private double producedPower;

    public EnergyProducingServiceImpl() {
        this.mappedMethods = Collections.synchronizedMap(new EnumMap<>(PowerPlantType.class));
        mappedMethods.put(COAL, this::useStorageCapablePlants);
        mappedMethods.put(NUCLEAR, this::useStorageCapablePlants);
        mappedMethods.put(HYDRO, this::useStorageCapablePlants);
        mappedMethods.put(SOLAR, this::useStorageIncapablePlants);
        mappedMethods.put(WIND, this::useStorageIncapablePlants);
    }

    @Override
    public double produceEnergy(PowerPlant powerPlant) {
        eraseProducedPower();

        return mappedMethods.getOrDefault(powerPlant.getType(), this::skipPowerPlant)
                .produceEnergy(powerPlant);
    }

    private double useStorageCapablePlants(PowerPlant powerPlant) {
        StorageCapableAbstractPlant plant = (StorageCapableAbstractPlant) powerPlant;

        int totalResourceAmount = plant.getStorage().getResource().getAmount();
        int resourceConsumption = plant.getResourceConsumption();

        if (totalResourceAmount >= resourceConsumption) {
            plant.getStorage().getResource().setAmount(totalResourceAmount - resourceConsumption);
            producedPower = plant.getMaxPower();
        } else {
            powerPlant.setWorking(false);
        }

        return producedPower;
    }


    private double useStorageIncapablePlants(PowerPlant powerPlant) {
        StorageIncapableAbstractPlant plant = (StorageIncapableAbstractPlant) powerPlant;

        int workTimeLeft = plant.getResource().getWorkTimeLeft();
        int oneWorkCircle = plant.getResourceConsumption();

        if (workTimeLeft >= oneWorkCircle) {
            plant.getResource().setWorkTimeLeft(workTimeLeft - oneWorkCircle);
            producedPower = plant.getMaxPower();
        } else {
            powerPlant.setWorking(false);
        }

        return producedPower;
    }

    private double skipPowerPlant(PowerPlant powerPlant) {
        return producedPower;
    }

    private void eraseProducedPower() {
        this.producedPower = 0;
    }
}