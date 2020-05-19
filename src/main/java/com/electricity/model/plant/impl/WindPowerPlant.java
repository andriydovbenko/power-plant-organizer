package com.electricity.model.plant.impl;

import com.electricity.enumeration.MaxPower;
import com.electricity.enumeration.PowerPlantType;
import com.electricity.enumeration.ResourceConsumption;
import com.electricity.model.plant.StorageIncapableAbstractPlant;
import com.electricity.model.resource.unstorable.Wind;

import java.util.Objects;

public class WindPowerPlant extends StorageIncapableAbstractPlant {
    private final PowerPlantType type;

    private double maxPower;
    private int resourceConsumption;

    public WindPowerPlant(Wind wind) {
        super(wind);
        this.type = PowerPlantType.WIND;
        initPowerAndConsumption();
    }

    private void initPowerAndConsumption() {
        this.maxPower = MaxPower.WIND.getPower();
        this.resourceConsumption = ResourceConsumption.WIND.getConsumption();
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
        WindPowerPlant that = (WindPowerPlant) o;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type);
    }

    @Override
    public String toString() {
        return "WindPowerPlant{" +
                "type=" + type +
                ", maxPower=" + maxPower +
                super.toString() +
                ", resourceConsumption=" + resourceConsumption +
                '}';
    }
}