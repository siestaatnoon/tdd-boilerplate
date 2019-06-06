package com.cccdlabs.tddboilerplate.domain.repository.exception;

import com.cccdlabs.tddboilerplate.domain.repository.base.Repository;

/**
 * Exception for insert errors occurring in the implemented {@link Repository}
 * classes.
 *
 * @author Johnny Spence
 * @version 1.0.0
 */
public class RepositoryInsertException extends RepositoryException {

    /**
     * No argument constructor.
     */
    public RepositoryInsertException() {}

    /**
     * Constructor.
     *
     * @param message   The error message
     * @param cause     Throwable object triggering this exception
     */
    public RepositoryInsertException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     *
     * @param message   The error message
     */
    public RepositoryInsertException(String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param cause     Throwable object triggering this exception
     */
    public RepositoryInsertException(Throwable cause) {
        super(cause);
    }
}
