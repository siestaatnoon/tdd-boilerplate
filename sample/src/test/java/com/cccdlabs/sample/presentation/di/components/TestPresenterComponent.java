package com.cccdlabs.sample.presentation.di.components;

import com.cccdlabs.sample.domain.interactors.SampleDisplayUseCase;
import com.cccdlabs.sample.presentation.di.modules.TestPresenterModule;
import com.cccdlabs.sample.presentation.presenters.MainPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = TestPresenterModule.class)
public interface TestPresenterComponent {
    void inject(MainPresenter presenter);
    SampleDisplayUseCase sampleDisplayUseCase();
}
