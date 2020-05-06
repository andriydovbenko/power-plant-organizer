package com.electricity.enumeration;

import java.math.BigDecimal;

public enum EnergyCost {
    ENERGY(100);

    private final BigDecimal cost;

    EnergyCost(int cost) {
        this.cost = BigDecimal.valueOf(cost);
    }

    public BigDecimal getCost() {
        return cost;
    }
}