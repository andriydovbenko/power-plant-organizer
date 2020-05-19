package com.electricity.service.process.plant;

import com.electricity.model.plant.PowerPlant;
import com.electricity.repository.PowerPlantRepository;
import com.electricity.service.plant.impl.EnergyProducingServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

public class PowerPlantProcessThread implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(PowerPlantProcessThread.class);
    private static volatile double totalProducedEnergy;
    private final EnergyProducingServiceImpl producingService;
    private final PowerPlantRepository repositoryManager;
    private List<PowerPlant> powerPlants;
    private boolean isRunning;

    public PowerPlantProcessThread(String tableName) {
        this.repositoryManager = new PowerPlantRepository(tableName);
        this.producingService = new EnergyProducingServiceImpl();
    }

    @Override
    public void run() {
        if (isRunning) {
            double producedEnergy = powerPlants.stream()
                    .filter(Objects::nonNull)
                    .filter(PowerPlant::isWorking)
                    .map(producingService::produceEnergy)
                    .reduce(0.d, Double::sum);

            synchronized (PowerPlantProcessThread.class) {
                totalProducedEnergy += producedEnergy;
            }
        } else {
            LOGGER.debug(" service isn't running");
        }
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
        LOGGER.debug(" Saving Power Plant into DB");
        repositoryManager.insertAllPowerPlants(powerPlants);
    }

    public void start() {
        powerPlants = repositoryManager.getAllPowerPlants();
        this.isRunning = true;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public List<PowerPlant> getPowerPlants() {
        return powerPlants;
    }

    public void setPowerPlants(List<PowerPlant> powerPlants) {
        this.powerPlants = powerPlants;
    }

    public Optional<PowerPlant> getOptPowerPlantById(String id) {
        return powerPlants.stream()
                .filter(powerPlant -> id.equals(powerPlant.getId())).findAny();
    }

    public PowerPlantRepository getRepositoryManager() {
        return repositoryManager;
    }
}