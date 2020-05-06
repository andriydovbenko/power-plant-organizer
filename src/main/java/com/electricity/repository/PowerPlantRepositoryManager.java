package com.electricity.repository;

import com.electricity.enumeration.PowerPlantColumnName;
import com.electricity.enumeration.TableName;
import com.electricity.model.plant.PowerPlant;
import com.electricity.repository.jdbc.JdbcDataConnectionLoaderImpl;
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

public class PowerPlantRepositoryManager {
    private static final Logger LOGGER = LogManager.getLogger(PowerPlantRepositoryManager.class);
    private final PowerPlantWriterRepository powerPlantWriter;
    private final PowerPlantReaderRepository powerPlantReader;
    private final String droppingTableQuery;
    private final String creatingTableQuery;
    private final String url;
    private final String username;
    private final String password;
    private StringBuilder messageInfo;

    public PowerPlantRepositoryManager() {
        JdbcDataConnectionLoaderImpl loader = JdbcDataConnectionLoaderImpl.getInstance();
        this.url = loader.getUrl();
        this.username = loader.getUsername();
        this.password = loader.getPassword();
        this.powerPlantWriter = new PowerPlantWriterRepositoryImpl(url, username, password);
        this.powerPlantReader = new PowerPlantReaderRepositoryImpl(url, username, password);
        this.droppingTableQuery = "DROP TABLE IF EXISTS " + TableName.POWER_PLANT.getName();
        this.creatingTableQuery = getCreatingTableQuery();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
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

    public void insertAllPowerPlants(List<PowerPlant> powerPlants) {
        createTableAndDropIfExist();

        powerPlants.forEach(powerPlantWriter::insertToDb);
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

    private String getCreatingTableQuery() {
        return "CREATE TABLE " + TableName.POWER_PLANT.getName() +
                " (" + PowerPlantColumnName.ID.getName() + " varchar(36) not null, " +
                PowerPlantColumnName.TYPE.getName() + " text not null, " +
                PowerPlantColumnName.COUNTRY.getName() + " varchar(50), " +
                PowerPlantColumnName.NUMBER_OF_EMPLOYEE.getName() + " integer, " +
                PowerPlantColumnName.IS_WORKING.getName() + " boolean not null, " +
                PowerPlantColumnName.MAX_POWER.getName() + " double precision not null, " +
                PowerPlantColumnName.RESOURCE_CONSUMPTION.getName() + " integer not null, " +
                PowerPlantColumnName.RESOURCE_AMOUNT.getName() + " integer not null, " +
                "PRIMARY KEY (" + PowerPlantColumnName.ID.getName() + "))";
    }
}