package com.electricity.model.resource.storable;

import com.electricity.model.resource.StorableResource;

public class Water implements StorableResource {
    private int amount;

    public Water(int amount) {
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
        return "Water{" +
                ", amount=" + amount +
                '}';
    }
}