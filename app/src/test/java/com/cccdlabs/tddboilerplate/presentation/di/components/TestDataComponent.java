package com.cccdlabs.tddboilerplate.presentation.di.components;

import com.cccdlabs.tddboilerplate.presentation.di.modules.TestDataModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = TestDataModule.class)
public interface TestDataComponent extends DataComponent {}
