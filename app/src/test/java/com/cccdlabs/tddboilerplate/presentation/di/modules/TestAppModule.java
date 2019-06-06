package com.cccdlabs.tddboilerplate.presentation.di.modules;

import android.content.Context;

import com.cccdlabs.tddboilerplate.presentation.executor.TestUiThread;
import com.cccdlabs.tddboilerplate.data.executor.TestIOThread;
import com.cccdlabs.tddboilerplate.data.executor.TestProcessThread;
import com.cccdlabs.tddboilerplate.domain.executor.ComputationThread;
import com.cccdlabs.tddboilerplate.domain.executor.ExecutorThread;
import com.cccdlabs.tddboilerplate.domain.executor.MainThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TestAppModule {

    private final Context context;

    public TestAppModule(Context context) {
        this.context = context;
    }

    @Provides @Singleton Context provideApplicationContext() {
        return context;
    }

    @Provides @Singleton MainThread provideMainThread(TestUiThread uiThread) {
        return uiThread;
    }

    @Provides @Singleton ExecutorThread provideExecutionThread(TestIOThread ioThread) {
        return ioThread;
    }

    @Provides @Singleton ComputationThread provideComputationThread(TestProcessThread processThread) {
        return processThread;
    }
}
