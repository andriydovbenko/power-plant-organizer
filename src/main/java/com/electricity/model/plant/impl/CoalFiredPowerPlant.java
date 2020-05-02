package com.electricity.model.plant.impl;

import com.electricity.enumeration.MaxPower;
import com.electricity.enumeration.PowerPlantType;
import com.electricity.enumeration.ResourceConsumption;
import com.electricity.model.plant.StorageCapableAbstractPlant;
import com.electricity.model.storage.Storage;

import java.util.Objects;

public class CoalFiredPowerPlant extends StorageCapableAbstractPlant {
    private final PowerPlantType type;
    private final double maxPower;

    private int resourceConsumption;

    public CoalFiredPowerPlant(Storage storage) {
        super(storage);
        this.type = PowerPlantType.COAL;
        this.maxPower = MaxPower.COAL.getPower();
        this.resourceConsumption = ResourceConsumption.COAL.getConsumption();
    }

    public PowerPlantType getType() {
        return type;
    }

    public double getMaxPower() {
        return maxPower;
    }

    @Override
    public int getResourceConsumption() {
        return resourceConsumption;
    }

    @Override
    public void setResourceConsumption(int resourceConsumption) {
        this.resourceConsumption = resourceConsumption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CoalFiredPowerPlant plant = (CoalFiredPowerPlant) o;
        return Double.compare(plant.maxPower, maxPower) == 0 &&
                type == plant.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type, maxPower);
    }

    @Override
    public String toString() {
        return "CoalFiredPowerPlant{" +
                "type=" + type +
                ", maxPower=" + maxPower +
                super.toString() +
                ", resourceConsumption=" + resourceConsumption +
                '}';
    }
}