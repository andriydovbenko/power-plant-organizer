package com.electricity.service.market;

import com.electricity.model.transaction.ResourceTransaction;

@FunctionalInterface
public interface ResourceMarket {

    ResourceTransaction setPriceOfTransaction(ResourceTransaction resourceTransaction);
}