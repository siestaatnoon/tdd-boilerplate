package com.cccdlabs.sample.presentation.di.modules;

import com.cccdlabs.sample.data.mappers.GizmoMapper;
import com.cccdlabs.sample.data.mappers.WidgetMapper;
import com.cccdlabs.sample.data.repository.GizmoRepository;
import com.cccdlabs.sample.data.repository.WidgetRepository;
import com.cccdlabs.sample.data.storage.dao.GizmoDao;
import com.cccdlabs.sample.data.storage.dao.WidgetDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public class TestUseCaseModule {

    public TestUseCaseModule() {}

    @Provides @Singleton GizmoRepository provideGizmoRepository() {
        return mock(GizmoRepository.class);
    }

    @Provides @Singleton WidgetRepository provideWidgetRepository() {
        return mock(WidgetRepository.class);
    }

    @Provides @Singleton GizmoMapper provideGizmoMapper() {
        return mock(GizmoMapper.class);
    }

    @Provides @Singleton WidgetMapper provideWidgetMapper() {
        return mock(WidgetMapper.class);
    }

    @Provides @Singleton GizmoDao provideGizmoDao() {
        return mock(GizmoDao.class);
    }

    @Provides @Singleton WidgetDao provideWidgetDao() {
        return mock(WidgetDao.class);
    }
}
