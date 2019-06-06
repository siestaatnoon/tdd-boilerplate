package com.cccdlabs.sample.domain.interactors;

import com.cccdlabs.sample.data.repository.GizmoRepository;
import com.cccdlabs.sample.domain.model.Gizmo;
import com.cccdlabs.tddboilerplate.domain.interactors.base.AbstractUseCase;

import java.util.List;

import javax.inject.Inject;

public class SampleDisplayUseCase extends AbstractUseCase<Void, List<Gizmo>> {

    @Inject
    GizmoRepository mGizmoRepository;

    @Inject
    public SampleDisplayUseCase() {}

    @Override
    public List<Gizmo> run(Void parameter) throws Exception {
        List<Gizmo> gizmos = mGizmoRepository.getAll();
        return mGizmoRepository.attachWidgets(gizmos);
    }
}
