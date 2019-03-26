package com.oscarrrweb.tddboilerplate;

import com.oscarrrweb.tddboilerplate.presentation.di.components.DaggerTestApplicationComponent;
import com.oscarrrweb.tddboilerplate.presentation.di.components.TestApplicationComponent;
import com.oscarrrweb.tddboilerplate.presentation.di.modules.TestApplicationModule;

import org.robolectric.TestLifecycleApplication;

import java.lang.reflect.Method;

public class TestApp extends App implements TestLifecycleApplication {

    private TestApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        //super.onCreate();
        applicationComponent = DaggerTestApplicationComponent.builder()
                .testApplicationModule(new TestApplicationModule())
                .build();
    }

    public TestApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @Override
    public void beforeTest(Method method) {}

    @Override
    public void prepareTest(Object test) {}

    @Override
    public void afterTest(Method method) {}
}
