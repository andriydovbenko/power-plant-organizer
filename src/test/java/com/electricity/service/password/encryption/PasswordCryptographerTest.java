package com.electricity.service.password.encryption;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordCryptographerTest {
    private final String password = "mySecurePassword@12345";
    private final String encryptedPassword = "eZuHHORnYelS2hkWzg5oilvEg+ml9dZR+x5a438S7XI=";

    private final String passwordShort = "short";
    private final String encryptedPasswordShort = "10x55ta8BbZrUVaXkzoyEQ==";

    @Test
    void encrypt() {
        assertEquals(PasswordCryptographer.encrypt(password), encryptedPassword);
        assertEquals(PasswordCryptographer.encrypt(passwordShort), encryptedPasswordShort);
    }

    @Test
    void decrypt() {
        assertEquals(PasswordCryptographer.decrypt(encryptedPassword), password);
        assertEquals(PasswordCryptographer.decrypt(encryptedPasswordShort), passwordShort);
    }
}