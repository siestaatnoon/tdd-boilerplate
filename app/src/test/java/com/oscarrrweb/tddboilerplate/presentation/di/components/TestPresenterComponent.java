package com.oscarrrweb.tddboilerplate.presentation.di.components;

import com.oscarrrweb.tddboilerplate.domain.interactors.sample.SampleDisplayUseCase;
import com.oscarrrweb.tddboilerplate.presentation.di.modules.TestPresenterModule;
import com.oscarrrweb.tddboilerplate.presentation.presenters.MainPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = TestPresenterModule.class)
public interface TestPresenterComponent {
    void inject(MainPresenter presenter);
    SampleDisplayUseCase sampleDisplayUseCase();
}
