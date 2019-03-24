package com.oscarrrweb.tddboilerplate.data.storage.dao.sample;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.oscarrrweb.tddboilerplate.data.entity.sample.Gizmo;
import com.oscarrrweb.tddboilerplate.data.storage.dao.base.EntityDao;

import java.util.List;

@Dao
abstract public class GizmoDao implements EntityDao<Gizmo> {

    @Query("DELETE FROM gizmos WHERE uuid=:uuid")
    abstract public int delete(String uuid);

    @Query("SELECT * FROM gizmos WHERE id=:id LIMIT 1")
    abstract public Gizmo fromId(int id);

    @Query("SELECT * FROM gizmos WHERE uuid=:uuid LIMIT 1")
    abstract public Gizmo fromUuid(String uuid);

    @Query("SELECT * FROM gizmos WHERE uuid IN (:uuids) ORDER BY name ASC")
    abstract public List<Gizmo> fromUuids(List<String> uuids);

    @Query("SELECT * FROM gizmos ORDER BY name ASC")
    abstract public List<Gizmo> getAll();

    @Query("UPDATE gizmos SET updated_at=:dateTime WHERE uuid=:uuid")
    abstract public int setUpdatedAt(String uuid, String dateTime);

    @Query("SELECT id, uuid, name FROM gizmos ORDER BY name ASC")
    abstract public List<ListItem> getForList();

    public static class ListItem {
        public int id;
        public String uuid;
        public String name;
    }
}
