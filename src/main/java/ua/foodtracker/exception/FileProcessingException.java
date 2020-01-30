package ua.foodtracker.exception;

/**
 * {@code FileProcessingException} is exception that indicates that there was an error during file processing.
 */
public class FileProcessingException extends RuntimeException {

    /**
     * Creates a new {@code FileProcessingException} object with a specified message and cause.
     *
     * @param description message of the exception
     * @param e           cause of the exception
     */
    public FileProcessingException(String description, Throwable e) {
        super(description, e);
    }

    /**
     * Creates a new {@code FileProcessingException} object with a specified message and cause.
     *
     * @param s message of the exception
     */
    public FileProcessingException(String s) {
        super(s);
    }

    /**
     * Creates a new {@code FileProcessingException} object with a specified message and cause.
     *
     * @param e cause of the exception
     */
    public FileProcessingException(Throwable e) {
        super(e);
    }
}