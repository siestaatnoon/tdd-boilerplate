package com.cccdlabs.sample;

import com.cccdlabs.sample.presentation.di.components.DaggerTestAppComponent;
import com.cccdlabs.sample.presentation.di.components.TestAppComponent;
import com.cccdlabs.sample.presentation.di.modules.TestAppModule;
import com.cccdlabs.sample.presentation.di.modules.TestDataModule;
import com.cccdlabs.tddboilerplate.App;

import org.robolectric.TestLifecycleApplication;

import java.lang.reflect.Method;

public class TestApp extends App implements TestLifecycleApplication {

    private TestAppComponent appComponent;

    @Override
    public void onCreate() {
        //super.onCreate();
        appComponent = DaggerTestAppComponent.builder()
                .testAppModule(new TestAppModule(this))
                .testDataModule(new TestDataModule(this))
                .build();
    }

    public TestAppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void beforeTest(Method method) {}

    @Override
    public void prepareTest(Object test) {}

    @Override
    public void afterTest(Method method) {}
}
