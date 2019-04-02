package com.oscarrrweb.tddboilerplate.presentation.di.components;

import com.oscarrrweb.tddboilerplate.presentation.di.modules.TestApplicationModule;
import com.oscarrrweb.tddboilerplate.presentation.di.modules.TestDataModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestApplicationModule.class, TestDataModule.class})
public interface TestApplicationComponent extends ApplicationComponent {}
