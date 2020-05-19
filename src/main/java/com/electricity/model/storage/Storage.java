package com.electricity.model.storage;

import com.electricity.exeption.NotEnoughStorageSpaceException;
import com.electricity.model.resource.StorableResource;

public abstract class Storage {
    private StorableResource resource;
    private final int capacity;

    public Storage(int capacity) {
        this.capacity = capacity;
    }

    public int getAmountOfResource() {
        return resource.getAmount();
    }

    public void addResourceToContainer(StorableResource storableResource) throws NotEnoughStorageSpaceException {
        int offeredAmount = storableResource.getAmount();
        int totalAmountAfterAdding = getAmountOfResource() + offeredAmount;

        if (totalAmountAfterAdding <= capacity) {
            resource.setAmount(totalAmountAfterAdding);
        } else {
            int excessAmount = totalAmountAfterAdding - capacity;
            resource.setAmount(capacity);

            throw new NotEnoughStorageSpaceException("Attempt adding too many resources into the storage." +
                    " Capacity of repository = \"" + capacity + "\"" +
                    " After operation Power Plant lost " + excessAmount + " tons of Coal");
        }
    }

    public void initializeResource(StorableResource resource) {
        this.resource = resource;
    }

    public StorableResource getResource() {
        return resource;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return "Storage{" +
                ", resource=" + resource +
                ", capacity=" + capacity +
                '}';
    }
}