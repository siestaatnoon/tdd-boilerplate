package com.oscarrrweb.tddboilerplate.presentation.di.components;

import com.oscarrrweb.tddboilerplate.presentation.di.PerActivity;
import com.oscarrrweb.tddboilerplate.presentation.di.modules.TestActivityModule;

import dagger.Component;

@PerActivity
@Component(dependencies = TestAppComponent.class, modules = TestActivityModule.class)
public interface TestActivityComponent extends ActivityComponent {}
