package com.oscarrrweb.tddboilerplate.presentation.di.modules;

import android.content.Context;

import com.oscarrrweb.tddboilerplate.App;
import com.oscarrrweb.tddboilerplate.data.executor.IOThread;
import com.oscarrrweb.tddboilerplate.data.executor.ProcessThread;
import com.oscarrrweb.tddboilerplate.domain.executor.ComputationThread;
import com.oscarrrweb.tddboilerplate.domain.executor.ExecutorThread;
import com.oscarrrweb.tddboilerplate.domain.executor.MainThread;
import com.oscarrrweb.tddboilerplate.presentation.executor.UIThread;

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

    @Provides @Singleton MainThread provideMainThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides @Singleton ExecutorThread provideExecutionThread(IOThread ioThread) {
        return ioThread;
    }

    @Provides @Singleton ComputationThread provideComputationThread(ProcessThread processThread) {
        return processThread;
    }
}
