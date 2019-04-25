package com.oscarrrweb.tddboilerplate.presentation.di.modules;

import android.content.Context;

import com.oscarrrweb.tddboilerplate.data.mappers.sample.DoodadMapper;
import com.oscarrrweb.tddboilerplate.data.mappers.sample.GizmoMapper;
import com.oscarrrweb.tddboilerplate.data.mappers.sample.WidgetMapper;
import com.oscarrrweb.tddboilerplate.data.network.retrofit.RestClient;
import com.oscarrrweb.tddboilerplate.data.repository.sample.DoodadRepository;
import com.oscarrrweb.tddboilerplate.data.repository.sample.GizmoRepository;
import com.oscarrrweb.tddboilerplate.data.repository.sample.WidgetRepository;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.DoodadDao;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.GizmoDao;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.WidgetDao;
import com.oscarrrweb.tddboilerplate.data.storage.database.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    AppDatabase database;

    public DataModule(Context context) {
        database = AppDatabase.getInstance(context);
    }

    @Provides @Singleton AppDatabase provideAppDatabase() {
        return database;
    }

    @Provides @Singleton RestClient provideRestClient() {
        return RestClient.getInstance();
    }

    /* SAMPLE USAGE BELOW */

    @Provides @Singleton GizmoRepository provideGizmoRepository() {
        return new GizmoRepository();
    }

    @Provides @Singleton GizmoMapper provideGizmoMapper() {
        return new GizmoMapper();
    }

    @Provides @Singleton GizmoDao provideGizmoDao() {
        return database.gizmoDao();
    }

    @Provides @Singleton WidgetRepository provideWidgetRepository() {
        return new WidgetRepository();
    }

    @Provides @Singleton WidgetMapper provideWidgetMapper() {
        return new WidgetMapper();
    }

    @Provides @Singleton WidgetDao provideWidgetDao() {
        return database.widgetDao();
    }

    @Provides @Singleton DoodadRepository provideDoodadRepository() {
        return new DoodadRepository();
    }

    @Provides @Singleton DoodadMapper provideDoodadMapper() {
        return new DoodadMapper();
    }

    @Provides @Singleton DoodadDao provideDoodadDao() {
        return database.doodadDao();
    }
}
