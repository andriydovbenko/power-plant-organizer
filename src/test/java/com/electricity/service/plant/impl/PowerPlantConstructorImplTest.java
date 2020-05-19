package com.electricity.service.plant.impl;

import com.electricity.enumeration.PowerPlantType;
import com.electricity.enumeration.ResourceConsumption;
import com.electricity.exeption.UnknownPowerPlantTypeException;
import com.electricity.model.dto.impl.PowerPlantCreatingDto;
import com.electricity.model.plant.PowerPlant;
import com.electricity.service.plant.PowerPlantConstructor;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PowerPlantConstructorImplTest {
    private final PowerPlantConstructor constructor = new PowerPlantConstructorImpl();
    private final String country = "Portugal";
    private final int numberOfEmployee = 500;
    private final boolean isWorking = true;
    private final PowerPlantCreatingDto dto = new PowerPlantCreatingDto();
    private final int idLength = 36;

    PowerPlantConstructorImplTest() {
        dto.setCountry(country);
        dto.setNumberOfEmployees(numberOfEmployee);
        dto.setWorking(true);
    }

    @Test
    void constructWithNullType() {
        dto.setType(null);

        assertThrows(UnknownPowerPlantTypeException.class, () -> constructor.construct(dto));
        assertThrows(UnknownPowerPlantTypeException.class, () -> constructor.construct(null));

    }

    @TestFactory
    Stream<DynamicTest> construct() {
        //Given
        List<PowerPlantType> plantTypes = new ArrayList<>(List.of(
                PowerPlantType.COAL, PowerPlantType.NUCLEAR, PowerPlantType.HYDRO,
                PowerPlantType.SOLAR, PowerPlantType.WIND
        ));

        int[] powerPlantResourceConsumption = {ResourceConsumption.COAL.getConsumption(),
                ResourceConsumption.NUCLEAR.getConsumption(), ResourceConsumption.HYDRO.getConsumption(),
                ResourceConsumption.SOLAR.getConsumption(), ResourceConsumption.WIND.getConsumption()};

        return plantTypes.stream().map(type -> DynamicTest.dynamicTest(
                "Construction " + type + " Power Plant", () -> {
                    int index = plantTypes.indexOf(type);
                    dto.setType(type);

                    //When
                    PowerPlant powerPlant = constructor.construct(dto);

                    //Then
                    assertEquals(type, powerPlant.getType());
                    assertEquals(country, powerPlant.getCountry());
                    assertEquals(numberOfEmployee, powerPlant.getNumberOfEmployees());
                    assertEquals(isWorking, powerPlant.isWorking());
                    assertEquals(powerPlantResourceConsumption[index], powerPlant.getResourceConsumption());
                    assertEquals(idLength, powerPlant.getId().length());
                }));
    }
}