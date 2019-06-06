package com.cccdlabs.sample.presentation.di.modules;

import com.cccdlabs.sample.data.mappers.DoodadMapper;
import com.cccdlabs.sample.data.mappers.WidgetMapper;
import com.cccdlabs.sample.presentation.di.Mapper;

import dagger.Module;
import dagger.Provides;

@Module
public class TestMapperModule {

    @Provides @Mapper WidgetMapper provideWidgetMapper() {
        return new WidgetMapper();
    }

    @Provides @Mapper DoodadMapper provideDoodadMapper() {
        return new DoodadMapper();
    }
}
