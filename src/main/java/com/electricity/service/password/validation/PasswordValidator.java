package com.electricity.service.password.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
    private static final PasswordValidator INSTANCE = new PasswordValidator();
    private static String pattern;

    private PasswordValidator() {
    }

    public static PasswordValidator buildValidator(boolean forceSpecialChar, boolean forceCapitalLetter,
                                                   boolean forceNumber, int minLength, int maxLength) {
        StringBuilder patternBuilder = new StringBuilder("((?=.*[a-z])");

        if (forceSpecialChar) {
            patternBuilder.append("(?=.*[@#$%])");
        }

        if (forceCapitalLetter) {
            patternBuilder.append("(?=.*[A-Z])");
        }

        if (forceNumber) {
            patternBuilder.append("(?=.*\\d)");
        }

        patternBuilder.append(".{").append(minLength).append(",").append(maxLength).append("})");
        pattern = patternBuilder.toString();

        return INSTANCE;
    }

    public boolean validatePassword(final String password) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(password);
        return m.matches();
    }
}