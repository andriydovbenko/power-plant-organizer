package com.electricity.model.dto.impl;

import com.electricity.enumeration.plant.PowerPlantType;
import com.electricity.model.dto.PowerPlantAbstractDto;

public class PowerPlantCreatingDto extends PowerPlantAbstractDto {
    private PowerPlantType type;

    public PowerPlantType getType() {
        return type;
    }

    public void setType(PowerPlantType type) {
        this.type = type;
    }
}