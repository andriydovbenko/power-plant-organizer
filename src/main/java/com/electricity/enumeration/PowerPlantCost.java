package com.electricity.enumeration;

import java.math.BigDecimal;

public enum PowerPlantCost {
    //cost of construction power plant in usd
    COAL(BigDecimal.valueOf(25_000_000)),
    HYDRO(BigDecimal.valueOf(30_000_000)),
    NUCLEAR(BigDecimal.valueOf(32_000_000)),
    SOLAR(BigDecimal.valueOf(10_000_000)),
    WIND(BigDecimal.valueOf(8_000_000));

    private final BigDecimal cost;

    PowerPlantCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getCost() {
        return cost;
    }
}