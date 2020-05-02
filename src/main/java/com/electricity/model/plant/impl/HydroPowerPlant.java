package com.electricity.model.plant.impl;

import com.electricity.enumeration.MaxPower;
import com.electricity.enumeration.PowerPlantType;
import com.electricity.enumeration.ResourceConsumption;
import com.electricity.model.plant.StorageCapableAbstractPlant;
import com.electricity.model.storage.Storage;

import java.util.Objects;

public class HydroPowerPlant extends StorageCapableAbstractPlant {
    private final double maxPower;
    private final PowerPlantType type;

    private int resourceConsumption;

    public HydroPowerPlant(Storage storage) {
        super(storage);
        this.maxPower = MaxPower.HYDRO.getPower();
        this.type = PowerPlantType.HYDRO;
        this.resourceConsumption = ResourceConsumption.HYDRO.getConsumption();
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
        HydroPowerPlant that = (HydroPowerPlant) o;
        return Double.compare(that.maxPower, maxPower) == 0 &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), maxPower, type);
    }

    @Override
    public String toString() {
        return "HydroPowerPlant{" +
                "type=" + type +
                ", maxPower=" + maxPower +
                super.toString() +
                ", resourceConsumption=" + resourceConsumption +
                '}';
    }
}