package ua.foodtracker.exception;

/**
 * {@code PrepareStatementException} is exception that indicates that there was a problem during preparing statemnet]
 */
public class PrepareStatementException extends RuntimeException {
    /**
     * Creates a new {@code PrepareStatementException} object with a specified cause.
     *
     * @param e cause of the exception
     */
    public PrepareStatementException(Throwable e) {
        super(e);
    }
}