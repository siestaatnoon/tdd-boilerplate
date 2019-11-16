package com.cccdlabs.sample.presentation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.test.core.app.ApplicationProvider;

import com.cccdlabs.sample.R;
import com.cccdlabs.sample.TestApp;
import com.cccdlabs.sample.presentation.di.components.DaggerTestAppComponent;
import com.cccdlabs.sample.presentation.di.components.DaggerTestMainComponent;
import com.cccdlabs.sample.presentation.di.components.TestAppComponent;
import com.cccdlabs.sample.presentation.di.components.TestMainComponent;
import com.cccdlabs.sample.presentation.di.modules.TestAppModule;
import com.cccdlabs.sample.presentation.di.modules.TestDataModule;
import com.cccdlabs.sample.presentation.model.GizmoUiModel;
import com.cccdlabs.sample.presentation.presenters.MainPresenter;
import com.cccdlabs.sample.presentation.ui.adapters.SampleAdapter;
import com.cccdlabs.sample.presentation.ui.base.ActivityTest;
import com.cccdlabs.tddboilerplate.presentation.di.modules.ActivityModule;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.shadows.ShadowToast;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(sdk=28, application= TestApp.class)
public class MainActivityTest extends ActivityTest<MainActivity> {

    private static final String SAMPLE_MESSAGE = "Something has occurred";
    private static final int FAKE_ID = 2;

    private TestAppComponent appComponent;
    private MainActivity mainActivity;
    private ShadowActivity mainActivityShadow;
    private MainPresenter mockMainPresenter;
    private SampleAdapter mockSampleAdapter;
    private Menu menu;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        appComponent = DaggerTestAppComponent.builder()
                .testAppModule(new TestAppModule(context))
                .testDataModule(new TestDataModule(context))
                .build();
        mainActivity = (MainActivity) createWithIntent(null);
        TestMainComponent mainComponent = DaggerTestMainComponent.builder()
                .testAppComponent(appComponent)
                .activityModule(new ActivityModule(mainActivity))
                .build();
        mainComponent.inject(mainActivity);
        mockMainPresenter = mainComponent.mainPresenter();
        mainComponent.inject(mockMainPresenter);
        mainActivityShadow = shadowOf(mainActivity);
        menu = mainActivityShadow.getOptionsMenu();
        mockSampleAdapter = mainComponent.sampleAdapter();
    }

    @After
    public void tearDown() {
        mainActivity.finish();
        mainActivity = null;
        mainActivityShadow = null;
    }

    @Test
    public void MainActivity_componentsInitialized() {
        assertNotNull(mainActivity);
        assertNotNull(menu);

        getController().pause().resume();
        verify(mockMainPresenter).resume();
    }

    @Test
    public void MainActivity_testAboutMenuItem() {
        MenuItem item = menu.findItem(R.id.action_about);
        assertNotNull(item);

        menu.performIdentifierAction(R.id.action_about, 0);
        Intent startedIntent = mainActivityShadow.getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(AboutActivity.class.getName(), shadowIntent.getIntentClass().getName());
    }

    @Test
    public void MainActivity_testSettingsMenuItem() {
        MenuItem item = menu.findItem(R.id.action_settings);
        assertNotNull(item);

        menu.performIdentifierAction(R.id.action_settings, 0);
        Intent startedIntent = mainActivityShadow.getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(SettingsActivity.class.getName(), shadowIntent.getIntentClass().getName());
    }

    @Test
    public void MainActivity_testCloseMenuItem() {
        MenuItem item = menu.findItem(R.id.action_close);
        assertNotNull(item);
        menu.performIdentifierAction(R.id.action_close, 0);
        assertTrue(mainActivity.isFinishing());
    }

    @Test
    public void MainActivity_testShowGizmos() {
        GizmoUiModel model = new GizmoUiModel();
        model.setId(FAKE_ID);
        List<GizmoUiModel> list = new ArrayList<>();
        list.add(model);

        mainActivity.showGizmos(list);
        verify(mockSampleAdapter).addItems(list);
    }

    @Test
    public void MainActivity_testShowLoading() {
        mainActivity.showLoading();
        verifyZeroInteractions(mockMainPresenter);
    }

    @Test
    public void MainActivity_testHideLoading() {
        mainActivity.hideLoading();
        verifyZeroInteractions(mockMainPresenter);
    }

    @Test
    public void MainActivity_testShowRetry() {
        mainActivity.showRetry();
        verifyZeroInteractions(mockMainPresenter);
    }

    @Test
    public void MainActivity_testHideRetry() {
        mainActivity.hideRetry();
        verifyZeroInteractions(mockMainPresenter);
    }

    @Test
    public void MainActivity_testShowError() {
        mainActivity.showError(SAMPLE_MESSAGE);
        assertEquals(SAMPLE_MESSAGE, ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void MainActivity_testGetContext() {
        assertEquals(appComponent.context(), mainActivity.context());
    }

    @Test
    public void MainActivity_testShowMessage() {
        mainActivity.showToast(SAMPLE_MESSAGE);
        assertEquals(SAMPLE_MESSAGE, ShadowToast.getTextOfLatestToast());

        // Test empty message does not show Toast
        mainActivity.showToast("");
        assertEquals(1, ShadowToast.shownToastCount());
    }
}
