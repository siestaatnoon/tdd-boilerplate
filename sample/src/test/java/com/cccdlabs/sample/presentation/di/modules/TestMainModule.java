package com.cccdlabs.sample.presentation.di.modules;

import com.cccdlabs.sample.domain.interactors.SampleDisplayUseCase;
import com.cccdlabs.sample.presentation.presenters.MainPresenter;
import com.cccdlabs.sample.presentation.ui.adapters.SampleAdapter;
import com.cccdlabs.tddboilerplate.presentation.di.PerActivity;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;

@Module
public class TestMainModule {

    public TestMainModule() {}

    @Provides @PerActivity SampleDisplayUseCase provideSampleDisplayUseCase() {
        return Mockito.mock(SampleDisplayUseCase.class);
    }

    @Provides @PerActivity MainPresenter provideMainPresenter() {
        return Mockito.mock(MainPresenter.class);
    }

    @Provides @PerActivity SampleAdapter provideSampleAdapter() {
        return Mockito.mock(SampleAdapter.class);
    }
}
