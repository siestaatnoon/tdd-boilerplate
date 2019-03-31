package com.oscarrrweb.tddboilerplate.presentation.views;

import com.oscarrrweb.tddboilerplate.presentation.model.sample.GizmoUiModel;

import java.util.List;

public interface MainView extends BaseView {
    void showGizmos(List<GizmoUiModel> gizmos);
}
