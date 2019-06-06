package com.cccdlabs.sample.data.mappers;

import com.cccdlabs.sample.data.entity.DoodadEntity;
import com.cccdlabs.sample.domain.model.Doodad;
import com.cccdlabs.tddboilerplate.data.mappers.base.EntityMapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class DoodadMapper extends EntityMapper<DoodadEntity, Doodad> {

    @Inject
    public DoodadMapper() {}

    @Override
    public DoodadEntity fromDomainModel(Doodad domainModel) {
        if (domainModel == null) {
            return null;
        }

        DoodadEntity entity = (DoodadEntity) EntityMapper.setEntityFields(new DoodadEntity(), domainModel);
        entity.setWidgetUuid(domainModel.getWidgetUuid());
        entity.setName(domainModel.getName());
        entity.setDescription(domainModel.getDescription());
        return entity;
    }

    @Override
    public List<DoodadEntity> fromDomainModel(List<Doodad> domainModels) {
        if (domainModels == null) {
            return null;
        }

        List<DoodadEntity> entityList = new ArrayList<>();
        for (Doodad model : domainModels) {
            entityList.add(fromDomainModel(model));
        }
        return entityList;
    }

    @Override
    public Doodad toDomainModel(DoodadEntity entity) {
        if (entity == null) {
            return null;
        }

        Doodad model = new Doodad();
        model = (Doodad) EntityMapper.setDomainModelFields(model, entity);
        model.setWidgetUuid(entity.getWidgetUuid());
        model.setName(entity.getName());
        model.setDescription(entity.getDescription());
        return model;
    }

    @Override
    public List<Doodad> toDomainModel(List<DoodadEntity> entities) {
        if (entities == null) {
            return null;
        }

        List<Doodad> modelList = new ArrayList<>();
        for (DoodadEntity entity : entities) {
            modelList.add(toDomainModel(entity));
        }
        return modelList;
    }
}
