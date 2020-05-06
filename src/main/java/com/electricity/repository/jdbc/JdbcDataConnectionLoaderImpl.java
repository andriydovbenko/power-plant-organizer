package com.electricity.repository.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class JdbcDataConnectionLoaderImpl {
    private static final Logger LOGGER = LogManager.getLogger(JdbcDataConnectionLoaderImpl.class);
    private static final String PATH_NAME = "jdbcConnectionData.txt";
    private String url;
    private String username;
    private String password;

    private static JdbcDataConnectionLoaderImpl instance;

    private JdbcDataConnectionLoaderImpl() {
        fetchFromFile();
    }

    public static synchronized JdbcDataConnectionLoaderImpl getInstance() {
        if (instance == null) {
            instance = new JdbcDataConnectionLoaderImpl();
        }

        return instance;
    }

    private void fetchFromFile() {
        File file = new File(PATH_NAME);
        try (Scanner scanner = new Scanner(file)) {

            url = scanner.nextLine();
            username = scanner.nextLine();
            password = scanner.nextLine();
        } catch (FileNotFoundException e) {
            StringBuilder messageInfo = new StringBuilder();
            messageInfo.append(" File with jdbc data connection not found or data are illegal.")
                    .append(" Check file=").append(PATH_NAME).append(" Message: ").append(e.getMessage());
            LOGGER.error(messageInfo);
        }
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}