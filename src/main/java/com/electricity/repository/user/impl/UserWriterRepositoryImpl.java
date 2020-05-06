package com.electricity.repository.user.impl;

import com.electricity.enumeration.TableName;
import com.electricity.enumeration.UserColumnName;
import com.electricity.model.user.User;
import com.electricity.repository.user.UserWriterRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UserWriterRepositoryImpl implements UserWriterRepository {
    private static final Logger LOGGER = LogManager.getLogger(UserWriterRepositoryImpl.class);
    private static final String COMMA = ", ";
    private static final String QUOTE = "'";
    private static final String CLOSING_BRACKET = ")";
    private static final String OPENING_BRACKET = "(";
    private static final String VALUES = " VALUES(";
    private static final String INSERT_INTO = "INSERT INTO ";
    private final String url;
    private final String username;
    private final String password;
    private final String beginningOfQuery;

    public UserWriterRepositoryImpl(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.beginningOfQuery = getBeginningOfQuery();
    }

    private String getBeginningOfQuery() {
        return INSERT_INTO + TableName.USER.getName() + OPENING_BRACKET +
                UserColumnName.ID.getName() + COMMA +
                UserColumnName.FIRST_NAME.getName() + COMMA +
                UserColumnName.LAST_NAME.getName() + COMMA +
                UserColumnName.CURRENT_FUNDS_AMOUNT.getName() + CLOSING_BRACKET + VALUES;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public void insert(User user) {
        StringBuilder messageInfo = new StringBuilder();
        if (user != null) {
            StringBuilder sql = getFinalQuery(user);

            try (Connection connection = getConnection();
                 Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql.toString());
                messageInfo.append(" User was inserted to Table. ID=").append(user.getId());
                LOGGER.debug(messageInfo);
            } catch (SQLException e) {
                messageInfo.append(e.getMessage());
                LOGGER.error(messageInfo);
                messageInfo.append(" Check user with id:").append(user.getId());
                LOGGER.debug(messageInfo);
            }
        } else {
            LOGGER.debug(" User cannot be inserted because user = null");
        }
    }

    private StringBuilder getFinalQuery(User user) {
        StringBuilder sql = new StringBuilder(beginningOfQuery);

        return sql
                .append(QUOTE).append(user.getId()).append(QUOTE).append(COMMA)
                .append(QUOTE).append(user.getFirstName()).append(QUOTE).append(COMMA)
                .append(QUOTE).append(user.getLastName()).append(QUOTE).append(COMMA)
                .append(QUOTE).append(user.getCurrentFundsAmount()).append(QUOTE).append(CLOSING_BRACKET);
    }
}