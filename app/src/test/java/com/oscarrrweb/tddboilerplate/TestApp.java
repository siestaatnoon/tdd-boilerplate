package com.oscarrrweb.tddboilerplate;

import com.oscarrrweb.tddboilerplate.presentation.di.components.DaggerTestAppComponent;
import com.oscarrrweb.tddboilerplate.presentation.di.components.TestAppComponent;
import com.oscarrrweb.tddboilerplate.presentation.di.modules.TestAppModule;
import com.oscarrrweb.tddboilerplate.presentation.di.modules.TestDataModule;

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
