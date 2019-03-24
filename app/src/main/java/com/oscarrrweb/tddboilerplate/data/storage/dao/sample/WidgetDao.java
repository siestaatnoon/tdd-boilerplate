package com.oscarrrweb.tddboilerplate.data.storage.dao.sample;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.oscarrrweb.tddboilerplate.data.entity.sample.Widget;
import com.oscarrrweb.tddboilerplate.data.storage.dao.base.EntityDao;

import java.util.List;

@Dao
abstract public class WidgetDao implements EntityDao<Widget> {

    @Query("DELETE FROM widgets WHERE uuid=:uuid")
    abstract public int delete(String uuid);

    @Query("SELECT * FROM widgets WHERE id=:id LIMIT 1")
    abstract public Widget fromId(int id);

    @Query("SELECT * FROM widgets WHERE uuid=:uuid LIMIT 1")
    abstract public Widget fromUuid(String uuid);

    @Transaction
    @Query("SELECT * FROM widgets WHERE gizmo_uuid=:gizmoUuid ORDER BY created_at DESC")
    abstract public List<Widget> getByGizmo(String gizmoUuid);

    @Query("SELECT * FROM widgets ORDER BY created_at DESC")
    abstract public List<Widget> getAll();

    @Query("UPDATE widgets SET updated_at=:dateTime WHERE uuid=:uuid")
    abstract public int setUpdatedAt(String uuid, String dateTime);
}

