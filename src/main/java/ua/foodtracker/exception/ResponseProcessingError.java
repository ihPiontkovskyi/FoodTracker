package ua.foodtracker.exception;

/**
 * {@code ResponseProcessingError} is exception that indicates that there was a problem during processing the servlet
 */
public class ResponseProcessingError extends RuntimeException {
    /**
     * Creates a new {@code ResponseProcessingError} object with a specified message and cause.
     *
     * @param description message of the exception
     * @param e           cause of the exception
     */
    public ResponseProcessingError(String description, Throwable e) {
        super(description, e);
    }
}
