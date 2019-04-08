package com.oscarrrweb.tddboilerplate.domain.repository.sample;

import com.oscarrrweb.tddboilerplate.domain.model.sample.Widget;
import com.oscarrrweb.tddboilerplate.domain.repository.exception.RepositoryQueryException;

import java.util.List;

public interface WidgetRepo {

    Widget attachGizmo(Widget widget) throws RepositoryQueryException;

    List<Widget> attachGizmo(List<Widget> widget) throws RepositoryQueryException;

    Widget attachDoodads(Widget widget) throws RepositoryQueryException;

    List<Widget> attachDoodads(List<Widget> widget) throws RepositoryQueryException;
}
