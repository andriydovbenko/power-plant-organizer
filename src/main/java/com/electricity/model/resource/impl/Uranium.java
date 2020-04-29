package com.electricity.model.resource.impl;

import com.electricity.model.resource.StorableResource;

import java.util.Objects;
import java.util.UUID;

public class Uranium implements StorableResource {
    private String id;
    private int amount;

    public Uranium(int amount) {
        this.amount = amount;
        this.id = UUID.randomUUID().toString();
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
        Uranium uranium = (Uranium) o;
        return id.equals(uranium.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}