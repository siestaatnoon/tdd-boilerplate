package com.oscarrrweb.tddboilerplate.domain.model.sample;

import com.oscarrrweb.tddboilerplate.domain.model.base.AbstractModel;

public class Gizmo extends AbstractModel {

    private String name;

    private String description;


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
}
