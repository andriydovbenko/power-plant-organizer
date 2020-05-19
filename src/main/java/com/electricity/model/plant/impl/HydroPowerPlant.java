package com.electricity.model.plant.impl;

import com.electricity.enumeration.MaxPower;
import com.electricity.enumeration.PowerPlantType;
import com.electricity.enumeration.ResourceConsumption;
import com.electricity.model.plant.StorageCapableAbstractPlant;
import com.electricity.model.storage.impl.WaterReservoir;

import java.util.Objects;

public class HydroPowerPlant extends StorageCapableAbstractPlant {
    private final PowerPlantType type;

    private double maxPower;
    private int resourceConsumption;

    public HydroPowerPlant(WaterReservoir reservoir) {
        super(reservoir);
        this.type = PowerPlantType.HYDRO;
        initPowerAndConsumption();
    }

    private void initPowerAndConsumption() {
        this.maxPower = MaxPower.HYDRO.getPower();
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
        HydroPowerPlant that = (HydroPowerPlant) o;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type);
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