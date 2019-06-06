package com.cccdlabs.sample.presentation.di.components;

import com.cccdlabs.sample.data.mappers.GizmoMapper;
import com.cccdlabs.sample.data.mappers.WidgetMapper;
import com.cccdlabs.sample.presentation.di.Mapper;
import com.cccdlabs.sample.presentation.di.modules.TestMapperModule;

import dagger.Component;

@Mapper
@Component(modules = TestMapperModule.class)
public interface TestMapperComponent {

    void inject(GizmoMapper mapper);
    void inject(WidgetMapper mapper);
    GizmoMapper gizmoMapper();
    WidgetMapper widgetMapper();
}
