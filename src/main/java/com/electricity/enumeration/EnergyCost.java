package com.electricity.enumeration;

import java.math.BigDecimal;

public enum EnergyCost {
    ENERGY();

    private final BigDecimal cost;

    EnergyCost() {
        this.cost = BigDecimal.valueOf(100);
    }

    public BigDecimal getCost() {
        return cost;
    }
}