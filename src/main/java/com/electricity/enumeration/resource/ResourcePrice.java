package com.electricity.enumeration.resource;

import java.math.BigDecimal;

public enum ResourcePrice {
    //Price in USD per item
    COAL(BigDecimal.valueOf(15)),
    URANIUM(BigDecimal.valueOf(200));

    private final BigDecimal price;

    ResourcePrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}