package com.cccdlabs.tddboilerplate.presentation.di.components;

import com.cccdlabs.tddboilerplate.presentation.di.PerActivity;
import com.cccdlabs.tddboilerplate.presentation.di.modules.TestActivityModule;

import dagger.Component;

@PerActivity
@Component(dependencies = TestAppComponent.class, modules = TestActivityModule.class)
public interface TestActivityComponent extends ActivityComponent {}
