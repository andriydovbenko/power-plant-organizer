package com.electricity.service.session;

import com.electricity.model.dto.impl.PowerPlantCreatingDto;
import com.electricity.model.dto.impl.PowerPlantUpdatingDto;
import com.electricity.model.plant.PowerPlant;
import com.electricity.model.transaction.ResourceTransaction;
import com.electricity.model.user.User;

import java.util.List;

public interface UserSession {

    void start();

    void stopAndSave();

    void updatePowerPlant(PowerPlantUpdatingDto dto);

    void buyNewPowerPlant(PowerPlantCreatingDto dto);

    void buyResource(ResourceTransaction resourceTransaction);

    void removePowerPlantById(String powerPlantId);

    List<PowerPlant> getAllPowerPlants();

    PowerPlant getPowerPlantById(String id);

    User getUser();
}