package com.electricity.service.plant.impl;

import com.electricity.enumeration.plant.PowerPlantCost;
import com.electricity.enumeration.plant.PowerPlantType;
import com.electricity.exeption.UnknownPowerPlantTypeException;
import com.electricity.service.plant.PowerPlantPriceSetterService;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PowerPlantPriceSetterServiceImplTest {
    private final PowerPlantPriceSetterService service = new PowerPlantPriceSetterServiceImpl();

    @TestFactory
    Stream<DynamicTest> should_get_plant_cost() {
        //Given
        List<PowerPlantType> plantTypes = new ArrayList<>(List.of(
                PowerPlantType.COAL, PowerPlantType.NUCLEAR, PowerPlantType.HYDRO,
                PowerPlantType.SOLAR, PowerPlantType.WIND
        ));

        BigDecimal[] expectedPrices = {PowerPlantCost.COAL.getCost(), PowerPlantCost.NUCLEAR.getCost(),
                PowerPlantCost.HYDRO.getCost(), PowerPlantCost.SOLAR.getCost(), PowerPlantCost.WIND.getCost()};

        return plantTypes.stream().map(type -> DynamicTest.dynamicTest(
                "Get Price for " + type + " Power Plant", () -> {

                    int index = plantTypes.indexOf(type);
                    //When
                    BigDecimal realCoast = service.getPowerPlantCost(type);

                    //Then
                    assertEquals(expectedPrices[index], realCoast);
                }));
    }

    @Test
    void should_get_plant_cost_by_null_type() {

        assertThrows(UnknownPowerPlantTypeException.class,
                () -> service.getPowerPlantCost(null));
    }
}