package com.electricity.repository.plant.impl;

import com.electricity.exeption.UnknownPowerPlantTypeException;
import com.electricity.model.plant.PowerPlant;
import com.electricity.model.plant.impl.*;
import com.electricity.model.resource.storable.Coal;
import com.electricity.model.resource.storable.Uranium;
import com.electricity.model.resource.storable.Water;
import com.electricity.model.resource.unstorable.SolarEnergy;
import com.electricity.model.resource.unstorable.Wind;
import com.electricity.model.storage.impl.CoalStorage;
import com.electricity.model.storage.impl.UraniumStorage;
import com.electricity.model.storage.impl.WaterReservoir;
import com.electricity.repository.plant.PowerPlantReaderRepository;
import com.electricity.repository.plant.PowerPlantSwitcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

import static com.electricity.enumeration.PowerPlantColumnName.*;
import static com.electricity.enumeration.PowerPlantCost.*;
import static com.electricity.enumeration.UserColumnName.ID;

public class PowerPlantReaderRepositoryImpl implements PowerPlantReaderRepository, PowerPlantSwitcher {
    private static final Logger LOGGER = LogManager.getLogger(PowerPlantReaderRepositoryImpl.class);
    private final String url;
    private final String username;
    private final String password;
    private final List<PowerPlant> powerPlants;
    private final Map<String, PowerPlantSwitcher> switchingMethod;
    private final String selectionQuery;

    public PowerPlantReaderRepositoryImpl(String url, String username, String password, String tableName) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.powerPlants = Collections.synchronizedList(new ArrayList<>());
        this.selectionQuery = "SELECT * FROM " + tableName;

        this.switchingMethod = new HashMap<>();
        switchingMethod.put(COAL.toString(), this::switchToCoalFiredPowerPlant);
        switchingMethod.put(NUCLEAR.toString(), this::switchToNuclearPowerPlant);
        switchingMethod.put(HYDRO.toString(), this::switchToHydroPowerPlant);
        switchingMethod.put(WIND.toString(), this::switchToWindPowerPlant);
        switchingMethod.put(SOLAR.toString(), this::switchToSolarPowerPlant);
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public List<PowerPlant> selectAll() {
        StringBuilder messageInfo = new StringBuilder();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resSet = statement.executeQuery(selectionQuery)) {

            while (resSet.next()) {
                PowerPlant plant = switchTo(resSet);

                plant.setId(resSet.getString(ID.getName()));
                plant.setWorking(resSet.getBoolean(IS_WORKING.getName()));
                plant.setCountry(resSet.getString(COUNTRY.getName()));
                plant.setResourceConsumption(resSet.getInt(RESOURCE_CONSUMPTION.getName()));
                plant.setNumberOfEmployees(resSet.getInt(NUMBER_OF_EMPLOYEE.getName()));

                powerPlants.add(plant);

                messageInfo.append(" Power plant= ").append(plant.toString())
                        .append(" successfully selected from database");
                LOGGER.debug(messageInfo);
            }
        } catch (SQLException | UnknownPowerPlantTypeException e) {
            messageInfo.append(e.getMessage());
            LOGGER.error(messageInfo);
            messageInfo.append(" Power plant number=").append((powerPlants.size() + 1))
                    .append(" caused exception");
            LOGGER.debug(messageInfo);
        }

        return powerPlants;
    }

    @Override
    public PowerPlant switchTo(ResultSet resultSet) throws SQLException {
        return switchingMethod.getOrDefault(resultSet.getString(TYPE.getName()),
                this::throwUnknownPowerPlantException).switchTo(resultSet);
    }

    private PowerPlant switchToCoalFiredPowerPlant(ResultSet resultSet) throws SQLException {
        CoalFiredPowerPlant plant = new CoalFiredPowerPlant(new CoalStorage());
        Coal coal = new Coal(resultSet.getInt(RESOURCE_AMOUNT.getName()));
        plant.getStorage().initializeResource(coal);
        plant.setMaxPower(resultSet.getDouble(MAX_POWER.getName()));

        return plant;
    }

    private PowerPlant switchToNuclearPowerPlant(ResultSet resultSet) throws SQLException {
        NuclearPowerPlan plant = new NuclearPowerPlan(new UraniumStorage());
        Uranium uranium = new Uranium(resultSet.getInt(RESOURCE_AMOUNT.getName()));
        plant.getStorage().initializeResource(uranium);
        plant.setMaxPower(resultSet.getDouble(MAX_POWER.getName()));

        return plant;
    }

    private PowerPlant switchToHydroPowerPlant(ResultSet resultSet) throws SQLException {
        HydroPowerPlant plant = new HydroPowerPlant(new WaterReservoir());
        Water water = new Water(resultSet.getInt(RESOURCE_AMOUNT.getName()));
        plant.getStorage().initializeResource(water);
        plant.setMaxPower(resultSet.getDouble(MAX_POWER.getName()));

        return plant;
    }

    private PowerPlant switchToWindPowerPlant(ResultSet resultSet) throws SQLException {
        WindPowerPlant plant = new WindPowerPlant(new Wind());
        plant.getResource().setWorkTimeLeft(resultSet.getInt(RESOURCE_AMOUNT.getName()));
        plant.setMaxPower(resultSet.getDouble(MAX_POWER.getName()));

        return plant;
    }

    private PowerPlant switchToSolarPowerPlant(ResultSet resultSet) throws SQLException {
        SolarPowerPlant plant = new SolarPowerPlant(new SolarEnergy());
        plant.getResource().setWorkTimeLeft(resultSet.getInt(RESOURCE_AMOUNT.getName()));
        plant.setMaxPower(resultSet.getDouble(MAX_POWER.getName()));

        return plant;
    }

    private PowerPlant throwUnknownPowerPlantException(ResultSet resultSet) throws SQLException {
        throw new UnknownPowerPlantTypeException("Power plant Type: \""
                + resultSet.getString(TYPE.getName()) +
                "\" is not being supported by the application");
    }
}