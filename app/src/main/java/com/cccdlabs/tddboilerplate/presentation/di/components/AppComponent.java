package com.cccdlabs.tddboilerplate.presentation.di.components;

import android.content.Context;

import com.cccdlabs.tddboilerplate.data.network.retrofit.RestCaller;
import com.cccdlabs.tddboilerplate.data.storage.database.AppDatabase;
import com.cccdlabs.tddboilerplate.domain.executor.ComputationThread;
import com.cccdlabs.tddboilerplate.domain.executor.ExecutorThread;
import com.cccdlabs.tddboilerplate.domain.executor.MainThread;
import com.cccdlabs.tddboilerplate.presentation.ui.activities.base.BaseAppCompatActivity;
import com.cccdlabs.tddboilerplate.presentation.di.modules.AppModule;
import com.cccdlabs.tddboilerplate.presentation.di.modules.DataModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Lazy;

@Singleton
@Component(modules = {AppModule.class, DataModule.class})
public interface AppComponent {

    void inject(BaseAppCompatActivity activity);

    void inject(RestCaller restCaller);

    Context context();

    MainThread mainThread();

    ExecutorThread executorThread();

    Lazy<ComputationThread> computationThread();

    AppDatabase appDatabase();
}
