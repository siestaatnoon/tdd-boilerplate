package com.oscarrrweb.tddboilerplate.domain.repository.sample;

import com.oscarrrweb.tddboilerplate.domain.model.sample.Widget;

import java.util.List;

public interface WidgetRepo {

    Widget attachGizmo(Widget widget);

    List<Widget> attachGizmo(List<Widget> widget);

    Widget attachDoodads(Widget widget);

    List<Widget> attachDoodads(List<Widget> widget);
}
