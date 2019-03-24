package com.oscarrrweb.tddboilerplate.data.storage.dao.sample;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.oscarrrweb.tddboilerplate.data.entity.sample.Doodad;
import com.oscarrrweb.tddboilerplate.data.storage.dao.base.EntityDao;

import java.util.List;

@Dao
abstract public class DoodadDao implements EntityDao<Doodad> {

    @Query("DELETE FROM doodads WHERE uuid=:uuid")
    abstract public int delete(String uuid);

    @Query("SELECT * FROM doodads WHERE id=:id LIMIT 1")
    abstract public Doodad fromId(int id);

    @Query("SELECT * FROM doodads WHERE uuid=:uuid LIMIT 1")
    abstract public Doodad fromUuid(String uuid);

    @Query("SELECT * FROM doodads ORDER BY widget_uuid ASC, name ASC")
    abstract public List<Doodad> getAll();

    @Query("SELECT * FROM doodads WHERE widget_uuid=:widgetUuid ORDER BY name ASC")
    abstract public List<Doodad> getByWidget(String widgetUuid);

    @Query("SELECT * FROM doodads WHERE widget_uuid IN (:widgetUuids) ORDER BY name ASC")
    abstract public List<Doodad> getByWidgets(List<String> widgetUuids);

    @Query("UPDATE doodads SET updated_at=:dateTime WHERE uuid=:uuid")
    abstract public int setUpdatedAt(String uuid, String dateTime);
}
