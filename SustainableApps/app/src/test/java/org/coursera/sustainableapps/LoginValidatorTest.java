package org.coursera.sustainableapps;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginValidatorTest {

    /**
     * Test the password validation with an empty password.
     * <p>
     * The result should be PasswordValidationResult.EMPTY_CONTAINS_ONLY_SPACES.
     */
    @Test
    public void testPasswordValidationEmptyPassword() {
        String password = "";
        PasswordValidationResult result = LoginValidator.validatePassword(password);
        assertEquals(PasswordValidationResult.EMPTY_CONTAINS_ONLY_SPACES, result);
    }

    /**
     * Test the password validation with a password containing only spaces.
     * <p>
     * The result should be PasswordValidationResult.EMPTY_CONTAINS_ONLY_SPACES.
     */
    @Test
    public void testPasswordValidationSpacesPassword() {
        String password = "      ";
        PasswordValidationResult result = LoginValidator.validatePassword(password);
        assertEquals(PasswordValidationResult.EMPTY_CONTAINS_ONLY_SPACES, result);
    }

    /**
     * Test the password validation with a too short password.
     * <p>
     * The result should be PasswordValidationResult.TOO_SHORT.
     */
    @Test
    public void testPasswordValidationTooShortPassword() {
        String password = "1234567"; // 7 characters, should be minimum 8
        PasswordValidationResult result = LoginValidator.validatePassword(password);
        assertEquals(PasswordValidationResult.TOO_SHORT, result);
    }

    /**
     * Test the password validation with a valid password.
     * <p>
     * The result should be PasswordValidationResult.VALID.
     */
    @Test
    public void testPasswordValidationValidPassword() {
        String password = "password123";
        PasswordValidationResult result = LoginValidator.validatePassword(password);
        assertEquals(PasswordValidationResult.VALID, result);
    }

    /**
     * Test the email validation with a valid email.
     * <p>
     * The result should be true.
     */
    @Test
    public void testIsValidEmailValidEmail() {
        String email = "test@example.com";
        boolean result = LoginValidator.isValidEmail(email);
        assertTrue(result);
    }

    /**
     * Test the email validation with an invalid email missing "@" symbol.
     * <p>
     * The result should be false.
     */
    @Test
    public void testIsValidEmailInvalidEmailMissingAtSymbol() {
        String email = "testexample.com";
        boolean result = LoginValidator.isValidEmail(email);
        assertFalse(result);
    }

    /**
     * Test the email validation with an invalid email missing "." symbol after "@".
     * <p>
     * The result should be false.
     */
    @Test
    public void testIsValidEmailInvalidEmailMissingDotAfterAtSymbol() {
        String email = "test@examplecom";
        boolean result = LoginValidator.isValidEmail(email);
        assertFalse(result);
    }

    /**
     * Test the email validation with an invalid email having "." at the end.
     * <p>
     * The result should be false.
     */
    @Test
    public void testIsValidEmailInvalidEmailDotAtTheEnd() {
        String email = "test@example.";
        boolean result = LoginValidator.isValidEmail(email);
        assertFalse(result);
    }
}

