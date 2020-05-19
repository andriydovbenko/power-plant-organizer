package com.electricity.service.operation.plant;

import com.electricity.model.plant.PowerPlant;
import com.electricity.repository.PowerPlantRepository;
import com.electricity.service.plant.impl.EnergyProducingServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

public class PowerPlantOperationThread implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(PowerPlantOperationThread.class);
    private static volatile double totalProducedEnergy;
    private final EnergyProducingServiceImpl producingService;
    private final PowerPlantRepository repositoryManager;
    private List<PowerPlant> powerPlants;
    private boolean isRunning;

    public PowerPlantOperationThread(String tableName) {
        this.repositoryManager = new PowerPlantRepository(tableName);
        this.producingService = new EnergyProducingServiceImpl();
        downloadPowerPlantsFromTable();
    }

    @Override
    public void run() {
        if (isRunning) {
            double producedEnergy = powerPlants.stream()
                    .filter(Objects::nonNull)
                    .filter(PowerPlant::isWorking)
                    .map(producingService::produceEnergy)
                    .reduce(0.d, Double::sum);

            synchronized (PowerPlantOperationThread.class) {
                totalProducedEnergy += producedEnergy;
                LOGGER.debug("The Power Plants thread is running");
            }
        } else {
            LOGGER.debug("The Power Plants thread isn't running");
        }
    }

    private void downloadPowerPlantsFromTable() {
        this.powerPlants = repositoryManager.getAllPowerPlants();
    }

    public void addPowerPlant(PowerPlant powerPlant) {
        powerPlants.add(powerPlant);
    }

    public void removePowerPlantById(String powerPlantId) {
        powerPlants.removeIf(powerPlant -> powerPlant.getId().equals(powerPlantId));
    }

    public static BigDecimal sellProducedEnergy(BigDecimal pricePerMegawatt) {
        double producedEnergy = totalProducedEnergy;
        totalProducedEnergy = 0;

        return BigDecimal.valueOf(producedEnergy).multiply(pricePerMegawatt);
    }

    public void stopAndSave() {
        this.isRunning = false;
        save();
    }

    private void save() {
        repositoryManager.insertAllPlantsInNewTable(powerPlants);
    }

    public void start() {
        this.isRunning = true;
    }

    public List<PowerPlant> getPowerPlants() {
        return powerPlants;
    }

    public Optional<PowerPlant> getOptPowerPlantById(String id) {
        return powerPlants.stream()
                .filter(powerPlant -> id.equals(powerPlant.getId())).findAny();
    }
}