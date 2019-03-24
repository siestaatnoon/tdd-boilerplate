package com.oscarrrweb.tddboilerplate.domain.model.sample;

import com.oscarrrweb.tddboilerplate.domain.model.base.AbstractModel;

public class Gizmo extends AbstractModel {

    private String name;

    private String value;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
