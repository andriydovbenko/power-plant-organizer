package com.electricity.repository.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PostgresAuthenticationDataLoader {
    private static final Logger LOGGER = LogManager.getLogger(PostgresAuthenticationDataLoader.class);
    private static final String PATH_TO_AUTHENTICATION_DATA = "D:\\Data\\jdbcConnectionData.txt";
    private String url;
    private String username;
    private String password;

    private static PostgresAuthenticationDataLoader instance;

    private PostgresAuthenticationDataLoader() {
        fetchFromFile();
    }

    public static synchronized PostgresAuthenticationDataLoader getInstance() {

        if (instance == null) {
            instance = new PostgresAuthenticationDataLoader();
        }

        return instance;
    }

    private void fetchFromFile() {
        File file = new File(PATH_TO_AUTHENTICATION_DATA);

        try (Scanner scanner = new Scanner(file)) {
            this.url = scanner.nextLine();
            this.username = scanner.nextLine();
            this.password = scanner.nextLine();
        } catch (FileNotFoundException e) {
            StringBuilder messageInfo = new StringBuilder();
            messageInfo.append(" File with jdbc data connection not found or data are illegal.").append(" Check file=")
                    .append(PATH_TO_AUTHENTICATION_DATA).append(" Message: ").append(e.getMessage());
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