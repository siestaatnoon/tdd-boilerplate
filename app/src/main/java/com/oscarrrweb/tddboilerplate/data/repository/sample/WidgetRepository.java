package com.oscarrrweb.tddboilerplate.data.repository.sample;

import com.oscarrrweb.tddboilerplate.data.entity.sample.DoodadEntity;
import com.oscarrrweb.tddboilerplate.data.entity.sample.GizmoEntity;
import com.oscarrrweb.tddboilerplate.data.entity.sample.WidgetEntity;
import com.oscarrrweb.tddboilerplate.data.mappers.sample.DoodadMapper;
import com.oscarrrweb.tddboilerplate.data.mappers.sample.GizmoMapper;
import com.oscarrrweb.tddboilerplate.data.mappers.sample.WidgetMapper;
import com.oscarrrweb.tddboilerplate.data.repository.base.AbstractRepository;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.DoodadDao;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.GizmoDao;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.WidgetDao;
import com.oscarrrweb.tddboilerplate.domain.model.sample.Widget;
import com.oscarrrweb.tddboilerplate.domain.repository.sample.WidgetRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class WidgetRepository extends AbstractRepository<WidgetEntity, Widget, WidgetMapper, WidgetDao> implements WidgetRepo {

    @Inject WidgetMapper mWidgetMapper;
    @Inject DoodadMapper mDoodadMapper;
    @Inject GizmoMapper mGizmoMapper;
    @Inject WidgetDao mWidgetDao;
    @Inject GizmoDao mGizmoDao;
    @Inject DoodadDao mDoodadDao;

    @Inject
    public WidgetRepository() {}

    @Override
    public int delete(String uuid) {
        WidgetEntity model = mWidgetDao.fromUuid(uuid);
        int affectedRows = 0;

        if (model != null) {
            // first delete doodads attached to widget
            List<DoodadEntity> list = mDoodadDao.getByWidget(model.getUuid());
            if (list != null) {
                Object[] objects = list.toArray();
                DoodadEntity[] journal = Arrays.copyOf(objects, objects.length, DoodadEntity[].class);
                affectedRows = mDoodadDao.delete(journal);
            }

            // now delete the entity
            affectedRows = mWidgetDao.delete(model);
        }

        return affectedRows;
    }

    @Override
    public int delete(Widget model) {
        WidgetEntity entity = mWidgetMapper.fromDomainModel(model);
        if (entity != null) {
            return delete(entity.getUuid());
        }

        return 0;
    }

    @Override
    public int delete(int id) {
        WidgetEntity entity = mWidgetDao.fromId(id);
        if (entity != null) {
            return delete(entity.getUuid());
        }

        return 0;
    }

    @Override
    public Widget attachGizmo(Widget model) {
        if (model == null) {
            return model;
        }

        // since we do not know or need to know fields of the domain
        // model, we need to convert the domain model to an entity
        WidgetEntity entity = mWidgetMapper.fromDomainModel(model);

        GizmoEntity gizmo = mGizmoDao.fromUuid(entity.getGizmoUuid());
        model.setGizmo(mGizmoMapper.toDomainModel(gizmo));
        return model;
    }

    @Override
    public List<Widget> attachGizmo(List<Widget> models) {
        if (models == null || models.size() == 0) {
            return models;
        }

        List<String> uuids = new ArrayList<>();
        for (Widget item : models) {
            // since we do not know or need to know fields of the domain
            // model, we need to convert the domain model to an entity
            WidgetEntity entity = mWidgetMapper.fromDomainModel(item);

            String uuid = entity.getGizmoUuid();
            if ( ! uuids.contains(uuid)) {
                uuids.add(uuid);
            }
        }

        List<GizmoEntity> gizmos = mGizmoDao.fromUuids(uuids);
        for (Widget item : models) {
            // since we do not know or need to know fields of the domain
            // model, we need to convert the domain model to an entity
            WidgetEntity entity = mWidgetMapper.fromDomainModel(item);

            String uuid = entity.getGizmoUuid();
            for (GizmoEntity gizmo : gizmos) {
                if (uuid.equals(gizmo.getUuid())) {
                    item.setGizmo(mGizmoMapper.toDomainModel(gizmo));
                    break;
                }
            }
        }

        return models;
    }

    @Override
    public Widget attachDoodads(Widget model) {
        if (model == null) {
            return model;
        }

        // since we do not know or need to know fields of the domain
        // model, we need to convert the domain model to an entity
        WidgetEntity entity = mWidgetMapper.fromDomainModel(model);

        List<DoodadEntity> list = mDoodadDao.getByWidget(entity.getUuid());
        model.setDoodads(mDoodadMapper.toDomainModel(list));
        return model;
    }

    @Override
    public List<Widget> attachDoodads(List<Widget> models) {
        if (models == null || models.size() == 0) {
            return models;
        }

        List<String> uuids = new ArrayList<>();
        for (Widget item : models) {
            // since we do not know or need to know fields of the domain
            // model, we need to convert the domain model to an entity
            WidgetEntity entity = mWidgetMapper.fromDomainModel(item);

            String uuid = entity.getUuid();
            if ( ! uuids.contains(uuid)) {
                uuids.add(uuid);
            }
        }

        List<DoodadEntity> doodads = mDoodadDao.getByWidgets(uuids);
        for (Widget item : models) {
            // since we do not know or need to know fields of the domain
            // model, we need to convert the domain model to an entity
            WidgetEntity entity = mWidgetMapper.fromDomainModel(item);

            List<DoodadEntity> entities = new ArrayList<>();
            String currUuid = entity.getUuid();
            for (DoodadEntity doodad : doodads) {
                if (currUuid.equals(doodad.getWidgetUuid())) {
                    entities.add(doodad);
                }
            }
            item.setDoodads(mDoodadMapper.toDomainModel(entities));
        }

        return models;
    }
}
