package com.electricity.repository.user.impl;

import com.electricity.enumeration.TableName;
import com.electricity.enumeration.UserColumnName;
import com.electricity.exeption.UnknownPowerPlantTypeException;
import com.electricity.model.user.User;
import com.electricity.repository.user.UserReaderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

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
        this.sql = "SELECT * FROM " + TableName.USER.getName();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public User select() {
        User user = new User();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {

                user.setId(resultSet.getString(UserColumnName.ID.getName()));
                user.setFirstName(resultSet.getString(UserColumnName.FIRST_NAME.getName()));
                user.setLastName(resultSet.getString(UserColumnName.LAST_NAME.getName()));
                user.setCurrentFundsAmount(resultSet.getBigDecimal(UserColumnName.CURRENT_FUNDS_AMOUNT.getName()));

                LOGGER.debug("User was successfully download from db");
            }
        } catch (SQLException | UnknownPowerPlantTypeException e) {
            StringBuilder messageInfo = new StringBuilder(e.getMessage());
            LOGGER.error(messageInfo);
            messageInfo.append(" Filed Query =").append(sql);
            LOGGER.debug(messageInfo);
        }

        return user;
    }
}