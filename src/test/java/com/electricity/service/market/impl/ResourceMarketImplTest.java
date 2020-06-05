package com.electricity.service.market.impl;

import com.electricity.enumeration.resource.PurchasableResourceType;
import com.electricity.enumeration.resource.ResourcePrice;
import com.electricity.exeption.UnknownResourceTypeException;
import com.electricity.model.transaction.ResourceTransaction;
import com.electricity.service.market.ResourceMarket;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResourceMarketImplTest {
    private final BigDecimal pricePerCoalItem = ResourcePrice.COAL.getPrice();
    private final BigDecimal pricePerUraniumItem = ResourcePrice.URANIUM.getPrice();
    private final PurchasableResourceType coal = PurchasableResourceType.COAL;
    private final PurchasableResourceType uranium = PurchasableResourceType.URANIUM;
    private final ResourceMarket market = new ResourceMarketImpl();
    private final int[] resourceAmounts = {0, 10, 1000, 100000};
    private final String id = "1111";

    @TestFactory
    Collection<DynamicTest> should_check_coal_transaction_price() {
        List<ResourceTransaction> coalTransactions = new ArrayList<>() {{
            for (int resourceAmount : resourceAmounts) {
                this.add(new ResourceTransaction(id, resourceAmount, coal));
            }
        }};

        Collection<DynamicTest> coalDynamicTests = new ArrayList<>();

        for (int i = 0; i < coalTransactions.size(); i++) {
            ResourceTransaction testedTransaction =
                    market.setPriceOfTransaction(coalTransactions.get(i));

            BigDecimal expectedPrice = pricePerCoalItem
                    .multiply(BigDecimal.valueOf(resourceAmounts[i]));

            String testName = "Test Coal price with quantity=" + resourceAmounts[i];

            Executable exec = () -> assertEquals(expectedPrice,
                    testedTransaction.getTransactionPrice());

            DynamicTest dTest = DynamicTest.dynamicTest(testName, exec);

            coalDynamicTests.add(dTest);
        }
        return coalDynamicTests;
    }

    @TestFactory
    Collection<DynamicTest> should_check_uranium_transaction_price() {

        List<ResourceTransaction> uraniumTransactions = new ArrayList<>() {{
            for (int resourceAmount : resourceAmounts) {
                this.add(new ResourceTransaction(id, resourceAmount, uranium));
            }
        }};

        Collection<DynamicTest> uraniumDynamicTests = new ArrayList<>();

        for (int i = 0; i < uraniumTransactions.size(); i++) {
            ResourceTransaction testedTransaction =
                    market.setPriceOfTransaction(uraniumTransactions.get(i));

            BigDecimal expectedPrice = pricePerUraniumItem.
                    multiply(BigDecimal.valueOf(resourceAmounts[i]));

            String testName = "Test Uranium price with quantity=" + resourceAmounts[i];

            Executable exec = () -> assertEquals(expectedPrice,
                    testedTransaction.getTransactionPrice());

            DynamicTest dTest = DynamicTest.dynamicTest(testName, exec);

            uraniumDynamicTests.add(dTest);
        }
        return uraniumDynamicTests;
    }

    @Test
    void should_throw_null_resource_type_exception() {
        ResourceTransaction nullTypeTransaction = new ResourceTransaction(
                id, 10, null);

        assertThrows(UnknownResourceTypeException.class,
                () -> market.setPriceOfTransaction(nullTypeTransaction));
    }
}