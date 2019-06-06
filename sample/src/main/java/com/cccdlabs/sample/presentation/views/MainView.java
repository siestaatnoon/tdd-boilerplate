package com.cccdlabs.sample.presentation.views;

import com.cccdlabs.sample.presentation.model.GizmoUiModel;
import com.cccdlabs.tddboilerplate.presentation.views.base.BaseView;

import java.util.List;

public interface MainView extends BaseView {
    void showGizmos(List<GizmoUiModel> gizmos);
}
