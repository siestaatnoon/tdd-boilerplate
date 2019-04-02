package com.oscarrrweb.tddboilerplate.presentation.views;

import com.oscarrrweb.tddboilerplate.presentation.model.sample.GizmoUiModel;
import com.oscarrrweb.tddboilerplate.presentation.views.base.BaseView;

import java.util.List;

public interface MainView extends BaseView {
    void showGizmos(List<GizmoUiModel> gizmos);
}
