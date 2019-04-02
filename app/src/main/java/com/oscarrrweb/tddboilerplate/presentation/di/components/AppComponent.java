package com.oscarrrweb.tddboilerplate.presentation.di.components;

import android.content.Context;

import com.oscarrrweb.tddboilerplate.data.mappers.sample.DoodadMapper;
import com.oscarrrweb.tddboilerplate.data.mappers.sample.GizmoMapper;
import com.oscarrrweb.tddboilerplate.data.mappers.sample.WidgetMapper;
import com.oscarrrweb.tddboilerplate.data.repository.sample.DoodadRepository;
import com.oscarrrweb.tddboilerplate.data.repository.sample.GizmoRepository;
import com.oscarrrweb.tddboilerplate.data.repository.sample.WidgetRepository;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.DoodadDao;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.GizmoDao;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.WidgetDao;
import com.oscarrrweb.tddboilerplate.data.storage.database.AppDatabase;
import com.oscarrrweb.tddboilerplate.domain.executor.ComputationThread;
import com.oscarrrweb.tddboilerplate.domain.executor.ExecutorThread;
import com.oscarrrweb.tddboilerplate.domain.executor.MainThread;
import com.oscarrrweb.tddboilerplate.presentation.di.modules.ApplicationModule;
import com.oscarrrweb.tddboilerplate.presentation.di.modules.DataModule;
import com.oscarrrweb.tddboilerplate.presentation.ui.activities.base.BaseAppCompatActivity;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Lazy;

@Singleton
@Component(modules = {ApplicationModule.class, DataModule.class})
public interface ApplicationComponent {

    void inject(BaseAppCompatActivity activity);

    Context context();

    MainThread mainThread();

    ExecutorThread executorThread();

    Lazy<ComputationThread> computationThread();

    AppDatabase appDatabase();

    /* SAMPLE USAGE BELOW */

    void inject(WidgetMapper mapper);
    void inject(WidgetRepository repository);
    void inject(GizmoMapper mapper);
    void inject(GizmoRepository repository);
    void inject(DoodadMapper mapper);
    void inject(DoodadRepository repository);

    GizmoRepository gizmoRepository();
    GizmoMapper gizmoMapper();
    GizmoDao gizmoDao();

    WidgetRepository widgetRepository();
    WidgetMapper widgetMapper();
    WidgetDao widgetDao();

    DoodadRepository doodadRepository();
    DoodadMapper doodadMapper();
    DoodadDao doodadDao();
}
