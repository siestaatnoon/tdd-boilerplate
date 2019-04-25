package com.oscarrrweb.tddboilerplate.presentation.di.modules;

import android.app.Activity;

import dagger.Module;

@Module
public class TestActivityModule extends ActivityModule {

    public TestActivityModule(Activity activity) {
        super(activity);
    }
}
