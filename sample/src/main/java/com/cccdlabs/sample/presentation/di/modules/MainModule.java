package com.cccdlabs.sample.presentation.di.modules;

import android.app.Activity;
import android.content.Context;

import com.cccdlabs.sample.data.repository.GizmoRepository;
import com.cccdlabs.sample.domain.interactors.SampleDisplayUseCase;
import com.cccdlabs.sample.presentation.presenters.MainPresenter;
import com.cccdlabs.sample.presentation.ui.adapters.SampleAdapter;
import com.cccdlabs.sample.presentation.views.MainView;
import com.cccdlabs.tddboilerplate.presentation.di.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    public MainModule() {}

    @Provides @PerActivity SampleDisplayUseCase provideSampleDisplayUseCase() {
        return new SampleDisplayUseCase();
    }

    @Provides @PerActivity MainPresenter provideMainPresenter(GizmoRepository repository, Activity activity) {
        return new MainPresenter(repository, (MainView) activity);
    }

    @Provides @PerActivity SampleAdapter provideSampleAdapter(Activity activity, Context context) {
        return new SampleAdapter((MainView) activity, context);
    }
}
