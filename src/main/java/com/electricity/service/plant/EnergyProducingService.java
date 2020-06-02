package com.electricity.service.plant;

import com.electricity.model.plant.PowerPlant;

@FunctionalInterface
public interface EnergyProducingService {

    double produceEnergy(PowerPlant PowerPlant);
}