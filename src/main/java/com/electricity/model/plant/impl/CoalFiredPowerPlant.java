package com.electricity.model.plant.impl;

import com.electricity.enumeration.MaxPower;
import com.electricity.enumeration.PowerPlantType;
import com.electricity.enumeration.ResourceConsumption;
import com.electricity.model.plant.StorageCapableAbstractPlant;
import com.electricity.model.storage.impl.CoalStorage;

import java.util.Objects;

public class CoalFiredPowerPlant extends StorageCapableAbstractPlant {
    private final PowerPlantType type;

    private double maxPower;
    private int resourceConsumption;

    public CoalFiredPowerPlant(CoalStorage coalStorage) {
        super(coalStorage);
        this.type = PowerPlantType.COAL;
        initPowerAndConsumption();
    }

    private void initPowerAndConsumption() {
        this.maxPower = MaxPower.COAL.getPower();
        this.resourceConsumption = ResourceConsumption.COAL.getConsumption();
    }

    public PowerPlantType getType() {
        return type;
    }

    public double getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(double maxPower) {
        this.maxPower = maxPower;
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
        CoalFiredPowerPlant that = (CoalFiredPowerPlant) o;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type);
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