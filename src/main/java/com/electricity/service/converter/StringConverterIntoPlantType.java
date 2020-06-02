package com.electricity.service.converter;

import com.electricity.enumeration.PowerPlantType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StringConverterIntoPlantType {
    private static final List<PowerPlantType> powerPlants = new ArrayList<>() {{
        this.add(PowerPlantType.COAL);
        this.add(PowerPlantType.HYDRO);
        this.add(PowerPlantType.NUCLEAR);
        this.add(PowerPlantType.SOLAR);
        this.add(PowerPlantType.WIND);
    }};

    private StringConverterIntoPlantType() {
    }

    public static synchronized PowerPlantType convert(String type) {
        PowerPlantType plantType = null;

        Optional<PowerPlantType> typeOptional = powerPlants.stream()
                .filter(powerPlantType -> (powerPlantType.toString()).equals(type))
                .findFirst();

        if (typeOptional.isPresent()) {
            plantType = typeOptional.get();
        }

        return plantType;
    }
}