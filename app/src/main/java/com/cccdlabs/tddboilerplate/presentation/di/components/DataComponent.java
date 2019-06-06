package com.cccdlabs.tddboilerplate.presentation.di.components;

import com.cccdlabs.tddboilerplate.data.storage.database.AppDatabase;
import com.cccdlabs.tddboilerplate.presentation.di.modules.DataModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = DataModule.class)
public interface DataComponent {
    AppDatabase appDatabase();
}
