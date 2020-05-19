package com.electricity.model.plant;

import com.electricity.model.resource.UnstorableResource;

public abstract class StorageIncapableAbstractPlant extends PowerPlant {
    private UnstorableResource resource;

    protected StorageIncapableAbstractPlant() {
    }

    protected StorageIncapableAbstractPlant(UnstorableResource resource) {
        this.resource = resource;
    }

    public UnstorableResource getResource() {
        return resource;
    }

    public void setResource(UnstorableResource resource) {
        this.resource = resource;
    }

    @Override
    public int getResourceAmount() {
        return resource.getWorkTimeLeft();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString() + ", resource=" + resource;
    }
}