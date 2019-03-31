package com.oscarrrweb.tddboilerplate.domain.interactors.sample;

import com.oscarrrweb.tddboilerplate.data.repository.sample.WidgetRepository;
import com.oscarrrweb.tddboilerplate.domain.interactors.base.AbstractUseCase;
import com.oscarrrweb.tddboilerplate.domain.model.sample.Gizmo;

import java.util.List;

import javax.inject.Inject;

public class SampleDisplayUseCase extends AbstractUseCase<Void, List<Gizmo>> {

    @Inject WidgetRepository mRepository;

    @Inject
    public SampleDisplayUseCase() {}

    @Override
    public List<Gizmo> run(Void parameter) {
        return null;
    }
}
