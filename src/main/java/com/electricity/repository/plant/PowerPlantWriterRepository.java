package com.electricity.repository.plant;

import com.electricity.model.plant.PowerPlant;

public interface PowerPlantWriterRepository {

    void insertToDb(PowerPlant powerPlant);
}