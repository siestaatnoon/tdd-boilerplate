package com.oscarrrweb.tddboilerplate.presentation.views;

import com.oscarrrweb.tddboilerplate.presentation.model.sample.WidgetUiModel;

import java.util.List;

public interface MainView extends BaseView {
    void showWidgets(List<WidgetUiModel> tights);
}
