package com.oscarrrweb.tddboilerplate.presentation.di.modules;

import android.content.Context;

import com.oscarrrweb.tddboilerplate.data.executor.TestIOThread;
import com.oscarrrweb.tddboilerplate.data.executor.TestProcessThread;
import com.oscarrrweb.tddboilerplate.domain.executor.ComputationThread;
import com.oscarrrweb.tddboilerplate.domain.executor.ExecutorThread;
import com.oscarrrweb.tddboilerplate.domain.executor.MainThread;
import com.oscarrrweb.tddboilerplate.presentation.executor.TestUiThread;

import javax.inject.Singleton;

import androidx.test.core.app.ApplicationProvider;
import dagger.Module;
import dagger.Provides;

@Module
public class TestApplicationModule {

    public TestApplicationModule() {}

    @Provides @Singleton Context provideApplicationContext() {
        return ApplicationProvider.getApplicationContext();
    }

    @Provides @Singleton
    MainThread provideMainThread() {
        return new TestUiThread();
    }

    @Provides @Singleton
    ExecutorThread provideExecutionThread() {
        return new TestIOThread();
    }

    @Provides @Singleton
    ComputationThread provideComputationThread() {
        return new TestProcessThread();
    }
}
