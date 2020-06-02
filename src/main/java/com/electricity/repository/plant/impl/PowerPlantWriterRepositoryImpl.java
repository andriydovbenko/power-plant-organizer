package com.electricity.repository.plant.impl;

import com.electricity.enumeration.PowerPlantType;
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

import static com.electricity.enumeration.Driver.POSTGRES;
import static com.electricity.enumeration.PowerPlantColumnName.*;
import static com.electricity.enumeration.PowerPlantType.*;

public class PowerPlantWriterRepositoryImpl implements PowerPlantWriterRepository {
    private static final Logger LOGGER = LogManager.getLogger(PowerPlantWriterRepositoryImpl.class);
    private static final String COMMA = ", ";
    private static final String QUOTE = "'";
    private static final String CLOSING_BRACKET = ")";
    private static final String OPENING_BRACKET = "(";
    private final String firstPartOfQueryForAllTypes;
    private final String url;
    private final String username;
    private final String password;
    private final Map<PowerPlantType, PowerPlantWriterRepository> writingMethods;
    private final String tableName;
    private StringBuilder sql;

    public PowerPlantWriterRepositoryImpl(String url, String username, String password, String tableName) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.tableName = tableName;
        this.firstPartOfQueryForAllTypes = getBeginningOfQuery();
        this.writingMethods = new EnumMap<>(PowerPlantType.class);
        writingMethods.put(COAL, this::insertStorageCapablePlants);
        writingMethods.put(NUCLEAR, this::insertStorageCapablePlants);
        writingMethods.put(HYDRO, this::insertStorageCapablePlants);
        writingMethods.put(SOLAR, this::insertStorageIncapablePlants);
        writingMethods.put(WIND, this::insertStorageIncapablePlants);
        setDriver();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    private void setDriver() {
        try {
            Class.forName(POSTGRES.getPath());
        } catch (ClassNotFoundException e) {
            LOGGER.error(e);
        }
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
        return "INSERT INTO " + tableName +
                OPENING_BRACKET + ID.getName() + COMMA +
                TYPE.getName() + COMMA +
                COUNTRY.getName() + COMMA +
                NUMBER_OF_EMPLOYEE.getName() + COMMA +
                IS_WORKING.getName() + COMMA +
                MAX_POWER.getName() + COMMA +
                RESOURCE_CONSUMPTION.getName() + COMMA +
                RESOURCE_AMOUNT.getName() + CLOSING_BRACKET +
                " VALUES(";
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

    private void throwUnknownPowerPlantException(PowerPlant powerPlant) {
        throw new UnknownPowerPlantTypeException("Power plant Type: \"" + powerPlant.getType() +
                "\" is not being supported by the application");
    }

    private void throwUnknownPowerPlantExceptionCausedByNull() {
        throw new UnknownPowerPlantTypeException("powerPlant = null. " +
                "Check the PowerPlantCreatingDto creation method");
    }
}