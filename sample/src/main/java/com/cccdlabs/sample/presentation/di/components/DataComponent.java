package com.cccdlabs.sample.presentation.di.components;

import com.cccdlabs.sample.data.mappers.DoodadMapper;
import com.cccdlabs.sample.data.mappers.GizmoMapper;
import com.cccdlabs.sample.data.mappers.WidgetMapper;
import com.cccdlabs.sample.data.storage.dao.DoodadDao;
import com.cccdlabs.sample.data.storage.dao.GizmoDao;
import com.cccdlabs.sample.data.storage.dao.WidgetDao;
import com.cccdlabs.sample.presentation.di.modules.DataModule;
import com.cccdlabs.sample.data.repository.DoodadRepository;
import com.cccdlabs.sample.data.repository.GizmoRepository;
import com.cccdlabs.sample.data.repository.WidgetRepository;
import com.cccdlabs.sample.data.storage.database.AppDatabase;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = DataModule.class)
public interface DataComponent {

    AppDatabase appDatabase();

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
