package com.oscarrrweb.tddboilerplate.data.mappers.sample;

import com.oscarrrweb.tddboilerplate.data.entity.sample.Doodad;
import com.oscarrrweb.tddboilerplate.data.mappers.base.EntityMapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class DoodadMapper extends
        EntityMapper<Doodad, com.oscarrrweb.tddboilerplate.domain.model.sample.Doodad> {

    @Inject
    public DoodadMapper() {}

    @Override
    public Doodad fromDomainModel(com.oscarrrweb.tddboilerplate.domain.model.sample.Doodad domainModel) {
        if (domainModel == null) {
            return null;
        }

        Doodad entity = new Doodad();
        entity = (Doodad) EntityMapper.setEntityFields(entity, domainModel);
        entity.setName(domainModel.getName());
        entity.setValue(domainModel.getValue());
        entity.setWidgetUuid(domainModel.getWidgetUuid());
        return entity;
    }

    @Override
    public List<Doodad> fromDomainModel(List<com.oscarrrweb.tddboilerplate.domain.model.sample.Doodad> domainModels) {
        if (domainModels == null) {
            return null;
        }

        List<Doodad> entityList = new ArrayList<>();
        for (com.oscarrrweb.tddboilerplate.domain.model.sample.Doodad model : domainModels) {
            entityList.add(fromDomainModel(model));
        }
        return entityList;
    }

    @Override
    public com.oscarrrweb.tddboilerplate.domain.model.sample.Doodad toDomainModel(Doodad entity) {
        if (entity == null) {
            return null;
        }

        com.oscarrrweb.tddboilerplate.domain.model.sample.Doodad model =
                new com.oscarrrweb.tddboilerplate.domain.model.sample.Doodad();
        model = (com.oscarrrweb.tddboilerplate.domain.model.sample.Doodad) EntityMapper.setDomainModelFields(model, entity);
        model.setName(entity.getName());
        model.setValue(entity.getValue());
        model.setWidgetUuid(entity.getWidgetUuid());
        return model;
    }

    @Override
    public List<com.oscarrrweb.tddboilerplate.domain.model.sample.Doodad> toDomainModel(List<Doodad> entities) {
        if (entities == null) {
            return null;
        }

        List<com.oscarrrweb.tddboilerplate.domain.model.sample.Doodad> modelList = new ArrayList<>();
        for (Doodad entity : entities) {
            modelList.add(toDomainModel(entity));
        }
        return modelList;
    }
}
