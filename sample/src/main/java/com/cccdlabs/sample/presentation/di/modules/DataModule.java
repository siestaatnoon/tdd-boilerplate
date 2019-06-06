package com.cccdlabs.sample.presentation.di.modules;

import android.content.Context;

import com.cccdlabs.sample.data.mappers.DoodadMapper;
import com.cccdlabs.sample.data.mappers.GizmoMapper;
import com.cccdlabs.sample.data.mappers.WidgetMapper;
import com.cccdlabs.sample.data.repository.DoodadRepository;
import com.cccdlabs.sample.data.repository.GizmoRepository;
import com.cccdlabs.sample.data.repository.WidgetRepository;
import com.cccdlabs.sample.data.storage.dao.DoodadDao;
import com.cccdlabs.sample.data.storage.dao.GizmoDao;
import com.cccdlabs.sample.data.storage.dao.WidgetDao;
import com.cccdlabs.sample.data.storage.database.AppDatabase;
import com.cccdlabs.tddboilerplate.data.network.retrofit.RestClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    private AppDatabase database;
    private com.cccdlabs.tddboilerplate.data.storage.database.AppDatabase appDatabase;
    private String apiBaseUrl;
    private boolean refreshInstance;

    public DataModule(Context context) {
        this(context, null, false, false);
    }

    public DataModule(Context context, boolean isTest) {
        this(context, null, isTest, false);
    }

    public DataModule(Context context, boolean isTest, boolean refreshInstance) {
        this(context, null, false, false);
    }

    public DataModule(Context context, String apiBaseUrl) {
        this(context, apiBaseUrl, false, false);
    }

    public DataModule(Context context, String apiBaseUrl, boolean isTest) {
        this(context, apiBaseUrl, isTest, false);
    }

    public DataModule(Context context, String apiBaseUrl, boolean isTest, boolean refreshInstance) {
        this.apiBaseUrl = apiBaseUrl;
        this.refreshInstance = refreshInstance;
        database = AppDatabase.getInstance(context, isTest, refreshInstance);
        appDatabase = com.cccdlabs.tddboilerplate.data.storage.database.AppDatabase.getInstance(context, isTest, refreshInstance);
    }

    @Provides @Singleton com.cccdlabs.tddboilerplate.data.storage.database.AppDatabase provideAppDatabase() {
        return appDatabase;
    }

    @Provides @Singleton AppDatabase provideSampleAppDatabase() {
        return database;
    }

    @Provides @Singleton RestClient provideRestClient() {
        return RestClient.getInstance(apiBaseUrl, refreshInstance);
    }

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
