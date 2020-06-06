package com.electricity.service.plant.impl;

import com.electricity.enumeration.plant.PowerPlantType;
import com.electricity.exeption.UnknownPowerPlantTypeException;
import com.electricity.service.plant.PowerPlantPriceSetterService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import static com.electricity.enumeration.plant.PowerPlantCost.*;

public class PowerPlantPriceSetterServiceImpl implements PowerPlantPriceSetterService {
    private final Map<PowerPlantType, PowerPlantPriceSetterService> priceGettingMethods;

    public PowerPlantPriceSetterServiceImpl() {
        this.priceGettingMethods = Collections.synchronizedMap(new EnumMap<>(PowerPlantType.class));
        priceGettingMethods.put(PowerPlantType.COAL, this::getCoalFiredPowerPlantPrice);
        priceGettingMethods.put(PowerPlantType.NUCLEAR, this::getNuclearPowerPlantPrice);
        priceGettingMethods.put(PowerPlantType.HYDRO, this::getHydroPowerPlantPrice);
        priceGettingMethods.put(PowerPlantType.SOLAR, this::getSolarPowerPlantPrice);
        priceGettingMethods.put(PowerPlantType.WIND, this::getWindPowerPlantPrice);
    }

    @Override
    public BigDecimal getPowerPlantCost(PowerPlantType powerPlantType) {
        return priceGettingMethods.getOrDefault(powerPlantType, this::throwUnknownPowerPlantTypeException)
                .getPowerPlantCost(powerPlantType);
    }

    private BigDecimal getCoalFiredPowerPlantPrice(PowerPlantType powerPlantType) {
        return COAL.getCost();
    }

    private BigDecimal getNuclearPowerPlantPrice(PowerPlantType powerPlantType) {
        return NUCLEAR.getCost();
    }

    private BigDecimal getHydroPowerPlantPrice(PowerPlantType powerPlantType) {
        return HYDRO.getCost();
    }

    private BigDecimal getSolarPowerPlantPrice(PowerPlantType powerPlantType) {
        return SOLAR.getCost();
    }

    private BigDecimal getWindPowerPlantPrice(PowerPlantType powerPlantType) {
        return WIND.getCost();
    }

    private BigDecimal throwUnknownPowerPlantTypeException(PowerPlantType powerPlantType) {
        throw new UnknownPowerPlantTypeException("Power plant Type: \"" + powerPlantType +
                "\" is not being supported by the application");
    }
}