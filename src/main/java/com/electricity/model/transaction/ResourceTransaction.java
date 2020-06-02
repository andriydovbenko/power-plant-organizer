package com.electricity.model.transaction;

import com.electricity.enumeration.PurchasableResourceType;
import com.electricity.model.resource.StorableResource;

import java.math.BigDecimal;

public class ResourceTransaction {
    private final String powerPlantId;
    private final int amount;
    private final PurchasableResourceType resourceType;

    private StorableResource storableResource;
    private BigDecimal transactionPrice;

    public ResourceTransaction(String powerPlantId, int amount, PurchasableResourceType resourceType) {
        this.powerPlantId = powerPlantId;
        this.amount = amount;
        this.resourceType = resourceType;
    }

    public String getPowerPlantId() {
        return powerPlantId;
    }

    public int getAmount() {
        return amount;
    }

    public PurchasableResourceType getResourceType() {
        return resourceType;
    }

    public StorableResource getStorableResource() {
        return storableResource;
    }

    public void setStorableResource(StorableResource storableResource) {
        this.storableResource = storableResource;
    }

    public BigDecimal getTransactionPrice() {
        return transactionPrice;
    }

    public void setTransactionPrice(BigDecimal transactionPrice) {
        this.transactionPrice = transactionPrice;
    }
}