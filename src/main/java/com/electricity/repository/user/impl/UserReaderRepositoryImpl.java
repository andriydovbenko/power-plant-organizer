package com.electricity.repository.user.impl;

import com.electricity.exeption.UnknownPowerPlantTypeException;
import com.electricity.model.user.User;
import com.electricity.repository.user.UserReaderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.electricity.enumeration.TableName.USER;
import static com.electricity.enumeration.UserColumnName.*;

public class UserReaderRepositoryImpl implements UserReaderRepository {
    private static final Logger LOGGER = LogManager.getLogger(UserReaderRepositoryImpl.class);
    private final String url;
    private final String username;
    private final String password;
    private final String sql;

    public UserReaderRepositoryImpl(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.sql = "SELECT * FROM " + USER.getName();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public User selectByLogin(String login) {
        User user = null;
        String query = sql + " WHERE " + LOGIN.getName() +
                "='" + login + "';";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                user = getUserFromTable(resultSet);

                LOGGER.debug("User was successfully download from db");
            }
        } catch (SQLException | UnknownPowerPlantTypeException e) {
            StringBuilder messageInfo = new StringBuilder(e.getMessage());
            LOGGER.error(messageInfo);
            messageInfo.append(" Failed Query =").append(sql);
            LOGGER.debug(messageInfo);
        }
        return user;
    }

    @Override
    public List<User> selectAll() {
        List<User> users = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                users.add(getUserFromTable(resultSet));

                LOGGER.debug("User was successfully download from db");
            }
        } catch (SQLException | UnknownPowerPlantTypeException e) {
            StringBuilder messageInfo = new StringBuilder(e.getMessage());
            LOGGER.error(messageInfo);
            messageInfo.append(" Failed Query =").append(sql);
            LOGGER.debug(messageInfo);
        }
        return users;
    }

    private User getUserFromTable(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getString(ID.getName()));
        user.setLogin(resultSet.getString(LOGIN.getName()));
        user.setPassword(resultSet.getString(PASSWORD.getName()));
        user.setFirstName(resultSet.getString(FIRST_NAME.getName()));
        user.setLastName(resultSet.getString(LAST_NAME.getName()));
        user.setTableName(resultSet.getString(TABLE_NAME.getName()));
        user.setCurrentFundsAmount(resultSet.getBigDecimal(CURRENT_FUNDS_AMOUNT.getName()));

        return user;
    }
}