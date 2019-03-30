package com.oscarrrweb.tddboilerplate.presentation.di.modules;

import com.oscarrrweb.tddboilerplate.data.mappers.sample.DoodadMapper;
import com.oscarrrweb.tddboilerplate.data.mappers.sample.WidgetMapper;
import com.oscarrrweb.tddboilerplate.presentation.di.Mapper;

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
