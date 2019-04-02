package com.oscarrrweb.tddboilerplate.presentation.di.components;

import com.oscarrrweb.tddboilerplate.presentation.di.modules.TestAppModule;
import com.oscarrrweb.tddboilerplate.presentation.di.modules.TestDataModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestAppModule.class, TestDataModule.class})
public interface TestAppComponent extends AppComponent {}
