package com.oscarrrweb.tddboilerplate.data.mappers.sample;

import com.oscarrrweb.tddboilerplate.data.entity.sample.Gizmo;
import com.oscarrrweb.tddboilerplate.data.mappers.base.EntityMapper;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class GizmoMapper extends
        EntityMapper<Gizmo, com.oscarrrweb.tddboilerplate.domain.model.sample.Gizmo> {

    @Inject
    public GizmoMapper() {}

    @Override
    public Gizmo fromDomainModel(com.oscarrrweb.tddboilerplate.domain.model.sample.Gizmo domainModel) {
        if (domainModel == null) {
            return null;
        }

        Gizmo entity = new Gizmo();
        entity = (Gizmo) EntityMapper.setEntityFields(entity, domainModel);
        entity.setName(domainModel.getName());
        entity.setValue(domainModel.getValue());
        return entity;
    }

    @Override
    public List<Gizmo> fromDomainModel(List<com.oscarrrweb.tddboilerplate.domain.model.sample.Gizmo> domainModels) {
        if (domainModels == null) {
            return null;
        }

        List<Gizmo> entityList = new ArrayList<>();
        for (com.oscarrrweb.tddboilerplate.domain.model.sample.Gizmo model : domainModels) {
            entityList.add(fromDomainModel(model));
        }
        return entityList;
    }

    @Override
    public com.oscarrrweb.tddboilerplate.domain.model.sample.Gizmo toDomainModel(Gizmo entity) {
        if (entity == null) {
            return null;
        }

        com.oscarrrweb.tddboilerplate.domain.model.sample.Gizmo model =
                new com.oscarrrweb.tddboilerplate.domain.model.sample.Gizmo();
        model = (com.oscarrrweb.tddboilerplate.domain.model.sample.Gizmo) EntityMapper.setDomainModelFields(model, entity);
        model.setName(entity.getName());
        model.setValue(entity.getValue());
        return model;
    }

    @Override
    public List<com.oscarrrweb.tddboilerplate.domain.model.sample.Gizmo> toDomainModel(List<Gizmo> entities) {
        if (entities == null) {
            return null;
        }

        List<com.oscarrrweb.tddboilerplate.domain.model.sample.Gizmo> modelList = new ArrayList<>();
        for (Gizmo entity : entities) {
            modelList.add(toDomainModel(entity));
        }
        return modelList;
    }
}
