package com.oscarrrweb.tddboilerplate.data.repository.sample;

import com.oscarrrweb.tddboilerplate.data.entity.sample.GizmoEntity;
import com.oscarrrweb.tddboilerplate.data.entity.sample.WidgetEntity;
import com.oscarrrweb.tddboilerplate.data.mappers.sample.GizmoMapper;
import com.oscarrrweb.tddboilerplate.data.mappers.sample.WidgetMapper;
import com.oscarrrweb.tddboilerplate.data.repository.base.AbstractRepository;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.GizmoDao;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.WidgetDao;
import com.oscarrrweb.tddboilerplate.domain.model.sample.Gizmo;
import com.oscarrrweb.tddboilerplate.domain.model.sample.Widget;
import com.oscarrrweb.tddboilerplate.domain.repository.sample.GizmoRepo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class GizmoRepository extends AbstractRepository<GizmoEntity, Gizmo, GizmoMapper, GizmoDao> implements GizmoRepo {

    @Inject WidgetRepository mWidgetRepository;
    @Inject GizmoMapper mGizmoMapper;
    @Inject WidgetMapper mWidgetMapper;
    @Inject WidgetDao mWidgetDao;

    @Inject
    public GizmoRepository() {}

    @Override
    public Gizmo attachWidgets(Gizmo model) {
        if (model == null) {
            return model;
        }

        // since we do not know or need to know fields of the domain
        // model, we need to convert the domain model to an entity
        GizmoEntity entity = mGizmoMapper.fromDomainModel(model);

        List<WidgetEntity> list = mWidgetDao.getByGizmo(entity.getUuid());
        List<Widget> widgets = mWidgetMapper.toDomainModel(list);
        mWidgetRepository.attachDoodads(widgets);
        model.setWidgets(widgets);
        return model;
    }

    @Override
    public List<Gizmo> attachWidgets(List<Gizmo> models) {
        if (models == null || models.size() == 0) {
            return models;
        }

        List<String> uuids = new ArrayList<>();
        for (Gizmo item : models) {
            // since we do not know or need to know fields of the domain
            // model, we need to convert the domain model to an entity
            GizmoEntity entity = mGizmoMapper.fromDomainModel(item);

            String uuid = entity.getUuid();
            if ( ! uuids.contains(uuid)) {
                uuids.add(uuid);
            }
        }

        List<WidgetEntity> items = mWidgetDao.getByGizmos(uuids);
        List<Widget> widgets = mWidgetMapper.toDomainModel(items);
        widgets = mWidgetRepository.attachDoodads(widgets);

        for (Gizmo item : models) {
            // since we do not know or need to know fields of the domain
            // model, we need to convert the domain model to an entity
            GizmoEntity entity = mGizmoMapper.fromDomainModel(item);

            List<Widget> entities = new ArrayList<>();
            String currUuid = entity.getUuid();
            for (Widget widget : widgets) {
                // since we do not know or need to know fields of the domain
                // model, we need to convert the domain model to an entity
                WidgetEntity widgetEntity = mWidgetMapper.fromDomainModel(widget);

                if (currUuid.equals(widgetEntity.getGizmoUuid())) {
                    entities.add(widget);
                }
            }
            item.setWidgets(entities);
        }

        return models;
    }
}
