package com.oscarrrweb.tddboilerplate.data.storage.dao.base;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

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
