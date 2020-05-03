package com.electricity.model.plant.impl;

import com.electricity.enumeration.MaxPower;
import com.electricity.enumeration.PowerPlantType;
import com.electricity.enumeration.ResourceConsumption;
import com.electricity.model.plant.StorageIncapableAbstractPlant;
import com.electricity.model.resource.UnstorableResource;

import java.util.Objects;

public class SolarPowerPlant extends StorageIncapableAbstractPlant {
    private final PowerPlantType type;

    private double maxPower;
    private int resourceConsumption;

    public SolarPowerPlant() {
        this.type = PowerPlantType.SOLAR;
        initPowerAndConsumption();
    }

    public SolarPowerPlant(UnstorableResource resource) {
        super(resource);
        this.type = PowerPlantType.SOLAR;
        initPowerAndConsumption();
    }

    private void initPowerAndConsumption() {
        this.maxPower = MaxPower.SOLAR.getPower();
        this.resourceConsumption = ResourceConsumption.SOLAR.getConsumption();
    }

    @Override
    public PowerPlantType getType() {
        return type;
    }

    @Override
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
        SolarPowerPlant that = (SolarPowerPlant) o;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type);
    }

    @Override
    public String toString() {
        return "SolarPowerPlant{" +
                "type=" + type +
                ", maxPower=" + maxPower +
                super.toString() +
                ", resourceConsumption=" + resourceConsumption +
                '}';
    }
}
