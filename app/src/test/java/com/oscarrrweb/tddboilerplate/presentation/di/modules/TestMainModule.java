package com.oscarrrweb.tddboilerplate.presentation.di.modules;

import com.oscarrrweb.tddboilerplate.domain.interactors.sample.SampleDisplayUseCase;
import com.oscarrrweb.tddboilerplate.presentation.di.PerActivity;
import com.oscarrrweb.tddboilerplate.presentation.presenters.MainPresenter;
import com.oscarrrweb.tddboilerplate.presentation.ui.adapters.SampleAdapter;

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
