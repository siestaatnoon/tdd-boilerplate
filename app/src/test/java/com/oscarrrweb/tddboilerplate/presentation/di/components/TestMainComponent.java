package com.oscarrrweb.tddboilerplate.presentation.di.components;

import com.oscarrrweb.tddboilerplate.presentation.di.PerActivity;
import com.oscarrrweb.tddboilerplate.presentation.di.modules.TestActivityModule;
import com.oscarrrweb.tddboilerplate.presentation.di.modules.TestMainModule;

import dagger.Component;

@PerActivity
@Component(
        dependencies = {
                TestAppComponent.class
        },
        modules = {
                TestActivityModule.class,
                TestMainModule.class
        }
)
public interface TestMainComponent extends MainComponent {}
