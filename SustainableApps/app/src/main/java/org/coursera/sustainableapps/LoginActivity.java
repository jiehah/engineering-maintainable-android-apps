package org.coursera.sustainableapps;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;  // EditText for user to enter their email
    private EditText passwordEditText;  // EditText for user to enter their password
    private TextView errorMessageTextView;  // TextView to display error messages
    private TextView successMessageTextView;  // TextView to display success message
    private Button loginButton;  // Button for user to login

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        errorMessageTextView = findViewById(R.id.errorMessageTextView);
        successMessageTextView = findViewById(R.id.successMessageTextView);
        loginButton = findViewById(R.id.loginButton);

        // Set a click listener on the login button to trigger login()
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    /**
     * Method to handle the login logic when the login button is clicked.
     * It validates the user's email and password and displays appropriate messages.
     */
    private void login() {
        // Get the user-entered email and password from EditTexts
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Check if email or password is empty and show an error message
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            showError("Please enter both email and password.");
        }
        // Validate the password and show an error message accordingly
        else {
            PasswordValidationResult passwordResult = LoginValidator.validatePassword(password);
            switch (passwordResult) {
                case EMPTY_CONTAINS_ONLY_SPACES:
                    showError("The provided password is invalid.");
                    break;
                case TOO_SHORT:
                    showError("The provided password is too short (minimum 8 characters required).");
                    break;
                case VALID:
                    // Check if the email is in a valid format and show an error message if not
                    if (!LoginValidator.isValidEmail(email)) {
                        showError("Invalid email address.");
                    } else {
                        showSuccess("Login success");
                    }
                    break;
            }
        }
    }

    /**
     * Method to validate if the email is in a valid format.
     *
     * @param email The email address to be validated.
     * @return True if the email is in a valid format, false otherwise.
     */
    public boolean isValidEmail(String email) {
        return email.contains("@") && email.indexOf("@") > 0
                && email.indexOf(".") > email.indexOf("@") + 1
                && email.indexOf(".") < email.length() - 1;
    }

    /**
     * Method to show an error message and hide the success message.
     *
     * @param errorMessage The error message to be displayed.
     */
    public void showError(String errorMessage) {
        errorMessageTextView.setVisibility(View.VISIBLE);
        successMessageTextView.setVisibility(View.GONE);
        errorMessageTextView.setText(errorMessage);
    }

    /**
     * Method to show a success message and hide the error message.
     *
     * @param successMessage The success message to be displayed.
     */
    public void showSuccess(String successMessage) {
        successMessageTextView.setVisibility(View.VISIBLE);
        errorMessageTextView.setVisibility(View.GONE);
        successMessageTextView.setText(successMessage);
    }
}
