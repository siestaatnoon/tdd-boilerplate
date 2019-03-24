package com.oscarrrweb.tddboilerplate.presentation.di.modules;

import android.content.Context;

import com.oscarrrweb.tddboilerplate.data.mappers.sample.DoodadMapper;
import com.oscarrrweb.tddboilerplate.data.mappers.sample.GizmoMapper;
import com.oscarrrweb.tddboilerplate.data.mappers.sample.WidgetMapper;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.DoodadDao;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.GizmoDao;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.WidgetDao;
import com.oscarrrweb.tddboilerplate.data.storage.database.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    private final AppDatabase database;

    public DataModule(Context context) {
        database = AppDatabase.getInstance(context);
    }

    @Provides @Singleton AppDatabase provideAppDatabase() {
        return database;
    }

    /* SAMPLE USAGE BELOW */

    @Provides @Singleton WidgetDao provideWidgetDao() {
        return database.widgetDao();
    }

    @Provides @Singleton GizmoDao provideGizmoDao() {
        return database.gizmoDao();
    }

    @Provides @Singleton DoodadDao provideDoodadDao() {
        return database.doodadDao();
    }

    @Provides @Singleton WidgetMapper provideWidgetMapper() {
        return new WidgetMapper();
    }

    @Provides @Singleton GizmoMapper provideGizmoMapper() {
        return new GizmoMapper();
    }

    @Provides @Singleton DoodadMapper provideDoodadMapper() {
        return new DoodadMapper();
    }
}
