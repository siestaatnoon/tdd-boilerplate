package com.cccdlabs.sample.presentation.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.test.core.app.ApplicationProvider;

import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;

public class ActivityTest<T extends Activity> {

    private Class<T> activityClass;
    private ActivityController<T> controller;

    public ActivityTest(Class<T> activityClass) {
        this.activityClass = activityClass;
    }

    public Activity createWithIntent(Bundle bundle) {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), activityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }

        controller = Robolectric.buildActivity(activityClass, intent)
                .create()
                .start()
                .visible();

        return controller.get();
    }

    public ActivityController getController() {
        return controller;
    }
}
