package com.oscarrrweb.tddboilerplate.domain.repository.sample;

import com.oscarrrweb.tddboilerplate.domain.model.sample.Gizmo;
import com.oscarrrweb.tddboilerplate.domain.repository.exception.RepositoryQueryException;

import java.util.List;

public interface GizmoRepo {

    Gizmo attachWidgets(Gizmo gizmo) throws RepositoryQueryException;

    List<Gizmo> attachWidgets(List<Gizmo> gizmo) throws RepositoryQueryException;
}
