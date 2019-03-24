package com.oscarrrweb.tddboilerplate.presentation.di.components;

import android.content.Context;

import com.oscarrrweb.tddboilerplate.domain.executor.ComputationThread;
import com.oscarrrweb.tddboilerplate.domain.executor.ExecutorThread;
import com.oscarrrweb.tddboilerplate.domain.executor.MainThread;
import com.oscarrrweb.tddboilerplate.presentation.di.modules.ApplicationModule;
import com.oscarrrweb.tddboilerplate.presentation.ui.activities.base.BaseAppCompatActivity;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Lazy;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(BaseAppCompatActivity activity);

    Context context();

    MainThread mainThread();

    ExecutorThread executorThread();

    Lazy<ComputationThread> computationThread();
}
