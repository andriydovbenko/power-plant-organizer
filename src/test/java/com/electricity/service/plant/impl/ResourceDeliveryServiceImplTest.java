package com.electricity.service.plant.impl;

import com.electricity.enumeration.PurchasableResourceType;
import com.electricity.exeption.NotEnoughStorageSpaceException;
import com.electricity.exeption.UnknownResourceTypeException;
import com.electricity.model.plant.PowerPlant;
import com.electricity.model.plant.impl.CoalFiredPowerPlant;
import com.electricity.model.plant.impl.NuclearPowerPlan;
import com.electricity.model.plant.impl.WindPowerPlant;
import com.electricity.model.resource.storable.Coal;
import com.electricity.model.resource.storable.Uranium;
import com.electricity.model.resource.unstorable.Wind;
import com.electricity.model.storage.impl.CoalStorage;
import com.electricity.model.storage.impl.UraniumStorage;
import com.electricity.model.transaction.ResourceTransaction;
import com.electricity.service.plant.ResourceDeliveryService;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResourceDeliveryServiceImplTest {
    private final ResourceDeliveryService service = new ResourceDeliveryServiceImpl();
    private final int initialAmountOfResource = 0;
    private final Collection<DynamicTest> dynamicTests = new ArrayList<>();
    private Executable executable;

    @Test
    void testInappropriatePowerPlantType() {
        PowerPlant powerPlant = new WindPowerPlant(new Wind(initialAmountOfResource));

        assertThrows(UnknownResourceTypeException.class,
                () -> service.setResourceToPowerPlant(powerPlant, null));
    }

    @TestFactory
    Collection<DynamicTest> setResourceToCoalPowerPlant() {
        int storageCapacity = CoalStorage.CAPACITY;
        int[] resourceAmountRange = {0, 10000, 30000, 50000, 80000};
        int expectedAmountAfterOperation = initialAmountOfResource;

        CoalFiredPowerPlant powerPlant = initCoalFiredPowerPlant();

        for (Integer amount : resourceAmountRange) {
            ResourceTransaction transaction = createCoalTransaction(powerPlant, amount);

            String testName = createNameForDynamicTest(amount);

            if (expectedAmountAfterOperation <= storageCapacity) {
                expectedAmountAfterOperation += amount;
                this.executable = () -> assertDoesNotThrow(
                        () -> service.setResourceToPowerPlant(powerPlant, transaction));
            } else {
                this.executable = () -> assertThrows(NotEnoughStorageSpaceException.class,
                        () -> service.setResourceToPowerPlant(powerPlant, transaction));
            }

            dynamicTests.add(DynamicTest.dynamicTest(testName, executable));
        }

        return dynamicTests;
    }

    private ResourceTransaction createCoalTransaction(CoalFiredPowerPlant powerPlant, Integer amount) {
        ResourceTransaction transaction = new ResourceTransaction(
                powerPlant.getId(), amount, PurchasableResourceType.COAL);
        transaction.setStorableResource(new Coal(amount));
        return transaction;
    }

    private String createNameForDynamicTest(Integer amount) {
        return "Set resource=" + amount + " to Power Plant";
    }

    private CoalFiredPowerPlant initCoalFiredPowerPlant() {
        Coal coal = new Coal(initialAmountOfResource);
        CoalStorage emptyCoalStorage = new CoalStorage();
        emptyCoalStorage.initializeResource(coal);

        return new CoalFiredPowerPlant(emptyCoalStorage);
    }

    @TestFactory
    Collection<DynamicTest> setResourceToNuclearPowerPlant() {
        int storageCapacity = UraniumStorage.CAPACITY;
        int[] resourceAmountRange = {0, 1000, 3000, 5000, 8000};
        int expectedAmountAfterOperation = initialAmountOfResource;

        PowerPlant powerPlant = initNuclearPowerPlant();

        for (Integer amount : resourceAmountRange) {
            ResourceTransaction transaction = createUraniumTransaction(powerPlant, amount);

            String testName = createNameForDynamicTest(amount);

            if (expectedAmountAfterOperation <= storageCapacity) {
                expectedAmountAfterOperation += amount;
                this.executable = () -> assertDoesNotThrow(
                        () -> service.setResourceToPowerPlant(powerPlant, transaction));
            } else {
                this.executable = () -> assertThrows(NotEnoughStorageSpaceException.class,
                        () -> service.setResourceToPowerPlant(powerPlant, transaction));
            }

            dynamicTests.add(DynamicTest.dynamicTest(testName, executable));
        }

        return dynamicTests;
    }

    private ResourceTransaction createUraniumTransaction(PowerPlant powerPlant, Integer amount) {
        ResourceTransaction transaction = new ResourceTransaction(
                powerPlant.getId(), amount, PurchasableResourceType.URANIUM);
        transaction.setStorableResource(new Uranium(amount));
        return transaction;
    }

    private NuclearPowerPlan initNuclearPowerPlant() {
        Uranium uranium = new Uranium(initialAmountOfResource);
        UraniumStorage emptyUraniumStorage = new UraniumStorage();
        emptyUraniumStorage.initializeResource(uranium);

        return new NuclearPowerPlan(emptyUraniumStorage);
    }
}