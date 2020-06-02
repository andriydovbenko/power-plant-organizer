package com.electricity.service.session.impl;

import com.electricity.exeption.NoSuchPowerPlantIdException;
import com.electricity.model.dto.impl.PowerPlantCreatingDto;
import com.electricity.model.dto.impl.PowerPlantUpdatingDto;
import com.electricity.model.plant.PowerPlant;
import com.electricity.model.transaction.ResourceTransaction;
import com.electricity.model.user.User;
import com.electricity.repository.UserRepository;
import com.electricity.service.operation.plant.PowerPlantOperationManager;
import com.electricity.service.operation.user.UserProcessOperationManager;
import com.electricity.service.session.UserSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserSessionImpl implements UserSession {
    private static final Logger LOGGER = LogManager.getLogger(UserSessionImpl.class);
    private final PowerPlantOperationManager powerPlantOperationManager;
    private final UserProcessOperationManager userProcessOperationManager;
    private int countOfStarts = 0;

    public UserSessionImpl(UserRepository repositoryManager, User user) {
        this.powerPlantOperationManager = new PowerPlantOperationManager(user.getTableName());
        this.userProcessOperationManager = new UserProcessOperationManager(repositoryManager, user);

        userProcessOperationManager.injectPowerPlantExecutorReference(powerPlantOperationManager);
    }

    @Override
    public void start() {
        int limit = 1;
        if (countOfStarts < limit) {
            userProcessOperationManager.start();
            powerPlantOperationManager.start();
            countOfStarts++;
        }
    }

    @Override
    public void stopAndSave() {
        userProcessOperationManager.stopAndSave();
        powerPlantOperationManager.stopAndSave();
    }

    @Override
    public void updatePowerPlant(PowerPlantUpdatingDto dto) {
        powerPlantOperationManager.updatePowerPlant(dto);
    }

    @Override
    public void buyNewPowerPlant(PowerPlantCreatingDto dto) {
        userProcessOperationManager.buyPowerPlant(dto);
    }

    @Override
    public void buyResource(ResourceTransaction resourceTransaction) {
        userProcessOperationManager.buyResourceUsingTransaction(resourceTransaction);
    }

    @Override
    public void removePowerPlantById(String powerPlantId) {
        powerPlantOperationManager.removePowerPlantById(powerPlantId);
    }

    @Override
    public List<PowerPlant> getAllPowerPlants() {

        return powerPlantOperationManager.getAllPowerPlants();
    }

    @Override
    public PowerPlant getPowerPlantById(String id){
        PowerPlant powerPlant = null;

        try {
          powerPlant = powerPlantOperationManager.getPowerPlantById(id);
        } catch (NoSuchPowerPlantIdException e) {
           LOGGER.error(e);
        }

        return powerPlant;
    }

    @Override
    public User getUser(){
        return userProcessOperationManager.getUser();
    }
}