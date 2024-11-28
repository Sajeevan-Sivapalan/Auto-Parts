package com.example.automate;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertEquals;

import android.content.Intent;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class IntroActivityTest {

    @Rule
    public ActivityTestRule<IntroActivity> activityRule = new ActivityTestRule<>(IntroActivity.class);

    @Before
    public void setup() {
        // Launch IntroActivity before each test
        activityRule.launchActivity(new Intent());
    }

    @Test
    public void testIntroActivityDisplayed() {
        // Verify that the Intro Activity UI is displayed
        onView(withId(R.id.btn_get_started)).check(matches(isDisplayed()));
    }

    @Test
    public void testNightModeEnabled() {
        // Check if night mode is enabled
        int currentNightMode = AppCompatDelegate.getDefaultNightMode();
        assertEquals(AppCompatDelegate.MODE_NIGHT_YES, currentNightMode);
    }
}
