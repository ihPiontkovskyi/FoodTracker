package ua.foodtracker.exception;
/**
 * {@code IncorrectDataException} is exception that indicates that there was a problem with incoming data
 */
public class IncorrectDataException extends RuntimeException {

    /**
     * Creates a new {@code IncorrectDataException} object with a specified message.
     *
     * @param message message of the exception
     */
    public IncorrectDataException(String message) {
        super(message);
    }

}
