package com.oscarrrweb.tddboilerplate.data.repository.base;

import android.database.sqlite.SQLiteException;

import com.oscarrrweb.tddboilerplate.data.entity.base.Entity;
import com.oscarrrweb.tddboilerplate.data.mappers.base.Mapper;
import com.oscarrrweb.tddboilerplate.data.storage.dao.base.EntityDao;
import com.oscarrrweb.tddboilerplate.domain.model.base.AbstractModel;
import com.oscarrrweb.tddboilerplate.domain.repository.base.Repository;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

abstract public class AbstractRepository<E extends Entity, T extends AbstractModel,
        M extends Mapper<E, T>, D extends EntityDao<E>> implements Repository<T> {

    @Inject D mDao;
    @Inject M mMapper;

    protected AbstractRepository() {}

    @Override
    public int insert(T model) {
        if (model == null) {
            return -1;
        }

        E entity = mMapper.fromDomainModel(model);

        // make sure uuid is set
        entity.setUuid();

        // set created/updated timestamps
        if (entity.getCreatedAt() == null || entity.getUpdatedAt() == null) {
            entity.touch();
        }

        int id = -1;
        try {
            id = (int) mDao.insert(entity);
        } catch (SQLiteException e) {
            // This is most likely due to entity having a foreign
            // key to a row that no longer exists so log it
            StringBuffer buffer = new StringBuffer();
            buffer.append(e.getMessage()).append("\n").append(entity.toString());
            Timber.e(buffer.toString());
            return id;
        }

        return id;
    }

    @Override
    public int update(T model) {
        if (model == null) {
            return -1;
        }

        E updated = mMapper.fromDomainModel(model);

        // set updated timestamp
        updated.touch();

        E original = mDao.fromUuid(updated.getUuid());
        if (original == null) {
            // original model was deleted or doesn't exist
            return -1;
        }

        // make sure ID and created at date hasn't changed or set it if from synced entity
        updated.setId(original.getId());
        updated.setCreatedAt(original.getCreatedAt());

        int affectedRows = -1;
        try {
            affectedRows = mDao.update(updated);
        } catch (SQLiteException e) {
            // This is most likely due to entity having a foreign
            // key to a row that no longer exists so log it
            StringBuffer buffer = new StringBuffer();
            buffer.append(e.getMessage()).append("\n").append(updated.toString());
            Timber.e(buffer.toString());
            return affectedRows;
        }

        return affectedRows;
    }

    @Override
    public int delete(T model) {
        E entity = mMapper.fromDomainModel(model);
        if (entity != null) {
            return delete(entity.getUuid());
        }

        return 0;
    }

    @Override
    public int delete(String uuid) {
        if (uuid == null || uuid.equals("")) {
            return -1;
        }

        E model = mDao.fromUuid(uuid);
        int affectedRows = 0;

        if (model != null) {
            affectedRows = mDao.delete(model);
        }

        return affectedRows;
    }

    @Override
    public int delete(int id) {
        E entity = mDao.fromId(id);
        if (entity != null) {
            return delete(entity.getUuid());
        }

        return 0;
    }

    @Override
    public List<T> getAll() {
        List<E> models = mDao.getAll();
        return mMapper.toDomainModel(models);
    }

    @Override
    public T getById(int id) {
        E model = mDao.fromId(id);
        return mMapper.toDomainModel(model);
    }

    @Override
    public T getByUuid(String uuid) {
        E model = mDao.fromUuid(uuid);
        return mMapper.toDomainModel(model);
    }
}
