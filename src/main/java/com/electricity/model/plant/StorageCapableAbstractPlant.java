package com.electricity.model.plant;

import com.electricity.model.storage.Storage;

public abstract class StorageCapableAbstractPlant extends PowerPlant {
    private Storage storage;

    protected StorageCapableAbstractPlant(Storage storage) {
        this.storage = storage;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
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
    public int getResourceAmount() {
        return storage.getAmountOfResource();
    }

    @Override
    public String toString() {
        return super.toString() + ", storage=" + storage;
    }
}