package com.electricity.service.plant.impl;

import com.electricity.enumeration.PowerPlantType;
import com.electricity.exeption.NotEnoughStorageSpaceException;
import com.electricity.exeption.UnknownResourceTypeException;
import com.electricity.model.plant.PowerPlant;
import com.electricity.model.plant.impl.CoalFiredPowerPlant;
import com.electricity.model.plant.impl.NuclearPowerPlan;
import com.electricity.model.transaction.ResourceTransaction;
import com.electricity.service.plant.ResourceDeliveryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class ResourceDeliveryServiceImpl implements ResourceDeliveryService {
    private static final Logger LOGGER = LogManager.getLogger(ResourceDeliveryServiceImpl.class);

    private final Map<PowerPlantType, ResourceDeliveryService> deliveryMethods;

    public ResourceDeliveryServiceImpl() {
        this.deliveryMethods = Collections.synchronizedMap(new EnumMap<>(PowerPlantType.class));
        deliveryMethods.put(PowerPlantType.COAL, this::setResourceToCoalFired);
        deliveryMethods.put(PowerPlantType.NUCLEAR, this::setResourceToNuclear);
    }

    @Override
    public void setResourceToPowerPlant(PowerPlant powerPlant, ResourceTransaction resourceTransaction) {
        deliveryMethods.getOrDefault(powerPlant.getType(), this::throwUnknownResourceTypeException)
                .setResourceToPowerPlant(powerPlant, resourceTransaction);
    }

    private void setResourceToCoalFired(PowerPlant powerPlant, ResourceTransaction resourceTransaction) {
        CoalFiredPowerPlant coalFiredPowerPlant = (CoalFiredPowerPlant) powerPlant;

        try {
            coalFiredPowerPlant.getStorage()
                    .addResourceToContainer(resourceTransaction.getStorableResource());
        } catch (NotEnoughStorageSpaceException e) {
            makeLog(e);
        }
    }

    private void setResourceToNuclear(PowerPlant powerPlant, ResourceTransaction resourceTransaction) {
        NuclearPowerPlan nuclearPowerPlan = (NuclearPowerPlan) powerPlant;

        try {
            nuclearPowerPlan.getStorage().addResourceToContainer(resourceTransaction.getStorableResource());
        } catch (NotEnoughStorageSpaceException e) {
            makeLog(e);
        }
    }

    private void makeLog(NotEnoughStorageSpaceException e) {
        LOGGER.error(e);
        LOGGER.info(e.getStackTrace());
    }

    private void throwUnknownResourceTypeException(PowerPlant powerPlant, ResourceTransaction resourceTransaction) {

        throw new UnknownResourceTypeException("Type of Power Plant \"" + powerPlant.getType() +
                "\" does not support resource transportation");
    }
}