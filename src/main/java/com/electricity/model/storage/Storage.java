package com.electricity.model.storage;

import com.electricity.exeption.NotEnoughStorageSpaceException;
import com.electricity.model.resource.StorableResource;

import java.util.Objects;
import java.util.UUID;

public abstract class Storage {
    private String id;

    private StorableResource resource;
    private int capacity;

    public Storage(int capacity) {
        this.capacity = capacity;
        this.id = UUID.randomUUID().toString();
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

            throw new NotEnoughStorageSpaceException("You tried to add to storage to many Coal" +
                    "\nAfter operation losses account for " + excessAmount + " tons of Coal");
        }
    }


    public StorableResource getResource() {
        return resource;
    }

    public void setResource(StorableResource resource) {
        this.resource = resource;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
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
        Storage storage = (Storage) o;
        return id.equals(storage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Storage{" +
                "id='" + id + '\'' +
                ", resource=" + resource +
                ", capacity=" + capacity +
                '}';
    }
}