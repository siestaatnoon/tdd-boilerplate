package com.cccdlabs.sample.data.storage.dao;

import com.cccdlabs.sample.data.entity.GizmoEntity;
import com.cccdlabs.tddboilerplate.data.storage.dao.base.EntityDao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
abstract public class GizmoDao implements EntityDao<GizmoEntity> {

    @Query("DELETE FROM gizmos WHERE uuid=:uuid")
    abstract public int delete(String uuid);

    @Query("SELECT * FROM gizmos WHERE id=:id LIMIT 1")
    abstract public GizmoEntity fromId(int id);

    @Query("SELECT * FROM gizmos WHERE uuid=:uuid LIMIT 1")
    abstract public GizmoEntity fromUuid(String uuid);

    @Query("SELECT * FROM gizmos WHERE uuid IN (:uuids) ORDER BY id ASC")
    abstract public List<GizmoEntity> fromUuids(List<String> uuids);

    @Query("SELECT * FROM gizmos ORDER BY id ASC")
    abstract public List<GizmoEntity> getAll();

    @Query("UPDATE gizmos SET updated_at=:dateTime WHERE uuid=:uuid")
    abstract public int setUpdatedAt(String uuid, String dateTime);

    @Query("SELECT id, uuid, name FROM gizmos ORDER BY id ASC")
    abstract public List<ListItem> getForList();

    public static class ListItem {
        public int id;
        public String uuid;
        public String name;
    }
}
