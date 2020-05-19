package com.electricity.model.resource.storable;

import com.electricity.model.resource.StorableResource;

public class Uranium implements StorableResource {
    private int amount;

    public Uranium(int amount) {
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
}