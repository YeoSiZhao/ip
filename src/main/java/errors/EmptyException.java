package errors;

/**
 * Represents an error caused by a required input being left empty.
 */
public class EmptyException extends SixSevenException {

    /**
     * Creates an empty input exception with the given message.
     *
     * @param message Error message describing the empty input.
     */
    public EmptyException(String message) {
        super(message);
    }
}
