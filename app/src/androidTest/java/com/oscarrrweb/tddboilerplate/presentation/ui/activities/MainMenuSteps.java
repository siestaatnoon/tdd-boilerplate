package com.oscarrrweb.tddboilerplate.presentation.ui.activities;

import android.app.Activity;
import android.content.Intent;

import com.oscarrrweb.tddboilerplate.R;
import com.oscarrrweb.tddboilerplate.presentation.ui.activities.AboutActivity;
import com.oscarrrweb.tddboilerplate.presentation.ui.activities.MainActivity;
import com.oscarrrweb.tddboilerplate.presentation.ui.activities.SettingsActivity;

import org.junit.Rule;

import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class MainMenuSteps {

    @Rule
    ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private Activity activity;

    @Before
    public void setUp() throws Exception {
        activityTestRule.launchActivity(null);
        Intents.init();
        activity = activityTestRule.getActivity();
    }

    @Given("^I start the application again$")
    public void iStartTheApplicationAgain() throws Exception {
        assertNotNull("activity is null", activity);
    }

    @And("^I click on the menu button$")
    public void iClickOnTheMenuButton() throws Exception {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        //onView(withId(R.menu.menu_main)).check(matches(isDisplayed()));
    }

    @And("^I select the About menu item$")
    public void iSelectTheAboutMenuItem() throws Exception {
        onView(withText(R.string.menu_action_about)).perform(click());
    }

    @Then("^I should see the About screen$")
    public void iShouldSeeTheAboutScreen() throws Exception {
        intended(hasComponent(AboutActivity.class.getName()));
    }

    @And("^I select the Settings menu item$")
    public void iSelectTheSettingsMenuItem() throws Exception {
        onView(withText(R.string.menu_action_settings)).perform(click());
    }

    @Then("^I should see the Settings screen$")
    public void iShouldSeeTheSettingsScreen() throws Exception {
        intended(hasComponent(SettingsActivity.class.getName()));
    }

    @And("^I select the Close menu item$")
    public void iSelectTheCloseMenuItem() throws Exception {
        onView(withText(R.string.menu_action_close)).perform(click());
    }

    @Then("^I should see the application close$")
    public void iShouldSeeTheApplicationClose() throws Exception {
        assertTrue(activity.isFinishing());
    }

    @After
    public void tearDown() throws Exception {
        activityTestRule.finishActivity();
        Intents.release();
    }
}
