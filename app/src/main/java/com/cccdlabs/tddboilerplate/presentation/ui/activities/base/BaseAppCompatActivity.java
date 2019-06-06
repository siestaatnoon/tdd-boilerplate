package com.cccdlabs.tddboilerplate.presentation.ui.activities.base;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.cccdlabs.tddboilerplate.App;
import com.cccdlabs.tddboilerplate.presentation.di.components.AppComponent;
import com.cccdlabs.tddboilerplate.presentation.di.modules.ActivityModule;

/**
 * Activity abstraction that extends {@link AppCompatActivity} and performs the dependency
 * injection for it. Also methods to retrieve the App DI component (for {@link android.content.Context}
 * and DI module.
 *
 * @author Johnny Spence
 * @version 1.0.0
 */
abstract public class BaseAppCompatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getAppComponent().inject(this);
    }

    protected AppComponent getAppComponent() {
        return ((App) getApplication()).getAppComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
