package com.electricity.repository.plant;

import com.electricity.model.plant.PowerPlant;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface PowerPlantSwitcher {

    PowerPlant switchTo(ResultSet resultSet) throws SQLException;
}