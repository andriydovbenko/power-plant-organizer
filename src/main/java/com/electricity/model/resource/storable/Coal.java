package com.electricity.model.resource.storable;

import com.electricity.model.resource.StorableResource;

import java.util.Objects;
import java.util.UUID;

public class Coal implements StorableResource {
    private String id;
    private int amount;

    public Coal(int amount) {
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
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
        Coal coal = (Coal) o;
        return id.equals(coal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Coal{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                '}';
    }
}