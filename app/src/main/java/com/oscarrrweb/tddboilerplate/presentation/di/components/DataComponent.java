package com.oscarrrweb.tddboilerplate.presentation.di.components;

import com.oscarrrweb.tddboilerplate.data.mappers.sample.WidgetMapper;
import com.oscarrrweb.tddboilerplate.data.repository.sample.DoodadRepository;
import com.oscarrrweb.tddboilerplate.data.repository.sample.GizmoRepository;
import com.oscarrrweb.tddboilerplate.data.repository.sample.WidgetRepository;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.DoodadDao;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.GizmoDao;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.WidgetDao;
import com.oscarrrweb.tddboilerplate.data.storage.database.AppDatabase;
import com.oscarrrweb.tddboilerplate.presentation.di.Repository;
import com.oscarrrweb.tddboilerplate.presentation.di.modules.DataModule;

import dagger.Component;

@Repository
@Component(dependencies = ApplicationComponent.class, modules = DataModule.class)
public interface DataComponent {

    AppDatabase appDatabase();

    /* SAMPLE USAGE BELOW */

    void inject(WidgetMapper mapper);
    void inject(WidgetRepository repository);
    void inject(GizmoRepository repository);
    void inject(DoodadRepository repository);

    WidgetMapper widgetMapper();
    WidgetDao widgetDao();
    GizmoDao gizmoDao();
    DoodadDao doodadDao();
}
