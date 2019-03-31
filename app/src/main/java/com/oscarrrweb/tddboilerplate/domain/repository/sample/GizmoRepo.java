package com.oscarrrweb.tddboilerplate.domain.repository.sample;

import com.oscarrrweb.tddboilerplate.domain.model.sample.Gizmo;

import java.util.List;

public interface GizmoRepo {

    Gizmo attachWidgets(Gizmo gizmo);

    List<Gizmo> attachWidgets(List<Gizmo> gizmo);
}
