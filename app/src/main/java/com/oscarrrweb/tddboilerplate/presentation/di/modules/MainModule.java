package com.oscarrrweb.tddboilerplate.presentation.di.modules;

import android.app.Activity;
import android.content.Context;

import com.oscarrrweb.tddboilerplate.data.repository.sample.GizmoRepository;
import com.oscarrrweb.tddboilerplate.domain.interactors.sample.SampleDisplayUseCase;
import com.oscarrrweb.tddboilerplate.presentation.di.PerActivity;
import com.oscarrrweb.tddboilerplate.presentation.presenters.MainPresenter;
import com.oscarrrweb.tddboilerplate.presentation.ui.adapters.SampleAdapter;
import com.oscarrrweb.tddboilerplate.presentation.views.MainView;

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
