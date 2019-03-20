package com.oscarrrweb.tddboilerplate.domain.exception;

/**
 * Wrapper for handling exceptions within the application.
 *
 * @author Johnny Spence
 * @version 1.0.0
 */
public class ErrorContainer {

    /**
     * Error message to use if dealing with a null {@link Exception} or empty message from an
     * Exception.
     */
    private static final String DEFAULT_ERROR_MESSAGE = "Unknown error";

    /**
     * The {@link Exception} to wrap.
     */
    private final Exception exception;

    /**
     * Constructor
     *
     * @param exception
     */
    public ErrorContainer(Exception exception) {
        this.exception = exception;
    }

    /**
     * First checks if the exception is null or has a null or empty error message and returns
     * the class default message if so. Otherwise returns the exception error message.
     *
     * @return The error message String
     */
    public String getErrorMessage() {
        String message = exception == null ? null : exception.getMessage();
        return message == null || message.equals("") ? DEFAULT_ERROR_MESSAGE : message;
    }
}
