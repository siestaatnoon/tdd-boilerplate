package com.oscarrrweb.tddboilerplate;

import android.app.Application;

import com.oscarrrweb.tddboilerplate.presentation.di.components.ApplicationComponent;
import com.oscarrrweb.tddboilerplate.presentation.di.modules.ApplicationModule;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

public class App extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        } else if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
            Timber.plant(new Timber.DebugTree());
            Timber.d("Timber DebugTree initialized");
        }

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
