package com.electricity.service.converter;

import com.electricity.enumeration.PowerPlantType;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringConverterIntoPlantTypeTest {

    @TestFactory
    Collection<DynamicTest> should_convert_string_into_plant_type() {
        //Given
        final String[] stringTypes = {"HYDRO", "WIND", "SOLAR", "NUCLEAR", "COAL", "NonExistent"};
        final List<PowerPlantType> powerPlantTypes = new ArrayList<>() {{
            this.add(PowerPlantType.HYDRO);
            this.add(PowerPlantType.WIND);
            this.add(PowerPlantType.SOLAR);
            this.add(PowerPlantType.NUCLEAR);
            this.add(PowerPlantType.COAL);
            this.add(null);
        }};

        Collection<DynamicTest> convertationDynamicTests = new ArrayList<>();

        //When
        for (int i = 0; i < stringTypes.length; i++) {
            String testName = "Convertation String [" + stringTypes[i] + "] to PowerPlantType";

            PowerPlantType expectedType = powerPlantTypes.get(i);
            PowerPlantType realType = StringConverterIntoPlantType.convert(stringTypes[i]);

            //Than
            Executable exec = () -> assertEquals(expectedType, realType);
            convertationDynamicTests.add(DynamicTest.dynamicTest(testName, exec));
        }

        return convertationDynamicTests;
    }
}