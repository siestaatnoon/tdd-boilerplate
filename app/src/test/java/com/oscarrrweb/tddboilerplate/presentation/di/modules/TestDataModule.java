package com.oscarrrweb.tddboilerplate.presentation.di.modules;

import android.content.Context;

import com.oscarrrweb.tddboilerplate.data.storage.database.AppDatabase;

import dagger.Module;

@Module
public class TestDataModule extends DataModule {

    public TestDataModule(Context context) {
        super(context);
        database = AppDatabase.getInstance(context, true, true);
    }
}
