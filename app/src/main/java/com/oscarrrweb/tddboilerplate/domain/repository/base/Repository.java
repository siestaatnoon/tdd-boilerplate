package com.oscarrrweb.tddboilerplate.domain.repository.base;

import java.util.List;

/**
 * Provides the contract for basic database functions. The POJO (Plain Old Java Object)
 * referenced can be explained in more detail
 * <a href="https://docs.oracle.com/cd/E13171_01/alsb/docs25/userguide/pojo.html" target="_blank">here</a>.
 * <p>
 * Note that conversions will be required in the database layer to convert from a database column
 * type to a Java primitive and/or object type (e.g. String to {@link java.util.Date} and
 * vice versa).
 *
 * @param <M> The POJO model type
 * @author Johnny Spence
 * @version 1.0.0
 */
public interface Repository<M> {

    /**
     * Performs an INSERT query.
     *
     * @param model The POJO object containing the fields (member variables) and corresponding
     *              model values to insert
     * @return      The AUTO_INCREMENT ID of the inserted row
     */
    int insert(M model);

    /**
     * Performs an UPDATE query.
     *
     * @param model The POJO object containing the fields (member variables) and corresponding
     *              model values, including the ID and/or UUID of the model, to update
     * @return      The number of rows successfully updated, may be zero if no model values
     *              were changed
     */
    int update(M model);

    /**
     * Performs a DELETE query.
     *
     * @param model The POJO object containing the ID and/or UUID of the model to delete
     * @return      The number of rows successfully deleted, may be zero if model does not exist
     */
    int delete(M model);

    /**
     * Performs a DELETE query.
     *
     * @param uuid  The model UUID to delete
     * @return      The number of rows successfully deleted, may be zero if model does not exist
     */
    int delete(String uuid);

    /**
     * Performs a DELETE query.
     *
     * @param id    The model ID to delete
     * @return      The number of rows successfully deleted, may be zero if model does not exist
     */
    int delete(int id);

    /**
     * Performs a SELECT query returning all model rows.
     *
     * @return {@link List} of all rows as POJO objects
     */
    List<M> getAll();

    /**
     * Performs a SELECT query returning a single model row by ID.
     *
     * @param id    The model ID
     * @return      The model row as POJO object
     */
    M getById(int id);

    /**
     * Performs a SELECT query returning a single model row by UUID.
     *
     * @param uuid  The model UUID
     * @return      The model row as POJO object
     */
    M getByUuid(String uuid);
}
