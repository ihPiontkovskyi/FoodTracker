package ua.foodtracker.exception;

/**
 * {@code DatabaseInteractionException} is exception that indicates that there was a problem during accessing the database
 */
public class DatabaseInteractionException extends RuntimeException {
    /**
     * Creates a new {@code DatabaseInteractionException} object with a specified message and cause.
     *
     * @param description message of the exception
     * @param exceptionCause           cause of the exception
     */
    public DatabaseInteractionException(String description, Throwable exceptionCause) {
        super(description, exceptionCause);
    }

    /**
     * Creates a new {@code DatabaseInteractionException} object with a specified message.
     *
     * @param description message of the exception
     */
    public DatabaseInteractionException(String description) {
        super(description);
    }
}