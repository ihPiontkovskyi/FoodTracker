package ua.foodtracker.exception;

/**
 * {@code ValidationException} is exception that indicates that there was a problem with object
 */

public class ValidationException extends RuntimeException {

    /**
     * Creates a new {@code ValidationException} object with a specified message.
     *
     * @param message message of the exception
     */
    public ValidationException(String message) {
        super(message);
    }
}
