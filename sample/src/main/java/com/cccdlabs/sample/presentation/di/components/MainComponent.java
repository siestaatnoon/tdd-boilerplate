package com.cccdlabs.sample.presentation.di.components;

import com.cccdlabs.sample.presentation.di.modules.MainModule;
import com.cccdlabs.sample.domain.interactors.SampleDisplayUseCase;
import com.cccdlabs.sample.presentation.presenters.MainPresenter;
import com.cccdlabs.sample.presentation.ui.activities.MainActivity;
import com.cccdlabs.tddboilerplate.presentation.di.PerActivity;
import com.cccdlabs.tddboilerplate.presentation.di.components.ActivityComponent;
import com.cccdlabs.tddboilerplate.presentation.di.modules.ActivityModule;

import dagger.Component;

@PerActivity
@Component(
        dependencies = {
                SampleAppComponent.class
        },
        modules = {
                ActivityModule.class,
                MainModule.class
        }
)
public interface MainComponent extends ActivityComponent {

    SampleDisplayUseCase sampleDisplayUseCase();

    void inject(MainActivity mainActivity);

    void inject(MainPresenter mainPresenter);

    void inject(SampleDisplayUseCase useCase);
}
