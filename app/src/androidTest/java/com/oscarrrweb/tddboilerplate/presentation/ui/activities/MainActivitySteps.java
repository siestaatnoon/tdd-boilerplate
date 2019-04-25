package com.oscarrrweb.tddboilerplate.presentation.ui.activities;

import android.app.Activity;
import android.content.Intent;

import com.oscarrrweb.tddboilerplate.R;
import com.oscarrrweb.tddboilerplate.presentation.ui.activities.MainActivity;

import org.junit.Rule;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.Matchers.allOf;


public class MainActivitySteps {

    @Rule
    ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private Activity activity;

    @Before
    public void setUp() throws Exception {
        activityTestRule.launchActivity(new Intent());
        activity = activityTestRule.getActivity();
    }

    @Given("^I start the application$")
    public void iStartTheApplication() throws Exception {
        assertNotNull("activity is null", activity);
    }

    @And("^I see the Gizmos list is not empty$")
    public void iSeeTheGizmosListIsNotEmpty() throws Exception {
        onView(withId(R.id.gizmo_list)).check(matches(isDisplayed()));
    }

    @And("^I click on the first Gizmo$")
    public void iClickOnTheFirstGizmo() throws Exception {
        onView(withId(R.id.gizmo_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Then("^I should see a list of Widgets$")
    public void iShouldSeeAListOfWidgets() throws Exception {
        onView(allOf(withId(R.id.widget_items), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))).check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception {
        activityTestRule.finishActivity();
    }
}
