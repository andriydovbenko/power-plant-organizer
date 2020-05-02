package com.electricity.service.plant.impl;

import com.electricity.enumeration.InitialWorkTimeForUnstorablePowerPlant;
import com.electricity.enumeration.PowerPlantType;
import com.electricity.exeption.UnknownPowerPlantTypeException;
import com.electricity.model.dto.impl.PowerPlantCreatingDto;
import com.electricity.model.plant.PowerPlant;
import com.electricity.model.plant.impl.*;
import com.electricity.model.resource.storable.*;
import com.electricity.model.resource.unstorable.SolarEnergy;
import com.electricity.model.resource.unstorable.Wind;
import com.electricity.model.storage.impl.CoalStorage;
import com.electricity.model.storage.impl.UraniumStorage;
import com.electricity.model.storage.impl.WaterReservoir;
import com.electricity.service.plant.PowerPlantConstructor;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class PowerPlantConstructorImpl implements PowerPlantConstructor {
    private final Map<PowerPlantType, PowerPlantConstructor> constructionMethods;

    public PowerPlantConstructorImpl() {
        this.constructionMethods = Collections.synchronizedMap(new EnumMap<>(PowerPlantType.class));
        constructionMethods.put(PowerPlantType.COAL, this::constructNewCoalFired);
        constructionMethods.put(PowerPlantType.NUCLEAR, this::constructNewNuclear);
        constructionMethods.put(PowerPlantType.HYDRO, this::constructNewHydro);
        constructionMethods.put(PowerPlantType.SOLAR, this::constructNewSolar);
        constructionMethods.put(PowerPlantType.WIND, this::constructNewWind);
    }

    @Override
    public PowerPlant construct(PowerPlantCreatingDto powerPlantCreatingDto) {

        return constructionMethods.getOrDefault(powerPlantCreatingDto.getType(), this::throwUnknownPowerPlantException)
                .construct(powerPlantCreatingDto);
    }

    private PowerPlant constructNewCoalFired(PowerPlantCreatingDto powerPlantCreatingDto) {
        CoalStorage storage = new CoalStorage();

        storage.initializeResource(new Coal(storage.getCapacity()));
        CoalFiredPowerPlant powerPlant = new CoalFiredPowerPlant(storage);

        powerPlant.setWorking(powerPlantCreatingDto.isWorking());
        powerPlant.setCountry(powerPlantCreatingDto.getCountry());
        powerPlant.setNumberOfEmployees(powerPlantCreatingDto.getNumberOfEmployees());

        return powerPlant;
    }

    private PowerPlant constructNewNuclear(PowerPlantCreatingDto powerPlantCreatingDto) {
        UraniumStorage storage = new UraniumStorage();

        storage.initializeResource(new Uranium(storage.getCapacity()));
        NuclearPowerPlan powerPlant = new NuclearPowerPlan(storage);

        powerPlant.setWorking(powerPlantCreatingDto.isWorking());
        powerPlant.setCountry(powerPlantCreatingDto.getCountry());
        powerPlant.setNumberOfEmployees(powerPlantCreatingDto.getNumberOfEmployees());

        return powerPlant;
    }

    private PowerPlant constructNewHydro(PowerPlantCreatingDto powerPlantCreatingDto) {
        WaterReservoir reservoir = new WaterReservoir();

        reservoir.initializeResource(new Water(reservoir.getCapacity()));
        HydroPowerPlant powerPlant = new HydroPowerPlant(reservoir);

        powerPlant.setWorking(powerPlantCreatingDto.isWorking());
        powerPlant.setCountry(powerPlantCreatingDto.getCountry());
        powerPlant.setNumberOfEmployees(powerPlantCreatingDto.getNumberOfEmployees());

        return powerPlant;
    }

    private PowerPlant constructNewSolar(PowerPlantCreatingDto powerPlantCreatingDto) {
        int workTimeLeft = InitialWorkTimeForUnstorablePowerPlant.SOLAR.getWorkTimeLeft();

        SolarPowerPlant powerPlant = new SolarPowerPlant(new SolarEnergy(workTimeLeft));

        powerPlant.setWorking(powerPlantCreatingDto.isWorking());
        powerPlant.setCountry(powerPlantCreatingDto.getCountry());
        powerPlant.setNumberOfEmployees(powerPlantCreatingDto.getNumberOfEmployees());

        return powerPlant;
    }

    private PowerPlant constructNewWind(PowerPlantCreatingDto powerPlantCreatingDto) {
        int workTimeLeft = InitialWorkTimeForUnstorablePowerPlant.WIND.getWorkTimeLeft();

        WindPowerPlant powerPlant = new WindPowerPlant(new Wind(workTimeLeft));

        powerPlant.setWorking(powerPlantCreatingDto.isWorking());
        powerPlant.setCountry(powerPlantCreatingDto.getCountry());
        powerPlant.setNumberOfEmployees(powerPlantCreatingDto.getNumberOfEmployees());

        return powerPlant;
    }

    private PowerPlant throwUnknownPowerPlantException(PowerPlantCreatingDto powerPlantCreatingDto) {

        throw new UnknownPowerPlantTypeException("Power plant Type: \"" + powerPlantCreatingDto.getType() +
                "\" is not being supported by the application");
    }
}