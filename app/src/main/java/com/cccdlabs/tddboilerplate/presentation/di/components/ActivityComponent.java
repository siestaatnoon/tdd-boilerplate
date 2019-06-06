package com.cccdlabs.tddboilerplate.presentation.di.components;

import android.app.Activity;

import com.cccdlabs.tddboilerplate.presentation.di.PerActivity;
import com.cccdlabs.tddboilerplate.presentation.di.modules.ActivityModule;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity activity();
}
