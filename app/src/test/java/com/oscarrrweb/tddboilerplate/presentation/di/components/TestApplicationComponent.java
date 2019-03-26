package com.oscarrrweb.tddboilerplate.presentation.di.components;

import com.oscarrrweb.tddboilerplate.presentation.di.modules.TestApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = TestApplicationModule.class)
public interface TestApplicationComponent extends ApplicationComponent {}
