package com.oscarrrweb.tddboilerplate.presentation.di.components;

import com.oscarrrweb.tddboilerplate.data.mappers.sample.WidgetMapper;
import com.oscarrrweb.tddboilerplate.presentation.di.Mapper;
import com.oscarrrweb.tddboilerplate.presentation.di.modules.TestMapperModule;

import dagger.Component;

@Mapper
@Component(modules = TestMapperModule.class)
public interface TestMapperComponent {
    WidgetMapper widgetMapper();
    void inject(WidgetMapper mapper);
}
