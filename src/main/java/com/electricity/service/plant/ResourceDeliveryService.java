package com.electricity.service.plant;

import com.electricity.model.plant.PowerPlant;
import com.electricity.model.transaction.ResourceTransaction;

@FunctionalInterface
public interface ResourceDeliveryService {

    void setResourceToPowerPlant(PowerPlant powerPlant, ResourceTransaction resourceTransaction);
}