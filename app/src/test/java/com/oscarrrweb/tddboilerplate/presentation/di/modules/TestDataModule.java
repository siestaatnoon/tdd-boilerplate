package com.oscarrrweb.tddboilerplate.presentation.di.modules;

import android.content.Context;

import com.oscarrrweb.tddboilerplate.data.storage.database.AppDatabase;

import dagger.Module;

@Module
public class TestDataModule extends DataModule {

    public TestDataModule(Context context) {
        super(context, null, true, false);
    }

    public TestDataModule(Context context, boolean refreshInstance) {
        super(context, null, true, refreshInstance);
    }

    public TestDataModule(Context context, String apiBaseUrl, boolean refreshInstance) {
        super(context, apiBaseUrl, true, refreshInstance);
    }
}
