package com.oscarrrweb.tddboilerplate.presentation.di.modules;

import android.content.Context;

import com.oscarrrweb.tddboilerplate.data.mappers.sample.DoodadMapper;
import com.oscarrrweb.tddboilerplate.data.mappers.sample.GizmoMapper;
import com.oscarrrweb.tddboilerplate.data.mappers.sample.WidgetMapper;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.DoodadDao;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.GizmoDao;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.WidgetDao;
import com.oscarrrweb.tddboilerplate.data.storage.database.AppDatabase;
import com.oscarrrweb.tddboilerplate.presentation.di.Repository;

import dagger.Module;
import dagger.Provides;

@Module
public class TestDataModule {

    private final AppDatabase database;

    public TestDataModule(Context context) {
        database = AppDatabase.getInstance(context, true, true);
    }

    @Provides @Repository AppDatabase provideAppDatabase() {
        return database;
    }

    /* SAMPLE USAGE BELOW */

    @Provides @Repository WidgetDao provideWidgetDao() {
        return database.widgetDao();
    }

    @Provides @Repository GizmoDao provideGizmoDao() {
        return database.gizmoDao();
    }

    @Provides @Repository DoodadDao provideDoodadDao() {
        return database.doodadDao();
    }

    @Provides @Repository WidgetMapper provideWidgetMapper() {
        return new WidgetMapper();
    }

    @Provides @Repository GizmoMapper provideGizmoMapper() {
        return new GizmoMapper();
    }

    @Provides @Repository DoodadMapper provideDoodadMapper() {
        return new DoodadMapper();
    }
}
