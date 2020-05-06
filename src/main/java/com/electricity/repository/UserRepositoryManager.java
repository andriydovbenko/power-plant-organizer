package com.electricity.repository;

import com.electricity.enumeration.PowerPlantColumnName;
import com.electricity.enumeration.TableName;
import com.electricity.enumeration.UserColumnName;
import com.electricity.model.user.User;
import com.electricity.repository.jdbc.JdbcDataConnectionLoaderImpl;
import com.electricity.repository.user.UserReaderRepository;
import com.electricity.repository.user.UserWriterRepository;
import com.electricity.repository.user.impl.UserReaderRepositoryImpl;
import com.electricity.repository.user.impl.UserWriterRepositoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UserRepositoryManager {
    private static final Logger LOGGER = LogManager.getLogger(UserRepositoryManager.class);
    private final UserReaderRepository readerRepository;
    private final UserWriterRepository writerRepository;
    private final String droppingTableQuery;
    private final String creatingTableQuery;
    private final String url;
    private final String username;
    private final String password;
    private StringBuilder messageInfo;

    public UserRepositoryManager() {
        JdbcDataConnectionLoaderImpl loader = JdbcDataConnectionLoaderImpl.getInstance();
        this.url = loader.getUrl();
        this.username = loader.getUsername();
        this.password = loader.getPassword();
        this.readerRepository = new UserReaderRepositoryImpl(url, username, password);
        this.writerRepository = new UserWriterRepositoryImpl(url, username, password);
        this.droppingTableQuery = "DROP TABLE IF EXISTS " + TableName.USER.getName();
        this.creatingTableQuery = getCreatingTableQuery();
    }

    private String getCreatingTableQuery() {
        return "CREATE TABLE " + TableName.USER.getName() +
                "(" + UserColumnName.ID.getName() + " varchar(36) not null, " +
                UserColumnName.FIRST_NAME.getName() + " varchar(80), " +
                UserColumnName.LAST_NAME.getName() + " varchar(80), " +
                UserColumnName.CURRENT_FUNDS_AMOUNT.getName() + " DECIMAL, " +
                "PRIMARY KEY ( " + PowerPlantColumnName.ID.getName() + " ))";
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public User getUser() {
        return readerRepository.select();
    }

    public void insertUser(User user) {
        createTableAndDropIfExist();

        writerRepository.insert(user);
    }

    public void createTableAndDropIfExist() {
        messageInfo = new StringBuilder();
        dropTableIfExist();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(creatingTableQuery);
            messageInfo.append(" Table was successfully created. Query: ").append(creatingTableQuery);
            LOGGER.debug(messageInfo);
        } catch (SQLException e) {
            messageInfo.append(e.getMessage());
            LOGGER.error(messageInfo);
            messageInfo.append(" Query was failed: ").append(creatingTableQuery);
            LOGGER.debug(messageInfo);
        }
    }

    private void dropTableIfExist() {
        messageInfo = new StringBuilder();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(droppingTableQuery);
            messageInfo.append(" Table was successfully dropped. Query:  ").append(droppingTableQuery);
            LOGGER.debug(messageInfo);
        } catch (SQLException e) {
            messageInfo.append(e.getMessage());
            LOGGER.error(messageInfo);
            messageInfo.append(" Query was failed: ").append(creatingTableQuery);
            LOGGER.debug(messageInfo);
        }
    }
}