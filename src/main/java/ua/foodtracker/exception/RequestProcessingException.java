package ua.foodtracker.exception;

/**
 * {@code RequestProcessingException} is exception that indicates that there was a problem during processing the servlet response
 */
public class RequestProcessingException extends RuntimeException {
    /**
     * Creates a new {@code RequestProcessingException} object with a specified message and cause.
     *
     * @param description message of the exception
     * @param e           cause of the exception
     */
    public RequestProcessingException(String description, Throwable e) {
        super(description, e);
    }
}
