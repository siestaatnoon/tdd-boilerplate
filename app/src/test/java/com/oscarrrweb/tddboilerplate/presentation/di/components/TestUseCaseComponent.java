package com.oscarrrweb.tddboilerplate.presentation.di.components;

import com.oscarrrweb.tddboilerplate.data.mappers.sample.GizmoMapper;
import com.oscarrrweb.tddboilerplate.data.repository.sample.GizmoRepository;
import com.oscarrrweb.tddboilerplate.domain.interactors.sample.SampleDisplayUseCase;
import com.oscarrrweb.tddboilerplate.presentation.di.modules.TestUseCaseModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = TestUseCaseModule.class)
public interface TestUseCaseComponent {
    void inject(SampleDisplayUseCase useCase);
    void inject(GizmoRepository repository);
    void inject(GizmoMapper mapper);

    GizmoRepository gizmoRepository();
    GizmoMapper gizmoMapper();
}
