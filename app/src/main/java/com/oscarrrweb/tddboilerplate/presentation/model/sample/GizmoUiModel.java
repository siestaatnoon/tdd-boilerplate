package com.oscarrrweb.tddboilerplate.presentation.model.sample;

import java.util.List;

public class GizmoUiModel {

    private String name;

    private String description;

    private List<WidgetUiModel> widgets;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<WidgetUiModel> getWidgets() {
        return widgets;
    }

    public void setWidgets(List<WidgetUiModel> widgets) {
        this.widgets = widgets;
    }
}
