package ua.foodtracker.exception;

/**
 * {@code DatabaseInteractionException} is exception that indicates that there was a problem during accessing the database
 */
public class DatabaseInteractionException extends RuntimeException {
    /**
     * Creates a new {@code DatabaseInteractionException} object with a specified message and cause.
     *
     * @param description message of the exception
     * @param e           cause of the exception
     */
    public DatabaseInteractionException(String description, Throwable e) {
        super(description, e);
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