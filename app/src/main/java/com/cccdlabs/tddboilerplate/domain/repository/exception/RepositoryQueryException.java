package com.cccdlabs.tddboilerplate.domain.repository.exception;

import com.cccdlabs.tddboilerplate.domain.repository.base.Repository;

/**
 * Exception for query errors occurring in the implemented {@link Repository}
 * classes.
 *
 * @author Johnny Spence
 * @version 1.0.0
 */
public class RepositoryQueryException extends RepositoryException {

    /**
     * No argument constructor.
     */
    public RepositoryQueryException() {}

    /**
     * Constructor.
     *
     * @param message   The error message
     * @param cause     Throwable object triggering this exception
     */
    public RepositoryQueryException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     *
     * @param message   The error message
     */
    public RepositoryQueryException(String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param cause     Throwable object triggering this exception
     */
    public RepositoryQueryException(Throwable cause) {
        super(cause);
    }
}
