package com.electricity.model.resource.impl;

import com.electricity.model.resource.UnstorableResource;

import java.util.Objects;
import java.util.UUID;

public class Wind implements UnstorableResource {
    private String id;
    private int workTimeLeft;

    public Wind(int workTimeLeft) {
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
        Wind wind = (Wind) o;
        return id.equals(wind.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Wind{" +
                "id='" + id + '\'' +
                ", circlesCapableToWork=" + workTimeLeft +
                '}';
    }
}