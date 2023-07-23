package org.coursera.sustainableapps;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

public class LoginActivityEspressoTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    /**
     * Test method to verify that an error message is displayed when both email and password fields are empty.
     */
    @Test
    public void testEmptyEmailAndPassword() {
        // Click the login button without entering email and password
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());

        // Check if the error message is displayed
        Espresso.onView(ViewMatchers.withId(R.id.errorMessageTextView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Test method to verify that an error message is displayed when the password is too short.
     */
    @Test
    public void testShortPassword() {
        // Enter a valid email and a short password
        Espresso.onView(ViewMatchers.withId(R.id.emailEditText))
                .perform(ViewActions.typeText("test@example.com"));
        Espresso.onView(ViewMatchers.withId(R.id.passwordEditText))
                .perform(ViewActions.typeText("pass"));

        // Click the login button
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());

        // Check if the error message is displayed
        Espresso.onView(ViewMatchers.withId(R.id.errorMessageTextView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Test method to verify that an error message is displayed for an invalid email format.
     */
    @Test
    public void testInvalidEmailFormat() {
        // Enter an invalid email format
        Espresso.onView(ViewMatchers.withId(R.id.emailEditText))
                .perform(ViewActions.typeText("invalid_email"));
        Espresso.onView(ViewMatchers.withId(R.id.passwordEditText))
                .perform(ViewActions.typeText("password123"));

        // Click the login button
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());

        // Check if the error message is displayed
        Espresso.onView(ViewMatchers.withId(R.id.errorMessageTextView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Test method to verify that a success message is displayed for valid email and password.
     */
    @Test
    public void testValidEmailAndPassword() {
        // Enter a valid email and password
        Espresso.onView(ViewMatchers.withId(R.id.emailEditText))
                .perform(ViewActions.typeText("test@example.com"));
        Espresso.onView(ViewMatchers.withId(R.id.passwordEditText))
                .perform(ViewActions.typeText("password123"));

        // Click the login button
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());

        // Check if the success message is displayed
        Espresso.onView(ViewMatchers.withId(R.id.successMessageTextView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
