package com.cccdlabs.tddboilerplate.presentation.di.modules;

import android.content.Context;

import com.cccdlabs.tddboilerplate.App;
import com.cccdlabs.tddboilerplate.data.executor.IOThread;
import com.cccdlabs.tddboilerplate.data.executor.ProcessThread;
import com.cccdlabs.tddboilerplate.domain.executor.ComputationThread;
import com.cccdlabs.tddboilerplate.domain.executor.ExecutorThread;
import com.cccdlabs.tddboilerplate.domain.executor.MainThread;
import com.cccdlabs.tddboilerplate.presentation.executor.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides @Singleton Context provideApplicationContext() {
        return application;
    }

    @Provides @Singleton
    MainThread provideMainThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides @Singleton
    ExecutorThread provideExecutorThread(IOThread ioThread) {
        return ioThread;
    }

    @Provides @Singleton
    ComputationThread provideComputationThread(ProcessThread processThread) {
        return processThread;
    }
}
