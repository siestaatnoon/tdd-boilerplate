package com.oscarrrweb.tddboilerplate.data.repository.sample;

import com.oscarrrweb.tddboilerplate.data.entity.sample.Doodad;
import com.oscarrrweb.tddboilerplate.data.entity.sample.Gizmo;
import com.oscarrrweb.tddboilerplate.data.entity.sample.Widget;
import com.oscarrrweb.tddboilerplate.data.mappers.sample.DoodadMapper;
import com.oscarrrweb.tddboilerplate.data.mappers.sample.GizmoMapper;
import com.oscarrrweb.tddboilerplate.data.mappers.sample.WidgetMapper;
import com.oscarrrweb.tddboilerplate.data.repository.base.AbstractRepository;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.DoodadDao;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.GizmoDao;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.WidgetDao;
import com.oscarrrweb.tddboilerplate.domain.repository.sample.WidgetRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class WidgetRepository extends AbstractRepository<Widget, com.oscarrrweb.tddboilerplate.domain.model.sample.Widget,
        WidgetMapper, WidgetDao> implements WidgetRepo {

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
        com.oscarrrweb.tddboilerplate.data.entity.sample.Widget model = mWidgetDao.fromUuid(uuid);
        int affectedRows = 0;

        if (model != null) {
            // first delete doodads attached to widget
            List<Doodad> list = mDoodadDao.getByWidget(model.getUuid());
            if (list != null) {
                Object[] objects = list.toArray();
                Doodad[] journal = Arrays.copyOf(objects, objects.length, Doodad[].class);
                affectedRows = mDoodadDao.delete(journal);
            }

            // now delete the entity
            affectedRows = mWidgetDao.delete(model);
        }

        return affectedRows;
    }

    @Override
    public int delete(com.oscarrrweb.tddboilerplate.domain.model.sample.Widget model) {
        com.oscarrrweb.tddboilerplate.data.entity.sample.Widget entity = mWidgetMapper.fromDomainModel(model);
        if (entity != null) {
            return delete(entity.getUuid());
        }

        return 0;
    }

    @Override
    public int delete(int id) {
        com.oscarrrweb.tddboilerplate.data.entity.sample.Widget entity = mWidgetDao.fromId(id);
        if (entity != null) {
            return delete(entity.getUuid());
        }

        return 0;
    }

    @Override
    public com.oscarrrweb.tddboilerplate.domain.model.sample.Widget attachGizmo(com.oscarrrweb.tddboilerplate.domain.model.sample.Widget model) {
        if (model == null) {
            return model;
        }

        // since we do not know or need to know fields of the domain
        // model, we need to convert the domain model to an entity
        com.oscarrrweb.tddboilerplate.data.entity.sample.Widget entity = mWidgetMapper.fromDomainModel(model);

        Gizmo gizmo = mGizmoDao.fromUuid(entity.getUuid());
        model.setGizmo(mGizmoMapper.toDomainModel(gizmo));
        return model;
    }

    @Override
    public List<com.oscarrrweb.tddboilerplate.domain.model.sample.Widget>
            attachGizmo(List<com.oscarrrweb.tddboilerplate.domain.model.sample.Widget> models) {
        if (models == null || models.size() == 0) {
            return models;
        }

        List<String> uuids = new ArrayList<>();
        for (com.oscarrrweb.tddboilerplate.domain.model.sample.Widget item : models) {
            // since we do not know or need to know fields of the domain
            // model, we need to convert the domain model to an entity
            com.oscarrrweb.tddboilerplate.data.entity.sample.Widget entity = mWidgetMapper.fromDomainModel(item);

            String uuid = entity.getGizmoUuid();
            if ( ! uuids.contains(uuid)) {
                uuids.add(uuid);
            }
        }

        List<Gizmo> gizmos = mGizmoDao.fromUuids(uuids);
        for (com.oscarrrweb.tddboilerplate.domain.model.sample.Widget item : models) {
            // since we do not know or need to know fields of the domain
            // model, we need to convert the domain model to an entity
            com.oscarrrweb.tddboilerplate.data.entity.sample.Widget entity = mWidgetMapper.fromDomainModel(item);

            String uuid = entity.getGizmoUuid();
            for (Gizmo gizmo : gizmos) {
                if (uuid.equals(gizmo.getUuid())) {
                    item.setGizmo(mGizmoMapper.toDomainModel(gizmo));
                    break;
                }
            }
        }

        return models;
    }

    @Override
    public com.oscarrrweb.tddboilerplate.domain.model.sample.Widget
            attachDoodads(com.oscarrrweb.tddboilerplate.domain.model.sample.Widget model) {
        if (model == null) {
            return model;
        }

        // since we do not know or need to know fields of the domain
        // model, we need to convert the domain model to an entity
        com.oscarrrweb.tddboilerplate.data.entity.sample.Widget entity = mWidgetMapper.fromDomainModel(model);

        List<Doodad> list = mDoodadDao.getByWidget(entity.getUuid());
        model.setDoodads(mDoodadMapper.toDomainModel(list));
        return model;
    }

    @Override
    public List<com.oscarrrweb.tddboilerplate.domain.model.sample.Widget>
            attachDoodads(List<com.oscarrrweb.tddboilerplate.domain.model.sample.Widget> models) {
        if (models == null || models.size() == 0) {
            return models;
        }

        List<String> uuids = new ArrayList<>();
        for (com.oscarrrweb.tddboilerplate.domain.model.sample.Widget item : models) {
            // since we do not know or need to know fields of the domain
            // model, we need to convert the domain model to an entity
            com.oscarrrweb.tddboilerplate.data.entity.sample.Widget entity = mWidgetMapper.fromDomainModel(item);

            String uuid = entity.getUuid();
            if ( ! uuids.contains(uuid)) {
                uuids.add(uuid);
            }
        }

        List<Doodad> doodads = mDoodadDao.getByWidgets(uuids);
        for (com.oscarrrweb.tddboilerplate.domain.model.sample.Widget item : models) {
            // since we do not know or need to know fields of the domain
            // model, we need to convert the domain model to an entity
            com.oscarrrweb.tddboilerplate.data.entity.sample.Widget entity = mWidgetMapper.fromDomainModel(item);

            List<Doodad> entities = new ArrayList<>();
            String currUuid = entity.getUuid();
            for (Doodad doodad : doodads) {
                if (currUuid.equals(doodad.getWidgetUuid())) {
                    entities.add(doodad);
                }
            }
            item.setDoodads(mDoodadMapper.toDomainModel(entities));
        }

        return models;
    }
}
