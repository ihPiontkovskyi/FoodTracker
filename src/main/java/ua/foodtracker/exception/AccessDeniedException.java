package ua.foodtracker.exception;

/**
 * {@code AccessDeniedException} is exception that indicates that there was a problem with user access
 */
public class AccessDeniedException extends RuntimeException {

    /**
     * Creates a new {@code AccessDeniedException} object with a specified message.
     *
     * @param message message of the exception
     */
    public AccessDeniedException(String message) {
        super(message);
    }
}
