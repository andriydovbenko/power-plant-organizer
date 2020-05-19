package com.electricity.service.process.plant;

import com.electricity.exeption.NoSuchPowerPlantIdException;
import com.electricity.model.dto.impl.PowerPlantCreatingDto;
import com.electricity.model.dto.impl.PowerPlantUpdatingDto;
import com.electricity.model.plant.PowerPlant;
import com.electricity.model.transaction.ResourceTransaction;
import com.electricity.repository.PowerPlantRepository;
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

public class PowerPlantProcessManager {
    private static final Logger LOGGER = LogManager.getLogger(PowerPlantProcessManager.class);

    private static final int INITIAL_DELAY = 0;
    private static final int REFRESH_TIME = 2;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private static final int POOL_SIZE = 1;

    private final PowerPlantConstructor powerPlantConstructor;
    private final ResourceDeliveryService resourceDeliveryService;
    private final ScheduledExecutorService powerPlantScheduledService;
    private final PowerPlantProcessThread powerPlantProcessThread;

    public PowerPlantProcessManager(String tableName) {
        this.powerPlantConstructor = PowerPlantInitializer.POWER_PLANT_CONSTRUCTOR;
        this.powerPlantScheduledService = Executors.newScheduledThreadPool(POOL_SIZE);
        this.resourceDeliveryService = new ResourceDeliveryServiceImpl();
        this.powerPlantProcessThread = new PowerPlantProcessThread(tableName);
        this.powerPlantScheduledService.scheduleWithFixedDelay(
                powerPlantProcessThread, INITIAL_DELAY, REFRESH_TIME, TIME_UNIT);
    }

    public void createPowerPlantAndAddToThread(PowerPlantCreatingDto creatingDto) {
        powerPlantProcessThread.addPowerPlant(
                powerPlantConstructor.construct(creatingDto)
        );
    }

    public PowerPlant getPowerPlantById(String id) throws NoSuchPowerPlantIdException {
        return powerPlantProcessThread.getOptPowerPlantById(id)
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
        powerPlantProcessThread.removePowerPlantById(id);
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
        return powerPlantProcessThread.getPowerPlants();
    }

    public void stopAndSave() {
        powerPlantProcessThread.stopAndSave();
    }

    public void start() {
        powerPlantProcessThread.start();
    }

    public PowerPlantRepository getRepositoryManager() {
        return powerPlantProcessThread.getRepositoryManager();
    }

    public void shutdownScheduledService(){
        powerPlantScheduledService.shutdown();
    }
}