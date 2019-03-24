package com.oscarrrweb.tddboilerplate.data.storage.dao.base;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;

import java.util.List;


@Dao
public interface EntityDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(T entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insert(T[] entity);

    @Update
    int update(T entity);

    @Delete
    int delete(T entity);

    @Delete
    int delete(T[] entities);

    T fromId(int id);

    T fromUuid(String uuid);

    List<T> getAll();
}
