package com.oscarrrweb.tddboilerplate.presentation.di.components;

import com.oscarrrweb.tddboilerplate.presentation.di.Repository;
import com.oscarrrweb.tddboilerplate.presentation.di.modules.TestDataModule;

import dagger.Component;

@Repository
@Component(dependencies = TestApplicationComponent.class, modules = TestDataModule.class)
public interface TestDataComponent extends DataComponent {}
