package com.electricity.service.plant.impl;

import com.electricity.enumeration.PowerPlantCost;
import com.electricity.enumeration.PowerPlantType;
import com.electricity.exeption.UnknownPowerPlantTypeException;
import com.electricity.service.plant.PowerPlantPriceSettingService;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PowerPlantPriceSettingServiceImplTest {
    private final PowerPlantPriceSettingService service = new PowerPlantPriceSettingServiceImpl();

    @TestFactory
    Stream<DynamicTest> getPowerPlantCost() {
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
    void getPowerPlantCoastFromNullType() {

        assertThrows(UnknownPowerPlantTypeException.class,
                () -> service.getPowerPlantCost(null));
    }
}