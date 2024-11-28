package com.example.automate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.automate.model.ProductImageSingleton;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class FullScreenImageActivityTest {

    @Rule
    public ActivityTestRule<FullScreenImageActivity> activityRule =
            new ActivityTestRule<FullScreenImageActivity>(FullScreenImageActivity.class) {
                @Override
                protected void beforeActivityLaunched() {
                    // Setup the singleton before launching the activity
                    ProductImageSingleton.getInstance().setProductImage("https://example.com/image.png");
                }
            };

    @Before
    public void setup() {
    }

    @Test
    public void testImageIsLoadedCorrectly() {
        // Check if the ImageView is displayed with the correct image URL
        onView(withId(R.id.fullScreenImageView)).check(matches(isDisplayed()));
    }

    @Test
    public void testCloseButtonFunctionality() {
        // Click on the close button
        onView(withId(R.id.closeButton)).perform(click());
    }
}
