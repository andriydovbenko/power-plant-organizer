package com.electricity.repository;

import com.electricity.model.user.User;
import com.electricity.repository.jdbc.PostgresAuthenticationDataLoader;
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

import static com.electricity.enumeration.Driver.POSTGRES;
import static com.electricity.enumeration.TableName.USER;
import static com.electricity.enumeration.UserColumnName.*;

public class UserRepository {
    private static final Logger LOGGER = LogManager.getLogger(UserRepository.class);
    private final UserReaderRepository readerRepository;
    private final UserWriterRepository writerRepository;
    private final String droppingTableQuery;
    private final String creatingTableQuery;
    private final String url;
    private final String username;
    private final String password;
    private StringBuilder messageInfo;

    public UserRepository() {
        PostgresAuthenticationDataLoader loader = PostgresAuthenticationDataLoader.getInstance();
        this.url = loader.getUrl();
        this.username = loader.getUsername();
        this.password = loader.getPassword();
        this.readerRepository = new UserReaderRepositoryImpl(url, username, password);
        this.writerRepository = new UserWriterRepositoryImpl(url, username, password);
        this.droppingTableQuery = "DROP TABLE IF EXISTS " + USER.getName();
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

    private String getQueryToCreateTable() {
        return "CREATE TABLE " + USER.getName() +
                "(" + ID.getName() + " varchar(36) not null, " +
                LOGIN.getName() + " varchar(100) not null, " +
                PASSWORD.getName() + " varchar(100) not null, " +
                FIRST_NAME.getName() + " varchar(80), " +
                LAST_NAME.getName() + " varchar(80), " +
                TABLE_NAME.getName() + " varchar(120), " +
                CURRENT_FUNDS_AMOUNT.getName() + " DECIMAL, " +
                "PRIMARY KEY ( " + ID.getName() + " ))";
    }

    public User getUserByLogin(String login) {
        return readerRepository.selectByLogin(login);
    }

    public void insertUser(User user) {
        writerRepository.insert(user);
    }

    public void update(User user) {
        writerRepository.update(user);
    }

    public boolean isLoginFree(String login) {
        return readerRepository.selectByLogin(login) == null;
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