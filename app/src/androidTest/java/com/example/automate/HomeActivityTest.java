package com.example.automate;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.automate.model.Product;
import com.example.automate.model.CartManagerSingleton;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {

    @Rule
    public ActivityTestRule<HomeActivity> activityRule = new ActivityTestRule<>(HomeActivity.class);

    private List<Product> mockProductList;

    @Before
    public void setUp() {
        mockProductList = new ArrayList<>();
        mockProductList.add(new Product("1", "Brake Pads", "url", "category1", "Quality brake pads.", 49.99, 100, true, "vendor1", "2024-01-01", "2024-01-10", "Brakes", "AutoCorp", 4));
        mockProductList.add(new Product("2", "Oil Filter", "url", "category2", "Reliable oil filter.", 19.99, 150, true, "vendor2", "2024-01-01", "2024-01-10", "Filters", "PartsCo", 5));
    }

    @Test
    public void testNavigationDrawerMenu() {
        onView(withId(R.id.buttonDrawerToggle)).perform(click());
        onView(withId(R.id.navHome)).perform(click());
        onView(withId(R.id.recyclerViewProducts)).check(matches(withId(R.id.recyclerViewProducts)));
    }

    @Test
    public void testCartButtonNavigation() {
        onView(withId(R.id.buttonDrawerMenuRight)).perform(click());
        Intent expectedIntent = new Intent(activityRule.getActivity(), HomeActivity.class);
        assertEquals(expectedIntent.getComponent(), activityRule.getActivity().getComponentName());
    }



    @Test
    public void testSwipeRefreshResetsFilters() {
        onView(withId(R.id.swipeRefreshLayout)).perform(click());
        onView(withId(R.id.editTextMinPrice)).check(matches(withText("")));
        onView(withId(R.id.editTextMaxPrice)).check(matches(withText("")));
    }

    @Test
    public void testProductSearch() throws Throwable {
        onView(withId(R.id.searchViewProducts)).perform(typeText("Brake Pads"));

        activityRule.runOnUiThread(() -> activityRule.getActivity().applyFilters());

        onView(withId(R.id.recyclerViewProducts)).check(matches(hasDescendant(withText("Brake Pads"))));
    }


    @Test
    public void testLogoutConfirmationDialog() {
        onView(withId(R.id.buttonDrawerToggle)).perform(click());
        onView(withId(R.id.navLogout)).perform(click());
        onView(withText("Are you sure you want to log out?")).check(matches(withText("Are you sure you want to log out?")));
    }
}
