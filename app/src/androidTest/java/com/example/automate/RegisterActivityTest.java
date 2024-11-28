package com.example.automate;

import android.content.Intent;
import android.content.res.Configuration;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.intent.Intents;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

import static com.example.automate.TransformationMethodMatcher.withTransformationMethod;

@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest {

    @Rule
    public ActivityScenarioRule<RegisterActivity> activityRule = new ActivityScenarioRule<>(RegisterActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @Test
    public void testDarkModeEnabled() {
        activityRule.getScenario().onActivity(activity -> {
            int currentMode = activity.getResources().getConfiguration().uiMode
                    & Configuration.UI_MODE_NIGHT_MASK;
            assertEquals(Configuration.UI_MODE_NIGHT_YES, currentMode);
        });
    }

    @Test
    public void testActivityLaunch() {
        onView(withId(R.id.btnSignup)).check(matches(isDisplayed()));
        onView(withId(R.id.text_login)).check(matches(isDisplayed()));
        onView(withId(R.id.imgPasswordVisibility)).check(matches(isDisplayed()));
        onView(withId(R.id.imgConfirmPasswordVisibility)).check(matches(isDisplayed()));
        onView(withId(R.id.edt_password)).check(matches(isDisplayed()));
        onView(withId(R.id.edt_confirm_password)).check(matches(isDisplayed()));
    }

    @Test
    public void testLoginTextIsUnderlined() {
        onView(withId(R.id.text_login)).check(matches(withText(containsString("Login"))));
    }

    @Test
    public void testPasswordVisibilityToggle() {
        onView(withId(R.id.edt_password))
                .check(matches(withTransformationMethod(PasswordTransformationMethod.class)));

        onView(withId(R.id.imgPasswordVisibility)).perform(click());
        onView(withId(R.id.edt_password))
                .check(matches(withTransformationMethod(HideReturnsTransformationMethod.class)));

        onView(withId(R.id.imgPasswordVisibility)).perform(click());
        onView(withId(R.id.edt_password))
                .check(matches(withTransformationMethod(PasswordTransformationMethod.class)));
    }

    @Test
    public void testConfirmPasswordVisibilityToggle() {
        onView(withId(R.id.imgConfirmPasswordVisibility)).perform(click());
        onView(withId(R.id.edt_confirm_password))
                .check(matches(withTransformationMethod(HideReturnsTransformationMethod.class)));

        onView(withId(R.id.imgConfirmPasswordVisibility)).perform(click());
        onView(withId(R.id.edt_confirm_password))
                .check(matches(withTransformationMethod(PasswordTransformationMethod.class)));
    }

    @Test
    public void testSignupButtonRedirectsToHome() {
        onView(withId(R.id.btnSignup)).perform(click());
        intended(hasComponent(HomeActivity.class.getName()));
    }

    @Test
    public void testLoginTextRedirectsToLogin() {
        onView(withId(R.id.text_login)).perform(click());
        intended(hasComponent(LoginActivity.class.getName()));
    }

    @Test
    public void testEdgeToEdgeDisplay() {
        onView(withId(R.id.edt_password)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.edt_confirm_password)).check(matches(isCompletelyDisplayed()));
    }

    @After
    public void tearDown() {
        Intents.release();
    }
}
