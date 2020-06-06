package com.electricity.repository.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;

public class PostgresAuthenticationDataLoader {
    private static final Logger LOGGER = LogManager.getLogger(PostgresAuthenticationDataLoader.class);
    private static boolean isLocalApp;
    private String url;
    private String username;
    private String password;

    private static PostgresAuthenticationDataLoader instance;

    //fetching sensitive data from PC drive or from project properties
    private PostgresAuthenticationDataLoader() {
        if (isLocalApp) {
            fetchFromFile();
        } else {
            fetchFromProjectProperties();
        }
    }

    public static PostgresAuthenticationDataLoader getInstance() {
        if (instance == null) {
            instance = new PostgresAuthenticationDataLoader();
            isLocalApp = true;
        }

        return instance;
    }

    private void fetchFromProjectProperties() {
        String propertiesPath = "db.properties";
        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(propertiesPath)).getFile());
        try  (FileReader fileReader = new FileReader(file)){
            Properties properties = new Properties();
            properties.load(fileReader);
            url = properties.getProperty("postgres.db.url");
            username = properties.getProperty("postgres.db.user");
            password = properties.getProperty("postgres.db.password");
        } catch (IOException e) {
            LOGGER.error("DB properties file didn't load properly. Try to check 'resources/db.properties'");
        }
    }

    private void fetchFromFile() {
        String pathToAuthenticationData = "D:\\Data\\jdbcConnectionData.txt";
        File file = new File(pathToAuthenticationData);

        try (Scanner scanner = new Scanner(file)) {
            this.url = scanner.nextLine();
            this.username = scanner.nextLine();
            this.password = scanner.nextLine();
        } catch (FileNotFoundException e) {
            StringBuilder messageInfo = new StringBuilder();
            messageInfo.append(" File with jdbc data connection not found or data are illegal.").append(" Check file=")
                    .append(pathToAuthenticationData).append(" Message: ").append(e.getMessage());
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