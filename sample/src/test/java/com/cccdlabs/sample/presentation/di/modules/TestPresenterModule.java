package com.cccdlabs.sample.presentation.di.modules;

import android.content.Context;

import com.cccdlabs.sample.data.executor.TestIOThread;
import com.cccdlabs.sample.domain.interactors.SampleDisplayUseCase;
import com.cccdlabs.sample.presentation.executor.TestUiThread;
import com.cccdlabs.tddboilerplate.domain.executor.ExecutorThread;
import com.cccdlabs.tddboilerplate.domain.executor.MainThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public class TestPresenterModule {

    private final Context context;

    public TestPresenterModule(Context context) {
        this.context = context;
    }

    @Provides @Singleton Context provideApplicationContext() {
        return context;
    }

    @Provides @Singleton MainThread provideMainThread() {
        return new TestUiThread();
    }

    @Provides @Singleton ExecutorThread provideExecutorThread() {
        return new TestIOThread();
    }

    @Provides @Singleton SampleDisplayUseCase provideSampleDisplayUseCase() {
        return mock(SampleDisplayUseCase.class);
    }
}
