package com.oscarrrweb.tddboilerplate.domain.model.base;

import java.util.Date;

/**
 * Base model <a href="https://docs.oracle.com/cd/E13171_01/alsb/docs25/userguide/pojo.html" target="_blank">POJO</a>
 * with common fields for subclassed models.
 *
 * @author Johnny Spence
 * @version 1.0.0
 */
abstract public class AbstractModel {

    /**
     * The AUTO_INCREMENT id of the model.
     */
    public int id;

    /**
     * The UUID of the model.
     */
    public String uuid;

    /**
     * The timestamp of model creation
     */
    public Date createdAt;

    /**
     * The timestamp of most recent model update
     */
    public Date updatedAt;


    /**
     * Returns the model ID.
     *
     * @return The ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the model ID. Normally this would be done automatically within the database
     * framework of the application.
     *
     * @param id The model ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the model UUID.
     *
     * @return The model UUID
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets the model UUID.
     *
     * @param uuid The model UUID
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Returns the model time of creation timestamp.
     *
     * @return The created at timestamp
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the model time of creation timestamp.
     *
     * @param createdAt The timestamp
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Returns the model time of last update timestamp.
     *
     * @return The updated at timestamp
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the model time of last update timestamp.
     *
     * @param updatedAt The timestamp
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
