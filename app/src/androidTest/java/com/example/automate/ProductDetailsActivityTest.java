package com.example.automate;

import android.content.Intent;
import android.view.View;
import android.widget.RatingBar;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.automate.model.CartManagerSingleton;
import com.example.automate.model.ProductCommentData;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static java.util.EnumSet.allOf;

@RunWith(AndroidJUnit4.class)
public class ProductDetailsActivityTest {

    @Before
    public void setup() {
        // Create the Intent to launch the ProductDetailsActivity
        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), ProductDetailsActivity.class);
        intent.putExtra("productId", "1");
        intent.putExtra("productName", "Product X");
        intent.putExtra("productImage", "https://example.com/image.jpg");
        intent.putExtra("categoryId", "cat1");
        intent.putExtra("description", "A sample product description.");
        intent.putExtra("price", 100.0);
        intent.putExtra("availableStock", 10);
        intent.putExtra("isActive", true);
        intent.putExtra("vendorId", "vendor1");
        intent.putExtra("createdAt", "2024-01-01");
        intent.putExtra("stockLastUpdated", "2024-01-01");
        intent.putExtra("productCategoryName", "Electronics");
        intent.putExtra("vendorName", "Vendor Co");
        intent.putExtra("averageRating", 4.5);

        // Add FLAG_ACTIVITY_NEW_TASK flag to the Intent
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Start the activity
        InstrumentationRegistry.getInstrumentation().startActivitySync(intent);
    }


    @Test
    public void testInitialization() {
        onView(withId(R.id.productNameTextView)).check(matches(withText("Product X")));
        onView(withId(R.id.productPriceTextView)).check(matches(withText("100.00")));
        onView(withId(R.id.productDescriptionTextView)).check(matches(withText("A sample product description.")));
    }

    @Test
    public void testProductImageLoading() {
        onView(withId(R.id.productImageView)).check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testBackButton() {
        onView(withId(R.id.buttonBack)).perform(click());
        // Add verification for the previous activity
    }

    @Test
    public void testAddToCart() {
        onView(withId(R.id.buttonAddToCart)).perform(click());
        onView(withId(R.id.cart_count)).check(matches(withText(String.valueOf(CartManagerSingleton.getInstance().getCartCount()))));
    }

    @Test
    public void testQuantityIncrementDecrement() {
        onView(withId(R.id.buttonPlus)).perform(click());
        onView(withId(R.id.quantityTextView)).check(matches(withText("2"))); // Assuming initial quantity is 1

        onView(withId(R.id.buttonMinus)).perform(click());
        onView(withId(R.id.quantityTextView)).check(matches(withText("1"))); // Ensure it doesn't go below 1
    }


    // 9. Verify Swipe to Refresh Functionality
    @Test
    public void testSwipeToRefresh() {
        onView(withId(R.id.swipeRefreshLayout)).perform(ViewActions.swipeDown());
        // Check that product details are reloaded (implementation depends on your refresh logic)
    }
}
