package com.cccdlabs.sample.domain.repository;

import com.cccdlabs.sample.domain.model.Gizmo;
import com.cccdlabs.tddboilerplate.domain.repository.exception.RepositoryQueryException;

import java.util.List;

public interface GizmoRepo {

    Gizmo attachWidgets(Gizmo gizmo) throws RepositoryQueryException;

    List<Gizmo> attachWidgets(List<Gizmo> gizmo) throws RepositoryQueryException;
}
