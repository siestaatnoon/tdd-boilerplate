package com.oscarrrweb.tddboilerplate.presentation.di.components;

import android.content.Context;

import com.oscarrrweb.tddboilerplate.presentation.di.modules.DataModule;
import com.oscarrrweb.tddboilerplate.data.repository.sample.DoodadRepository;
import com.oscarrrweb.tddboilerplate.data.repository.sample.GizmoRepository;
import com.oscarrrweb.tddboilerplate.data.repository.sample.WidgetRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(dependencies = ApplicationComponent.class, modules = DataModule.class)
public interface DataComponent {

    /* SAMPLE USAGE BELOW */

    void inject(WidgetRepository repository);
    void inject(GizmoRepository repository);
    void inject(DoodadRepository repository);
}
