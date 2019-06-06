package com.cccdlabs.sample.presentation.di.components;

import com.cccdlabs.sample.data.mappers.GizmoMapper;
import com.cccdlabs.sample.data.repository.GizmoRepository;
import com.cccdlabs.sample.domain.interactors.SampleDisplayUseCase;
import com.cccdlabs.sample.presentation.di.modules.TestUseCaseModule;

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
