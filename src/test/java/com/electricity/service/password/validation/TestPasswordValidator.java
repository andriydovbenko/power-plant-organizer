package com.electricity.service.password.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPasswordValidator {
    @Test
    public void testNormalPassword() {
        PasswordValidator validator = PasswordValidator.buildValidator(
                false, false, false, 6, 16);

        assertTrue(validator.validatePassword("longpassword"));
        assertTrue(validator.validatePassword("somepas"));
        assertTrue(validator.validatePassword("aaa123489"));

        assertFalse(validator.validatePassword("short"));
    }

    @Test
    public void testForceNumeric() {
        PasswordValidator validator = PasswordValidator.buildValidator(
                false, false, true, 6, 16);

        assertTrue(validator.validatePassword("withnumeber1"));
        assertTrue(validator.validatePassword("2withnumber"));
        assertTrue(validator.validatePassword("withn3umber"));

        assertFalse(validator.validatePassword("withoutnumber"));
    }

    @Test
    public void testForceCapitalLetter() {
        PasswordValidator validator = PasswordValidator.buildValidator(
                false, true, false, 6, 16);

        assertTrue(validator.validatePassword("withcapitalA"));
        assertTrue(validator.validatePassword("Awithcapital"));
        assertTrue(validator.validatePassword("withAcapital"));

        assertFalse(validator.validatePassword("withoutcapital"));
    }

    @Test
    public void testForceSpecialCharacter() {
        PasswordValidator validator = PasswordValidator.buildValidator(
                true, false, false, 6, 16);

        assertTrue(validator.validatePassword("with@symbols"));
        assertTrue(validator.validatePassword("@Withsympols"));
        assertTrue(validator.validatePassword("withOIsymbols@"));

        assertFalse(validator.validatePassword("withnospecial"));
    }
}