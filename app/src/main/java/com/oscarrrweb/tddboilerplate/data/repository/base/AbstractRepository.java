package com.oscarrrweb.tddboilerplate.data.repository.base;

import android.database.sqlite.SQLiteException;

import com.oscarrrweb.tddboilerplate.data.entity.base.Entity;
import com.oscarrrweb.tddboilerplate.data.mappers.base.Mapper;
import com.oscarrrweb.tddboilerplate.data.storage.dao.base.EntityDao;
import com.oscarrrweb.tddboilerplate.domain.model.base.Model;
import com.oscarrrweb.tddboilerplate.domain.repository.base.Repository;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Abstraction for straightforward database INSERT, UPDATE, DELETE and SELECT (by ID OR UUID)
 * via POJO objects. Methods can be overridden to account for complex queries or relations.
 * Note that this class handles mapping between {@link Entity} and {@link Model} objects.
 *
 * @param <E>   {@link Entity} subclass type from <code>data</code> package
 * @param <T>   {@link Model} subclass type from <code>domain</code> package
 * @param <M>   {@link Mapper} subclass type that maps the data between the <code><E></code>
 *              and <code><T></code> object types
 * @param <D>   {@link EntityDao} subclass type for <code><T></code> entity type
 */
abstract public class AbstractRepository<E extends Entity, T extends Model,
        M extends Mapper<E, T>, D extends EntityDao<E>> implements Repository<T> {

    /**
     * {@link EntityDao} database access object. {@link Inject} annotated to enable
     * Dagger dependency injection of this object.
     */
    @Inject D mDao;

    /**
     * {@link Mapper} to convert POJO objects between the <code>data</code> and <code>domain</code>
     * packages. {@link Inject} annotated to enable Dagger dependency injection of this object.
     */
    @Inject M mMapper;

    /**
     * Constructor.
     */
    protected AbstractRepository() {}

    /**
     * Performs a database INSERT given the fields of a  {@link Model} subclass POJO object.
     * Note that this method ensures a valid UUID and created/updated timestamps are set before
     * performing the query.
     * <p>
     * NOTE: If an {@link SQLiteException} occurs, it will be logged and -1 will be returned.
     *
     * @param model The POJO object containing the fields (member variables) and corresponding
     *              model values to insert
     * @return int  The AUTO_INCREMENT id of the inserted row
     */
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

    /**
     * Performs a database UPDATE given the fields of a  {@link Model} subclass POJO object.
     * Note that this method updates the update timestamp to current date/time and ensures
     * the original AUTO_INCREMENT id and created timestamp are not changed.
     * <p>
     * NOTE: If an {@link SQLiteException} occurs, it will be logged and -1 will be returned.
     *
     * @param model The POJO object containing the fields (member variables) and corresponding
     *              model values to update
     * @return int  The number of rows successfully updated
     */
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

    /**
     * Performs a database DELETE given a {@link Model} subclass POJO object.
     * <p>
     * NOTE: If an {@link SQLiteException} occurs, it will be logged and -1 will be returned.
     *
     * @param model The POJO object to perform delete
     * @return int  The number of rows successfully deleted
     */
    @Override
    public int delete(T model) {
        int affectedRows = 0;

        try {
            E entity = mMapper.fromDomainModel(model);
            if (entity != null) {
                return delete(entity.getUuid());
            }
        } catch (SQLiteException e) {
            // This is most likely due to a foreign key existing
            // in another table for the row to delete
            Timber.e(e.getMessage());
            return -1;
        }

        return affectedRows;
    }

    /**
     * Performs a database DELETE given a row UUID.
     * <p>
     * NOTE: If an {@link SQLiteException} occurs, it will be logged and -1 will be returned.
     *
     * @param uuid  The UUID of row to perform delete
     * @return int  The number of rows successfully deleted
     */
    @Override
    public int delete(String uuid) {
        if (uuid == null || uuid.equals("")) {
            return -1;
        }

        int affectedRows = 0;

        try {
            E model = mDao.fromUuid(uuid);
            if (model != null) {
                affectedRows = mDao.delete(model);
            }
        } catch (SQLiteException e) {
            // This is most likely due to a foreign key existing
            // in another table for the row to delete
            Timber.e(e.getMessage());
            return -1;
        }

        return affectedRows;
    }

    /**
     * Performs a database DELETE given a row AUTO_INCREMENT id.
     * <p>
     * NOTE: If an {@link SQLiteException} occurs, it will be logged and -1 will be returned.
     *
     * @param id    The id of row to perform delete
     * @return int  The number of rows successfully deleted
     */
    @Override
    public int delete(int id) {
        try {
            E entity = mDao.fromId(id);
            if (entity != null) {
                return delete(entity.getUuid());
            }
        } catch (SQLiteException e) {
            // This is most likely due to a foreign key existing
            // in another table for the row to delete
            Timber.e(e.getMessage());
            return -1;
        }

        return 0;
    }

    /**
     * Returns all rows of a table as a {@link List} of <code><T></code> type.
     * <p>
     * NOTE: If an {@link SQLiteException} occurs, it will be logged and null will be returned.
     *
     * @return List<T> All table rows as POJO objects
     */
    @Override
    public List<T> getAll() {
        List<T> models = null;

        try {
            List<E> entities = mDao.getAll();
            models = mMapper.toDomainModel(entities);
        } catch (SQLiteException e) {
            Timber.e(e.getMessage());
            return models;
        }

        return models;
    }

    /**
     * Returns a single row from a given AUTO_INCREMENT id.
     * <p>
     * NOTE: If an {@link SQLiteException} occurs, it will be logged and null will be returned.
     *
     * @param id    The id of row to retrieve
     * @return T    The row as POJO object
     */
    @Override
    public T getById(int id) {
        T model = null;

        try {
            E entity = mDao.fromId(id);
            model = mMapper.toDomainModel(entity);
        } catch (SQLiteException e) {
            Timber.e(e.getMessage());
            return model;
        }

        return model;
    }

    /**
     * Returns a single row from a given UUID.
     * <p>
     * NOTE: If an {@link SQLiteException} occurs, it will be logged and null will be returned.
     *
     * @param uuid  The UUID of row to retrieve
     * @return T    The row as POJO object
     */
    @Override
    public T getByUuid(String uuid) {
        T model = null;

        try {
            E entity = mDao.fromUuid(uuid);
            model = mMapper.toDomainModel(entity);
        } catch (SQLiteException e) {
            Timber.e(e.getMessage());
            return model;
        }

        return model;
    }
}
