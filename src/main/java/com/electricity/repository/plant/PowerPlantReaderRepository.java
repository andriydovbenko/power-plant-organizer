package com.electricity.repository.plant;

import com.electricity.model.plant.PowerPlant;

import java.util.List;

public interface PowerPlantReaderRepository {

    List<PowerPlant> selectAll();
}