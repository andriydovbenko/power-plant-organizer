package com.electricity.model.resource.storable;

import com.electricity.model.resource.StorableResource;

public class Coal implements StorableResource {
    private int amount;

    public Coal() {
        this.amount = 0;
    }

    public Coal(int amount) {
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

    @Override
    public String toString() {
        return "Coal{" +
                ", amount=" + amount +
                '}';
    }
}