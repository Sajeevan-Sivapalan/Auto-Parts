package com.example.automate;

import android.content.Intent;
import android.content.res.Configuration;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CartActivityTest {

    @Rule
    public ActivityScenarioRule<CartActivity> activityRule = new ActivityScenarioRule<>(CartActivity.class);

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
        onView(withId(R.id.totalAmountTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.checkoutButton)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonBack)).check(matches(isDisplayed()));
        onView(withId(R.id.cartRecyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void testBackButtonFunctionality() {
        onView(withId(R.id.buttonBack)).perform(click());
    }

    @Test
    public void testCheckoutButtonRedirectsToCheckout() {
        onView(withId(R.id.checkoutButton)).perform(click());
        intended(hasComponent(CheckoutActivity.class.getName()));
    }

    @Test
    public void testTotalAmountDisplay() {
        double expectedTotal = 49.99 + 19.99 + 9.99; // Sum of the product prices
        onView(withId(R.id.totalAmountTextView))
                .check(matches(withText(String.format("Total: LKR %.2f", expectedTotal))));
    }

    @Test
    public void testRecyclerViewItemsDisplay() {
        onView(withId(R.id.cartRecyclerView)).check(matches(isDisplayed()));
        // Check if specific items are displayed in the RecyclerView
        onView(withText("Brake Pads")).check(matches(isDisplayed()));
        onView(withText("Oil Filter")).check(matches(isDisplayed()));
        onView(withText("Spark Plugs")).check(matches(isDisplayed()));
    }


    @After
    public void tearDown() {
        Intents.release();
    }
}
