package com.electricity.service.password.encryption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import static com.electricity.enumeration.attribute.AESData.*;

public class PasswordCryptographer {
    private static final Logger LOGGER = LogManager.getLogger(PasswordCryptographer.class);
    private static final int NEW_LENGTH_OF_KEY = 16;
    private static SecretKeySpec secretKey;

    private PasswordCryptographer() {
    }

    private static void setKey(String myKey) {
        MessageDigest sha;
        try {
            byte[] key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance(ALGORITHM_SHA.getInformation());
            key = sha.digest(key);
            key = Arrays.copyOf(key, NEW_LENGTH_OF_KEY);
            secretKey = new SecretKeySpec(key, ALGORITHM_AES.getInformation());
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e);
        }
    }

    public static String encrypt(String strToEncrypt) {
        try {
            setKey(SECRET.getInformation());
            Cipher cipher = Cipher.getInstance(PADDING_SCHEMA.getInformation());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            String message = "Error while encrypting: " + e.toString();
            LOGGER.error(message);
        }
        return null;
    }

    public static String decrypt(String strToDecrypt) {
        try {
            setKey(SECRET.getInformation());
            Cipher cipher = Cipher.getInstance(PADDING_SCHEMA.getInformation());
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            String message = "Error while decrypting: " + e.toString();
            LOGGER.error(message);
        }
        return null;
    }
}