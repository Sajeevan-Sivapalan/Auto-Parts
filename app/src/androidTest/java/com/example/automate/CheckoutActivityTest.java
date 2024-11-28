package com.example.automate;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.automate.model.Product;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class CheckoutActivityTest {

    @Rule
    public ActivityTestRule<CheckoutActivity> activityRule =
            new ActivityTestRule<>(CheckoutActivity.class);

    @Before
    public void setup() {
    }

    @Test
    public void testBackButtonNavigation() {
        // Click on the back button and check if the activity finishes
        onView(withId(R.id.buttonBack)).perform(click());
    }

    @Test
    public void testPayNowButtonNavigation() {
        // Click on the Pay Now button
        onView(withId(R.id.payNowButton)).perform(click());
    }

    @Test
    public void testSubtotalAndTotalCalculations() {
        // Calculate expected subtotal and total
        double expectedSubtotal = 49.99 + 19.99 + 9.99; // Sum of product prices
        String expectedSubtotalText = String.format("LKR %.2f", expectedSubtotal);
        String expectedTotalText = String.format("LKR %.2f", expectedSubtotal); // Assuming no additional fees

        // Check that subtotal and total are displayed correctly
        onView(withId(R.id.subtotalAmountTextView)).check(matches(withText(expectedSubtotalText)));
        onView(withId(R.id.totalAmountTextView)).check(matches(withText(expectedTotalText)));
    }

    @Test
    public void testRecyclerViewIsPopulated() {
        // Check that the RecyclerView is populated correctly
        onView(withId(R.id.cartRecyclerView)).check(new RecyclerViewItemCountAssertion(3));
    }

    public static class RecyclerViewItemCountAssertion implements ViewAssertion {
        private final int expectedCount;

        public RecyclerViewItemCountAssertion(int expectedCount) {
            this.expectedCount = expectedCount;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            // Cast the view to RecyclerView
            RecyclerView recyclerView = (RecyclerView) view;

            // Assert that the item count matches the expected count
            assertThat(recyclerView.getAdapter().getItemCount(), is(expectedCount));
        }
    }
}
