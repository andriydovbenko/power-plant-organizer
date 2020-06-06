package com.electricity.repository.user.impl;

import com.electricity.model.user.User;
import com.electricity.repository.user.UserWriterRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static com.electricity.enumeration.path.Driver.POSTGRES;
import static com.electricity.enumeration.repo.TableName.*;
import static com.electricity.enumeration.repo.UserColumnName.*;

public class UserWriterRepositoryImpl implements UserWriterRepository {
    private static final Logger LOGGER = LogManager.getLogger(UserWriterRepositoryImpl.class);
    private static final String COMMA = ", ";
    private static final String CLOSING_BRACKET = ")";
    private static final String OPENING_BRACKET = "(";
    private final String url;
    private final String username;
    private final String password;
    private final String firstPartOfInsertQuery;
    private StringBuilder sql;
    private StringBuilder messageInfo;

    public UserWriterRepositoryImpl(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.firstPartOfInsertQuery = getFirsPartOfInsertQuery();
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
    public void insert(User user) {
        messageInfo = new StringBuilder();
        if (user != null) {
            sql = createFinalInsertQuery(user);

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

    @Override
    public void update(User user) {
        messageInfo = new StringBuilder();
        sql = creteFinalUpdateQuery(user);

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql.toString());
            messageInfo.append(" User was updated. ID=").append(user.getId());
            LOGGER.debug(messageInfo);
        } catch (SQLException e) {
            messageInfo.append(e.getMessage()).append(sql);
            LOGGER.error(messageInfo);
            messageInfo.append(" Check user by id:").append(user.getId());
            LOGGER.debug(messageInfo);
        }
    }

    private StringBuilder creteFinalUpdateQuery(User user) {
        sql = new StringBuilder("UPDATE ");
        return sql
                .append(USER.getName()).append(" SET ")
                .append(PASSWORD.getName()).append("='").append(user.getPassword()).append("', ")
                .append(FIRST_NAME.getName()).append("='").append(user.getFirstName()).append("', ")
                .append(LAST_NAME.getName()).append("='").append(user.getLastName()).append("', ")
                .append(CURRENT_FUNDS_AMOUNT.getName()).append("='").append(user.getCurrentFundsAmount())
                .append("' WHERE ").append(ID.getName()).append("='").append(user.getId()).append("';");
    }

    private String getFirsPartOfInsertQuery() {
        return "INSERT INTO " + USER.getName() + OPENING_BRACKET +
                ID.getName() + COMMA +
                LOGIN.getName() + COMMA +
                PASSWORD.getName() + COMMA +
                FIRST_NAME.getName() + COMMA +
                LAST_NAME.getName() + COMMA +
                TABLE_NAME.getName() + COMMA +
                CURRENT_FUNDS_AMOUNT.getName() + CLOSING_BRACKET + " VALUES(";
    }

    private StringBuilder createFinalInsertQuery(User user) {
        String quote = "'";
        sql = new StringBuilder(firstPartOfInsertQuery);

        return sql
                .append(quote).append(user.getId()).append(quote).append(COMMA)
                .append(quote).append(user.getLogin()).append(quote).append(COMMA)
                .append(quote).append(user.getPassword()).append(quote).append(COMMA)
                .append(quote).append(user.getFirstName()).append(quote).append(COMMA)
                .append(quote).append(user.getLastName()).append(quote).append(COMMA)
                .append(quote).append(user.getTableName()).append(quote).append(COMMA)
                .append(quote).append(user.getCurrentFundsAmount()).append(quote).append(CLOSING_BRACKET);
    }
}