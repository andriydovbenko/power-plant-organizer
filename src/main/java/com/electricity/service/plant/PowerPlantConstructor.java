package com.electricity.service.plant;

import com.electricity.model.dto.impl.PowerPlantCreatingDto;
import com.electricity.model.plant.PowerPlant;

@FunctionalInterface
public interface PowerPlantConstructor {

    PowerPlant construct(PowerPlantCreatingDto powerPlantCreatingDto);
}