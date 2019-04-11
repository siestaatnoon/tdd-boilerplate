package com.oscarrrweb.tddboilerplate.presentation.di.components;

import com.oscarrrweb.tddboilerplate.domain.interactors.sample.SampleDisplayUseCase;
import com.oscarrrweb.tddboilerplate.presentation.di.PerActivity;
import com.oscarrrweb.tddboilerplate.presentation.di.modules.ActivityModule;
import com.oscarrrweb.tddboilerplate.presentation.di.modules.MainModule;
import com.oscarrrweb.tddboilerplate.presentation.presenters.MainPresenter;
import com.oscarrrweb.tddboilerplate.presentation.ui.activities.MainActivity;

import dagger.Component;

@PerActivity
@Component(
        dependencies = {
                AppComponent.class
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
