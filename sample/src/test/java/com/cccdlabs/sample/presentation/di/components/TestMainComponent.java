package com.cccdlabs.sample.presentation.di.components;

import com.cccdlabs.sample.presentation.di.modules.TestMainModule;
import com.cccdlabs.sample.presentation.presenters.MainPresenter;
import com.cccdlabs.sample.presentation.ui.adapters.SampleAdapter;
import com.cccdlabs.tddboilerplate.presentation.di.PerActivity;
import com.cccdlabs.tddboilerplate.presentation.di.modules.ActivityModule;

import dagger.Component;

@PerActivity
@Component(
        dependencies = {
                TestAppComponent.class
        },
        modules = {
                ActivityModule.class,
                TestMainModule.class
        }
)
public interface TestMainComponent extends MainComponent {

    MainPresenter mainPresenter();

    SampleAdapter sampleAdapter();
}
