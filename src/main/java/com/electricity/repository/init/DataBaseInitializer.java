package com.electricity.repository.init;

import com.electricity.model.dto.impl.PowerPlantCreatingDto;
import com.electricity.model.plant.PowerPlant;
import com.electricity.service.plant.PowerPlantConstructor;
import com.electricity.service.plant.impl.PowerPlantConstructorImpl;

import java.util.ArrayList;
import java.util.List;

import static com.electricity.enumeration.PowerPlantType.*;

public class DataBaseInitializer {

    private DataBaseInitializer() {
    }

    public static final PowerPlantConstructor POWER_PLANT_CONSTRUCTOR =
            new PowerPlantConstructorImpl();

    public static List<PowerPlant> initialize() {
        PowerPlantCreatingDto dto1 = new PowerPlantCreatingDto();
            dto1.setType(COAL);
            dto1.setCountry("USA");
            dto1.setNumberOfEmployees(1200);
            dto1.setWorking(true);

        PowerPlantCreatingDto dto2 = new PowerPlantCreatingDto();
            dto2.setType(HYDRO);
            dto2.setCountry("Ukraine");
            dto2.setNumberOfEmployees(1000);
            dto2.setWorking(true);

        PowerPlantCreatingDto dto3 = new PowerPlantCreatingDto();
            dto3.setType(NUCLEAR);
            dto3.setCountry("Japan");
            dto3.setNumberOfEmployees(1600);
            dto3.setWorking(true);

        PowerPlantCreatingDto dto4 = new PowerPlantCreatingDto();
            dto4.setType(WIND);
            dto4.setCountry("Portugal");
            dto4.setNumberOfEmployees(100);
            dto4.setWorking(true);

        PowerPlantCreatingDto dto5 = new PowerPlantCreatingDto();
            dto5.setType(SOLAR);
            dto5.setCountry("Spain");
            dto5.setNumberOfEmployees(200);
            dto5.setWorking(true);

        List<PowerPlantCreatingDto> dtoList = new ArrayList<>();
            dtoList.add(dto1);
            dtoList.add(dto2);
            dtoList.add(dto3);
            dtoList.add(dto4);
            dtoList.add(dto5);

        List<PowerPlant> powerPlantList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            powerPlantList.add(POWER_PLANT_CONSTRUCTOR.construct(dtoList.get(i)));
        }

        return powerPlantList;
    }
}