package com.electricity.service.plant;

import com.electricity.enumeration.PowerPlantType;

import java.math.BigDecimal;

@FunctionalInterface
public interface PowerPlantPriceSettingService {

    BigDecimal getPowerPlantCost(PowerPlantType powerPlantType);
}