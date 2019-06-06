package com.cccdlabs.tddboilerplate.domain.repository.exception;

import com.cccdlabs.tddboilerplate.domain.repository.base.Repository;

/**
 * Exception for update errors occurring in the implemented {@link Repository}
 * classes.
 *
 * @author Johnny Spence
 * @version 1.0.0
 */
public class RepositoryUpdateException extends RepositoryException {

    /**
     * No argument constructor.
     */
    public RepositoryUpdateException() {}

    /**
     * Constructor.
     *
     * @param message   The error message
     * @param cause     Throwable object triggering this exception
     */
    public RepositoryUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     *
     * @param message   The error message
     */
    public RepositoryUpdateException(String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param cause     Throwable object triggering this exception
     */
    public RepositoryUpdateException(Throwable cause) {
        super(cause);
    }
}
