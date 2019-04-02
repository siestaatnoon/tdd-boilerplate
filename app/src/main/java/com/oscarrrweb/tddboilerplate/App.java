package com.oscarrrweb.tddboilerplate;

import android.app.Application;

import com.oscarrrweb.tddboilerplate.presentation.di.components.AppComponent;
import com.oscarrrweb.tddboilerplate.presentation.di.components.DaggerAppComponent;
import com.oscarrrweb.tddboilerplate.presentation.di.modules.AppModule;
import com.oscarrrweb.tddboilerplate.presentation.di.modules.DataModule;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

public class App extends Application {

    private AppComponent applicationComponent;

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

        applicationComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dataModule(new DataModule(this))
                .build();
    }

    public AppComponent getApplicationComponent() {
        return applicationComponent;
    }
}
