package com.electricity.model.resource.impl;

import com.electricity.model.resource.UnstorableResource;

import java.util.Objects;
import java.util.UUID;

public class SolarEnergy implements UnstorableResource {
    private String id;
    private int workTimeLeft;

    public SolarEnergy(int workTimeLeft) {
        this.workTimeLeft = workTimeLeft;
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public int getWorkTimeLeft() {
        return workTimeLeft;
    }

    @Override
    public void setWorkTimeLeft(int workTimeLeft) {
        this.workTimeLeft = workTimeLeft;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SolarEnergy that = (SolarEnergy) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SolarEnergy{" +
                "id='" + id + '\'' +
                ", amountOfCirclesCapableToWork=" + workTimeLeft +
                '}';
    }
}