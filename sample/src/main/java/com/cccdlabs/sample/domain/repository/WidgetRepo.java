package com.cccdlabs.sample.domain.repository;

import com.cccdlabs.sample.domain.model.Widget;
import com.cccdlabs.tddboilerplate.domain.repository.exception.RepositoryQueryException;

import java.util.List;

public interface WidgetRepo {

    Widget attachGizmo(Widget widget) throws RepositoryQueryException;

    List<Widget> attachGizmo(List<Widget> widget) throws RepositoryQueryException;

    Widget attachDoodads(Widget widget) throws RepositoryQueryException;

    List<Widget> attachDoodads(List<Widget> widget) throws RepositoryQueryException;
}
