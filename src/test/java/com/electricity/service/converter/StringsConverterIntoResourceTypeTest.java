package com.electricity.service.converter;

import com.electricity.enumeration.PurchasableResourceType;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringsConverterIntoResourceTypeTest {

    @TestFactory
    Collection<DynamicTest> convertationTests() {
        //Given
        final String[] stringTypes = {"COAL", "URANIUM", "NonExistent"};
        final List<PurchasableResourceType> resourceTypes = new ArrayList<>() {{
            this.add(PurchasableResourceType.COAL);
            this.add(PurchasableResourceType.URANIUM);
            this.add(null);
        }};

        Collection<DynamicTest> convertationDynamicTests = new ArrayList<>();

        //When
        for (int i = 0; i < stringTypes.length; i++) {
            String testName = "Convertation String [" + stringTypes[i] + "] to PurchasableResourceType";

            PurchasableResourceType expectedType = resourceTypes.get(i);
            PurchasableResourceType realType = StringsConverterIntoResourceType.convert(stringTypes[i]);

            //Than
            Executable exec = () -> assertEquals(expectedType, realType);
            convertationDynamicTests.add(DynamicTest.dynamicTest(testName, exec));
        }

        return convertationDynamicTests;
    }
}