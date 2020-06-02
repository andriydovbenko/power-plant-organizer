package com.electricity.service.operation.plant;

import com.electricity.exeption.NoSuchPowerPlantIdException;
import com.electricity.model.dto.impl.PowerPlantCreatingDto;
import com.electricity.model.dto.impl.PowerPlantUpdatingDto;
import com.electricity.model.plant.PowerPlant;
import com.electricity.model.transaction.ResourceTransaction;
import com.electricity.repository.init.PowerPlantInitializer;
import com.electricity.service.plant.PowerPlantConstructor;
import com.electricity.service.plant.ResourceDeliveryService;
import com.electricity.service.plant.impl.ResourceDeliveryServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PowerPlantOperationManager {
    private static final Logger LOGGER = LogManager.getLogger(PowerPlantOperationManager.class);
    private static final int INITIAL_DELAY = 0;
    private static final int REFRESH_TIME = 2;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private static final int POOL_SIZE = 1;

    private final PowerPlantConstructor powerPlantConstructor;
    private final ResourceDeliveryService resourceDeliveryService;
    private final ScheduledExecutorService powerPlantScheduledService;
    private final PowerPlantOperationThread powerPlantOperationThread;

    public PowerPlantOperationManager(String tableName) {
        this.powerPlantConstructor = PowerPlantInitializer.POWER_PLANT_CONSTRUCTOR;
        this.powerPlantScheduledService = Executors.newScheduledThreadPool(POOL_SIZE);
        this.resourceDeliveryService = new ResourceDeliveryServiceImpl();
        this.powerPlantOperationThread = new PowerPlantOperationThread(tableName);
        this.powerPlantScheduledService.scheduleWithFixedDelay(
                powerPlantOperationThread, INITIAL_DELAY, REFRESH_TIME, TIME_UNIT);
    }

    public void createPowerPlantAndAddToThread(PowerPlantCreatingDto creatingDto) {
        powerPlantOperationThread.addPowerPlant(
                powerPlantConstructor.construct(creatingDto)
        );
    }

    public PowerPlant getPowerPlantById(String id) throws NoSuchPowerPlantIdException {
        return powerPlantOperationThread.getOptPowerPlantById(id)
                .orElseThrow(() ->
                        new NoSuchPowerPlantIdException("Power Plant with id: \"" + id + "\" does not exist"));
    }

    public void updatePowerPlant(PowerPlantUpdatingDto updatingDto) {
        try {
            PowerPlant powerPlant = getPowerPlantById(updatingDto.getId());

            powerPlant.setCountry(updatingDto.getCountry());
            powerPlant.setNumberOfEmployees(updatingDto.getNumberOfEmployees());
            powerPlant.setWorking(updatingDto.isWorking());
        } catch (NoSuchPowerPlantIdException e) {
            LOGGER.error(e.toString());
            LOGGER.info(Arrays.toString(e.getStackTrace()));
        }
    }

    public void removePowerPlantById(String id) {
        powerPlantOperationThread.removePowerPlantById(id);
    }

    public void setResourceUsingDeliveryService(ResourceTransaction resourceTransaction) {
        try {
            PowerPlant powerPlant = getPowerPlantById(resourceTransaction.getPowerPlantId());
            resourceDeliveryService.setResourceToPowerPlant(powerPlant, resourceTransaction);
        } catch (NoSuchPowerPlantIdException e) {
            LOGGER.error(e.toString());
            LOGGER.info(Arrays.toString(e.getStackTrace()));
        }
    }

    public List<PowerPlant> getAllPowerPlants() {
        return powerPlantOperationThread.getPowerPlants();
    }

    public void stopAndSave() {
        powerPlantOperationThread.stopAndSave();

        powerPlantScheduledService.shutdown();
    }

    public void start() {
        powerPlantOperationThread.start();
    }
}