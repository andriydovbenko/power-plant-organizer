package com.electricity.model.dto.impl;

import com.electricity.model.dto.PowerPlantAbstractDto;

public class PowerPlantUpdatingDto extends PowerPlantAbstractDto {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}