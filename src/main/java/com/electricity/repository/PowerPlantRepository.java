package com.electricity.repository;

import com.electricity.model.plant.PowerPlant;
import com.electricity.repository.jdbc.PostgresAuthenticationDataLoader;
import com.electricity.repository.plant.PowerPlantReaderRepository;
import com.electricity.repository.plant.PowerPlantWriterRepository;
import com.electricity.repository.plant.impl.PowerPlantReaderRepositoryImpl;
import com.electricity.repository.plant.impl.PowerPlantWriterRepositoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static com.electricity.enumeration.path.Driver.POSTGRES;
import static com.electricity.enumeration.repo.PowerPlantColumnName.*;

public class PowerPlantRepository {
    private static final Logger LOGGER = LogManager.getLogger(PowerPlantRepository.class);
    private final PowerPlantWriterRepository powerPlantWriter;
    private final PowerPlantReaderRepository powerPlantReader;
    private final String droppingTableQuery;
    private final String creatingTableQuery;
    private final String url;
    private final String username;
    private final String password;
    private final String tableName;
    private StringBuilder messageInfo;

    public PowerPlantRepository(String tableName) {
        this.tableName = tableName;
        PostgresAuthenticationDataLoader loader = PostgresAuthenticationDataLoader.getInstance();
        this.url = loader.getUrl();
        this.username = loader.getUsername();
        this.password = loader.getPassword();
        this.powerPlantWriter = new PowerPlantWriterRepositoryImpl(url, username, password, tableName);
        this.powerPlantReader = new PowerPlantReaderRepositoryImpl(url, username, password, tableName);
        this.droppingTableQuery = "DROP TABLE IF EXISTS " + tableName;
        this.creatingTableQuery = getQueryToCreateTable();
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

    public List<PowerPlant> getAllPowerPlants() {
        return powerPlantReader.selectAll();
    }

    public void createTableAndDropIfExist() {
        dropTableIfExist();
        messageInfo = new StringBuilder();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(creatingTableQuery);
            messageInfo.append(" Power plant table was created. Query= ").append(creatingTableQuery);
            LOGGER.debug(messageInfo);
        } catch (SQLException e) {
            messageInfo.append(e.getMessage());
            LOGGER.error(messageInfo);
            messageInfo.append(" Failed Query= ").append(creatingTableQuery);
            LOGGER.debug(messageInfo);
        }
    }

    private void dropTableIfExist() {
        messageInfo = new StringBuilder();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(droppingTableQuery);

            messageInfo.append(" Power plant table was dropped. Query= ").append(droppingTableQuery);
            LOGGER.debug(messageInfo);
        } catch (SQLException e) {
            messageInfo.append(e.getMessage());
            LOGGER.error(messageInfo);
            messageInfo.append(" Failed Query= ").append(droppingTableQuery);
            LOGGER.debug(messageInfo);
        }
    }

    public void insertAllPlantsInNewTable(List<PowerPlant> powerPlants) {
        createTableAndDropIfExist();

        powerPlants.forEach(powerPlantWriter::insertToDb);
    }

    private String getQueryToCreateTable() {
        return "CREATE TABLE " + tableName +
                " (" + ID.getName() + " varchar(36) not null, " +
                TYPE.getName() + " text not null, " +
                COUNTRY.getName() + " varchar(50), " +
                NUMBER_OF_EMPLOYEE.getName() + " integer, " +
                IS_WORKING.getName() + " boolean not null, " +
                MAX_POWER.getName() + " double precision not null, " +
                RESOURCE_CONSUMPTION.getName() + " integer not null, " +
                RESOURCE_AMOUNT.getName() + " integer not null, " +
                "PRIMARY KEY (" + ID.getName() + "))";
    }
}