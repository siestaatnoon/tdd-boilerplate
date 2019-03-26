package com.oscarrrweb.tddboilerplate.data.mappers.sample;

import com.oscarrrweb.tddboilerplate.data.entity.sample.Widget;
import com.oscarrrweb.tddboilerplate.data.mappers.base.EntityMapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class WidgetMapper extends
        EntityMapper<Widget, com.oscarrrweb.tddboilerplate.domain.model.sample.Widget> {

    @Inject DoodadMapper mDoodadMapper;

    @Inject
    public WidgetMapper() {}

    @Override
    public Widget fromDomainModel(com.oscarrrweb.tddboilerplate.domain.model.sample.Widget domainModel) {
        if (domainModel == null) {
            return null;
        }

        Widget entity = new Widget();
        entity = (Widget) EntityMapper.setEntityFields(entity, domainModel);
        entity.setName(domainModel.getName());
        entity.setValue(domainModel.getValue());
        entity.setDoodads(mDoodadMapper.fromDomainModel(domainModel.getDoodads()));
        return entity;
    }

    @Override
    public List<Widget> fromDomainModel(List<com.oscarrrweb.tddboilerplate.domain.model.sample.Widget> domainModels) {
        if (domainModels == null) {
            return null;
        }

        List<Widget> entityList = new ArrayList<>();
        for (com.oscarrrweb.tddboilerplate.domain.model.sample.Widget model : domainModels) {
            entityList.add(fromDomainModel(model));
        }
        return entityList;
    }

    @Override
    public com.oscarrrweb.tddboilerplate.domain.model.sample.Widget toDomainModel(Widget entity) {
        if (entity == null) {
            return null;
        }

        com.oscarrrweb.tddboilerplate.domain.model.sample.Widget model =
                new com.oscarrrweb.tddboilerplate.domain.model.sample.Widget();
        model = (com.oscarrrweb.tddboilerplate.domain.model.sample.Widget) EntityMapper.setDomainModelFields(model, entity);
        model.setName(entity.getName());
        model.setValue(entity.getValue());
        model.setDoodads(mDoodadMapper.toDomainModel(entity.getDoodads()));
        return model;
    }

    @Override
    public List<com.oscarrrweb.tddboilerplate.domain.model.sample.Widget> toDomainModel(List<Widget> entities) {
        if (entities == null) {
            return null;
        }

        List<com.oscarrrweb.tddboilerplate.domain.model.sample.Widget> modelList = new ArrayList<>();
        for (Widget entity : entities) {
            modelList.add(toDomainModel(entity));
        }
        return modelList;
    }
}
