package ua.foodtracker.exception;

/**
 * {@code DataAccessException} is exception that indicates that there was a problem during accessing the database
 */
public class DataAccessException extends RuntimeException {
    /**
     * Creates a new {@code DataAccessException} object with a specified message and cause.
     *
     * @param description message of the exception
     * @param e           cause of the exception
     */
    public DataAccessException(String description, Throwable e) {
        super(description, e);
    }
}