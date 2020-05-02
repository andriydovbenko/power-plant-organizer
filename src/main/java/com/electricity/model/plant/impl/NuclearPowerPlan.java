package com.electricity.model.plant.impl;

import com.electricity.enumeration.MaxPower;
import com.electricity.enumeration.PowerPlantType;
import com.electricity.enumeration.ResourceConsumption;
import com.electricity.model.plant.StorageCapableAbstractPlant;
import com.electricity.model.storage.Storage;

import java.util.Objects;

public class NuclearPowerPlan extends StorageCapableAbstractPlant {
    private final double maxPower;
    private final PowerPlantType type;

    private int resourceConsumption;

    public NuclearPowerPlan(Storage storage) {
        super(storage);
        this.maxPower = MaxPower.NUCLEAR.getPower();
        this.type = PowerPlantType.NUCLEAR;
        this.resourceConsumption = ResourceConsumption.NUCLEAR.getConsumption();
    }

    @Override
    public PowerPlantType getType() {
        return type;
    }

    @Override
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
        NuclearPowerPlan powerPlan = (NuclearPowerPlan) o;
        return Double.compare(powerPlan.maxPower, maxPower) == 0 &&
                type == powerPlan.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), maxPower, type);
    }

    @Override
    public String toString() {
        return "NuclearPowerPlant{" +
                "type=" + type +
                ", maxPower=" + maxPower +
                super.toString() +
                ", resourceConsumption=" + resourceConsumption +
                '}';
    }
}