package com.electricity.service.market.impl;

import com.electricity.enumeration.resource.PurchasableResourceType;
import com.electricity.exeption.UnknownResourceTypeException;
import com.electricity.model.resource.storable.Coal;
import com.electricity.model.resource.storable.Uranium;
import com.electricity.model.transaction.ResourceTransaction;
import com.electricity.service.market.ResourceMarket;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import static com.electricity.enumeration.resource.ResourcePrice.COAL;
import static com.electricity.enumeration.resource.ResourcePrice.URANIUM;

public class ResourceMarketImpl implements ResourceMarket {
    private final Map<PurchasableResourceType, ResourceMarket> transactionMethods;

    public ResourceMarketImpl() {
        this.transactionMethods = Collections.synchronizedMap(new EnumMap<>(PurchasableResourceType.class));
        transactionMethods.put(PurchasableResourceType.COAL, this::setPriceForCoal);
        transactionMethods.put(PurchasableResourceType.URANIUM, this::setPriceForUranium);
    }

    @Override
    public ResourceTransaction setPriceOfTransaction(ResourceTransaction resourceTransaction) {

        return transactionMethods.getOrDefault(
                resourceTransaction.getResourceType(), this::throwUnknownResourceTypeException)
                .setPriceOfTransaction(resourceTransaction);
    }

    private ResourceTransaction setPriceForCoal(ResourceTransaction resourceTransaction) {
        BigDecimal pricePerItem = COAL.getPrice();
        int amount = resourceTransaction.getAmount();

        resourceTransaction.setTransactionPrice(
                pricePerItem.multiply(BigDecimal.valueOf(amount)));

        resourceTransaction.setStorableResource(new Coal(amount));

        return resourceTransaction;
    }

    private ResourceTransaction setPriceForUranium(ResourceTransaction resourceTransaction) {
        BigDecimal pricePerItem = URANIUM.getPrice();
        int amount = resourceTransaction.getAmount();

        resourceTransaction.setTransactionPrice(
                pricePerItem.multiply(BigDecimal.valueOf(amount)));

        resourceTransaction.setStorableResource(new Uranium(amount));

        return resourceTransaction;
    }

    private ResourceTransaction throwUnknownResourceTypeException(ResourceTransaction resourceTransaction) {

        throw new UnknownResourceTypeException("Resource type: " + resourceTransaction.getResourceType() +
                " is not supported by the application");
    }
}