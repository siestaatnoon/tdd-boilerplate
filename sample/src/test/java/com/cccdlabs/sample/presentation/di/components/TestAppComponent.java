package com.cccdlabs.sample.presentation.di.components;

import com.cccdlabs.sample.data.storage.database.AppDatabase;
import com.cccdlabs.sample.presentation.di.modules.TestAppModule;
import com.cccdlabs.sample.presentation.di.modules.TestDataModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestAppModule.class, TestDataModule.class})
public interface TestAppComponent extends SampleAppComponent {
    AppDatabase sampleAppDatabase();
}
