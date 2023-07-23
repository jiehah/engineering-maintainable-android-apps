package org.coursera.sustainableapps;

public class LoginValidator {

    /**
     * Method to validate the password.
     *
     * @param password The password to be validated.
     * @return A PasswordValidationResult indicating the validation result.
     */
    public static PasswordValidationResult validatePassword(String password) {
        if (password.trim().isEmpty()) {
            return PasswordValidationResult.EMPTY_CONTAINS_ONLY_SPACES;
        } else if (password.length() < 8) {
            return PasswordValidationResult.TOO_SHORT;
        } else {
            return PasswordValidationResult.VALID;
        }
    }

    /**
     * Method to validate if the email is in a valid format.
     *
     * @param email The email address to be validated.
     * @return True if the email is in a valid format, false otherwise.
     */
    public static boolean isValidEmail(String email) {
        return email.contains("@") && email.indexOf("@") > 0
                && email.indexOf(".") > email.indexOf("@") + 1
                && email.indexOf(".") < email.length() - 1;
    }
}
