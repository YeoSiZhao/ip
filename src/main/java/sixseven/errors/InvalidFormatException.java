package sixseven.errors;

/**
 * Represents an error caused by input that does not follow the expected format.
 */
public class InvalidFormatException extends SixSevenException {

    /**
     * Creates an invalid format exception with the given message.
     *
     * @param message Error message describing the invalid format.
     */
    public InvalidFormatException(String message) {
        super(message);
    }
}
