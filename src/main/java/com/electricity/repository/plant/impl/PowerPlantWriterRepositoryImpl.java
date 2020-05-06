package com.electricity.repository.plant.impl;

import com.electricity.enumeration.PowerPlantColumnName;
import com.electricity.enumeration.PowerPlantType;
import com.electricity.enumeration.TableName;
import com.electricity.exeption.UnknownPowerPlantTypeException;
import com.electricity.model.plant.PowerPlant;
import com.electricity.model.plant.StorageCapableAbstractPlant;
import com.electricity.model.plant.StorageIncapableAbstractPlant;
import com.electricity.repository.plant.PowerPlantWriterRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EnumMap;
import java.util.Map;

public class PowerPlantWriterRepositoryImpl implements PowerPlantWriterRepository {
    private static final Logger LOGGER = LogManager.getLogger(PowerPlantWriterRepositoryImpl.class);
    private static final String COMMA = ", ";
    private static final String QUOTE = "'";
    private static final String CLOSING_BRACKET = ")";
    private static final String OPENING_BRACKET = "(";
    private static final String VALUES = " VALUES(";
    private static final String INSERT_INTO = "INSERT INTO ";
    private final String firstPartOfQueryForAllTypes;
    private final String url;
    private final String username;
    private final String password;
    private final Map<PowerPlantType, PowerPlantWriterRepository> writingMethods;
    private StringBuilder sql;

    public PowerPlantWriterRepositoryImpl(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.firstPartOfQueryForAllTypes = getBeginningOfQuery();
        this.writingMethods = new EnumMap<>(PowerPlantType.class);
        writingMethods.put(PowerPlantType.COAL, this::insertStorageCapablePlants);
        writingMethods.put(PowerPlantType.NUCLEAR, this::insertStorageCapablePlants);
        writingMethods.put(PowerPlantType.HYDRO, this::insertStorageCapablePlants);
        writingMethods.put(PowerPlantType.SOLAR, this::insertStorageIncapablePlants);
        writingMethods.put(PowerPlantType.WIND, this::insertStorageIncapablePlants);
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public void insertToDb(PowerPlant powerPlant) {

        if (powerPlant != null) {
            sql = new StringBuilder(firstPartOfQueryForAllTypes);

            writingMethods.getOrDefault(powerPlant.getType(), this::throwUnknownPowerPlantException)
                    .insertToDb(powerPlant);
        } else {
            throwUnknownPowerPlantExceptionCausedByNull();
        }
    }

    private void insertStorageCapablePlants(PowerPlant powerPlant) {
        StorageCapableAbstractPlant storageCapablePlant = (StorageCapableAbstractPlant) powerPlant;

        sql
                .append(createPartOfQueryCommonToAllPowerPlants(powerPlant))
                .append(QUOTE).append(storageCapablePlant.getStorage().getAmountOfResource())
                .append(QUOTE).append(CLOSING_BRACKET);

        executeStatement(sql);
    }

    private void insertStorageIncapablePlants(PowerPlant powerPlant) {
        StorageIncapableAbstractPlant storageIncapablePlant = (StorageIncapableAbstractPlant) powerPlant;

        sql
                .append(createPartOfQueryCommonToAllPowerPlants(powerPlant))
                .append(QUOTE).append(storageIncapablePlant.getResource().getWorkTimeLeft())
                .append(QUOTE).append(CLOSING_BRACKET);

        executeStatement(sql);
    }

    private void executeStatement(StringBuilder sql) {
        StringBuilder messageInfo = new StringBuilder();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql.toString());
            messageInfo.append(" Power plant inserted to Table. Query =").append(sql);
            LOGGER.debug(messageInfo);
        } catch (SQLException e) {
            messageInfo.append(e.getMessage());
            LOGGER.error(messageInfo);
            messageInfo.append(" Failed query =").append(sql);
            LOGGER.debug(messageInfo);
        }
    }


    private String getBeginningOfQuery() {
        return INSERT_INTO + TableName.POWER_PLANT.getName() +
                OPENING_BRACKET + PowerPlantColumnName.ID.getName() + COMMA +
                PowerPlantColumnName.TYPE.getName() + COMMA +
                PowerPlantColumnName.COUNTRY.getName() + COMMA +
                PowerPlantColumnName.NUMBER_OF_EMPLOYEE.getName() + COMMA +
                PowerPlantColumnName.IS_WORKING.getName() + COMMA +
                PowerPlantColumnName.MAX_POWER.getName() + COMMA +
                PowerPlantColumnName.RESOURCE_CONSUMPTION.getName() + COMMA +
                PowerPlantColumnName.RESOURCE_AMOUNT.getName() + CLOSING_BRACKET +
                VALUES;
    }

    private StringBuilder createPartOfQueryCommonToAllPowerPlants(PowerPlant powerPlant) {
        return new StringBuilder()
                .append(QUOTE).append(powerPlant.getId()).append(QUOTE).append(COMMA)
                .append(QUOTE).append(powerPlant.getType()).append(QUOTE).append(COMMA)
                .append(QUOTE).append(powerPlant.getCountry()).append(QUOTE).append(COMMA)
                .append(QUOTE).append(powerPlant.getNumberOfEmployees()).append(QUOTE).append(COMMA)
                .append(QUOTE).append(powerPlant.isWorking()).append(QUOTE).append(COMMA)
                .append(QUOTE).append(powerPlant.getMaxPower()).append(QUOTE).append(COMMA)
                .append(QUOTE).append(powerPlant.getResourceConsumption()).append(QUOTE).append(COMMA);
    }

    private PowerPlant throwUnknownPowerPlantException(PowerPlant powerPlant) {
        throw new UnknownPowerPlantTypeException("Power plant Type: \"" + powerPlant.getType() +
                "\" is not being supported by the application");
    }

    private void throwUnknownPowerPlantExceptionCausedByNull() {
        throw new UnknownPowerPlantTypeException("powerPlant = null. " +
                "Check the PowerPlantCreatingDto creation method");
    }
}